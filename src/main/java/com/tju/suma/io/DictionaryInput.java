package com.tju.suma.io;

import com.hp.hpl.jena.util.FileManager;
import com.tju.suma.axiomProcessor.Processor;
import com.tju.suma.bean.*;
import com.tju.suma.dictionary.Dictionary;
import com.tju.suma.reason.DicSerialReason;
import com.tju.suma.roleScore.RoleGraph;
import com.tju.suma.rewrite.EquiClassRuleRewrite;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import static com.tju.suma.axiomProcessor.Processor.classAssertion;


public class DictionaryInput {
    public static RoleGraph graph = RoleGraph.getRoleGraph();
    private static Logger log = Logger.getLogger(DictionaryInput.class);
    public static void readABox(String pathABox) throws IOException {
        Map<Integer, DicRdfDataBean> dic = DicRdfDataMap.getDicDataMap();
        int index = dic.size();
        LineIterator it = FileUtils.lineIterator(new File(pathABox), "UTF-8");
        int rewrite_equiv_count = 0;
        int rewrite_inver_count = 0;
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (!line.contains("\\") && !line.contains("unknown:namespace")) {
                    List<String> list = Arrays.stream(line.split(" ")).collect(Collectors.toList());
                    String Rs = list.get(0);
                    String Rp = list.get(1);
                    String Ro = list.get(2);
                    int rs = Dictionary.encodeRdf(Rs);
                    int rp = Dictionary.encodeRdf(Rp);
                    int ro = Dictionary.encodeRdf(Ro);
//                  逆角色，等价角色进行替换
                    if (EquivalentPropertyMap.EquivalentPropertyMap.containsKey(rp)) {
                        rp = EquivalentPropertyMap.EquivalentPropertyMap.get(rp);
                        rewrite_equiv_count ++;
                    }
                    if (InversePropertyMap.InverseMap.containsKey(rp)) {
                        rp = InversePropertyMap.InverseMap.get(rp);
                        int tmp = rs;
                        rs = ro;
                        ro = tmp;
                        rewrite_inver_count++;
                    }
                    DicRdfDataMap.addSourceRdfDataBean(index, rs, rp, ro);

                    index++;
                    if (index % 1000000 == 0) {
                        log.info("finish read " + index + " data");
                    }

                }
            }
        } finally {
            it.close();

        }
        log.info("----------------------------------------------------");
        log.info("Number of initial data: " + index);
        log.info("Number of facts with equivalent role substitution: " + rewrite_equiv_count);
        log.info("Number of facts with inverse role substitution: " + rewrite_inver_count);
        log.info("Number of equivalent roles: " + EquivalentPropertyMap.EquivalentPropertyMap.size()*2);
        log.info("Number of inverse roles: " + InversePropertyMap.InverseMap.size()*2);

        addClassAssertion(index);

    }

    public static void readTtlABox(String pathABox) {
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(pathABox);
        if (in == null)
        {
            throw new IllegalArgumentException("File: " + pathABox + " not found");
        }
        //model.read(in, "","RDF/XML");//根据文件格式选用参数即可解析不同类型
        //model.read(in, "","N3");
        model.read(in, "","TTL");
        // list the statements in the graph
        StmtIterator iter = model.listStatements();
        Map<Integer, DicRdfDataBean> dic = DicRdfDataMap.getDicDataMap();
        int index = dic.size();
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); // get next statement
            String subject = stmt.getSubject().toString(); // get the subject
            String predicate = stmt.getPredicate().toString(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object

            String Ro = object.toString();
            int ro;
            if (object instanceof Resource) {
                ro = Dictionary.encodeRdf("<"+Ro+">");
            }
            // object is a literal
            else {
                ro = Dictionary.encodeRdf("\""+Ro+"\"");
            }

            int rs = Dictionary.encodeRdf("<"+ subject +">");
            int rp = Dictionary.encodeRdf("<"+ predicate +">");

//                  逆角色，等价角色进行替换
            if (EquivalentPropertyMap.EquivalentPropertyMap.containsKey(rp)) {
                rp = EquivalentPropertyMap.EquivalentPropertyMap.get(rp);
            }
            if (InversePropertyMap.InverseMap.containsKey(rp)) {
                rp = InversePropertyMap.InverseMap.get(rp);
                int tmp = rs;
                rs = ro;
                ro = tmp;
            }
            DicRdfDataMap.addSourceRdfDataBean(index, rs, rp, ro);

            index++;
            if (index % 1000000 == 0) {
                log.info("finish read " + index + " data");
            }

        }
        log.info("Number of source data: " + index);
        addClassAssertion(index);

    }

    private static void addClassAssertion(int index) {
        int tmpCount = index;
        Iterator<Integer> iter = classAssertion.iterator();
        while (iter.hasNext()) {
            int tmp1 = iter.next();
            int tmp2 = iter.next();
            DicRdfDataMap.addSourceRdfDataBean(tmpCount, tmp1, 0, tmp2);
            tmpCount++;
        }
        log.info("Number of facts after adding ClassAssertion: " + tmpCount);
    }

    public static void readTBox(String pathTBox) throws OWLOntologyCreationException {
        File testFile = new File(pathTBox);
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology univBench = m.loadOntologyFromOntologyDocument(testFile);

        //添加属性图节点，边，权重更新, 不添加公理
        int ip = 0;
        //round1 初始化属性图（公理处理，添加属性图节点，边，权重更新）
        axiomProcessor(univBench, ip);
        //round2 图节点排序（属性重要度排序）
        if(Processor.isRoleWriting) {
            graph.depthFirstSearch();
            //根据排序确定等价属性，逆属性替换表
            setEquivalentPropertyMap();
            setInversePropertyMap();
        }
        //round3 根据属性重要度，添加公理，等价属性，逆属性替换
        ip = 1;
        axiomProcessor(univBench, ip);
        EquiClassRuleRewrite.rewrite();
    }

    private static void setInversePropertyMap() {
        Iterator<Integer> inverseProIter = InversePropertyMap.InverseProperty.iterator();
        while (inverseProIter.hasNext()) {
            int inversePro1 = inverseProIter.next();
            int inversePro2 = inverseProIter.next();
            int weightPro1 = graph.getPropertyWeight(inversePro1);
            int weightPro2 = graph.getPropertyWeight(inversePro2);
            if (weightPro1 < weightPro2) {
                InversePropertyMap.InverseMap.put(inversePro1, inversePro2);
                InversePropertyMap.InverseMapDecode.put(inversePro2, inversePro1);
            } else if (weightPro1 > weightPro2) {
                InversePropertyMap.InverseMap.put(inversePro2, inversePro1);
                InversePropertyMap.InverseMapDecode.put(inversePro1, inversePro2);
            } else {
                log.info(inversePro1 + " " + inversePro2 + "(inverse) have the same weight!");
                if (inversePro1 < inversePro2) {
                    InversePropertyMap.InverseMap.put(inversePro1, inversePro2);
                    InversePropertyMap.InverseMapDecode.put(inversePro2, inversePro1);
                } else {
                    InversePropertyMap.InverseMap.put(inversePro2, inversePro1);
                    InversePropertyMap.InverseMapDecode.put(inversePro1, inversePro2);
                }
            }

        }
    }

    private static void setEquivalentPropertyMap() {
        Iterator<Integer> equiProIter = EquivalentPropertyMap.EquivalentPropertyList.iterator();
        while (equiProIter.hasNext()) {
            int equiPro1 = equiProIter.next();
            int equiPro2 = equiProIter.next();
            int weightPro1 = graph.getPropertyWeight(equiPro1);
            int weightPro2 = graph.getPropertyWeight(equiPro2);
            if (weightPro1 < weightPro2) {
                EquivalentPropertyMap.setEquivalentProperty(equiPro1, equiPro2);
                EquivalentPropertyMap.setEquivalentPropertyDecode(equiPro2, equiPro1);
            } else if (weightPro1 > weightPro2) {
                EquivalentPropertyMap.setEquivalentProperty(equiPro2, equiPro1);
                EquivalentPropertyMap.setEquivalentPropertyDecode(equiPro1, equiPro2);
            } else {
                log.info(weightPro1 + " " + weightPro2 + "(equi) have the same weight!");
                if (equiPro1 < equiPro2) {
                    EquivalentPropertyMap.setEquivalentProperty(equiPro1, equiPro2);
                    EquivalentPropertyMap.setEquivalentPropertyDecode(equiPro2, equiPro1);
                } else {
                    EquivalentPropertyMap.setEquivalentProperty(equiPro2, equiPro1);
                    EquivalentPropertyMap.setEquivalentPropertyDecode(equiPro1, equiPro2);
                }
            }

        }
    }

    private static void axiomProcessor(OWLOntology univBench, int ip) {
        int type;
        Iterator<OWLAxiom> localIterator1 = univBench.axioms().iterator();
        int index = 0;
        while (localIterator1.hasNext()) {
            index++;
            OWLAxiom axiom = localIterator1.next();
            type = axiom.typeIndex();
            switch (type) {
                case Processor.ObjectPropertyRange:
                    Processor.ObjectPropertyRangeProcessor(axiom, ip);
                    break;
                case Processor.ObjectPropertyDomain:
                    Processor.ObjectPropertyDomainProcessor(axiom, ip);
                    break;
                case Processor.SubClassOf:
                    Processor.OWLSubCLassProcessor(axiom, ip);
                    break;
                case Processor.SubObjectPropertyOf:
                    Processor.OWLSubObjectPropertyProcessor(axiom, ip);
                    break;
                case Processor.SymmetricObjectProperty:
                    Processor.OWLSymmetricObjectPropertyProcessor(axiom, ip);
                    break;
                case Processor.TransitiveProperty:
                    Processor.OWLTransitiveObjectPropertyProcessor(axiom, ip);
                    break;
                case Processor.InverseFunctionalObjectProperty:
                    Processor.InverseFunctionalProcessor(axiom, ip);
                    break;
                case Processor.FunctionalObjectProperty:
                    Processor.FunctionalPropertyProcessor(axiom, ip);
                    break;
                case Processor.EquivalentClass:
                    Processor.EquivalentClassProcessor((OWLEquivalentClassesAxiom) axiom, ip);
                    break;
                case Processor.OWLClassAssertion:
                    Processor.OWLClassAssertionProcessor((OWLClassAssertionAxiom) axiom, ip);
                    break;
                case Processor.InverseProperty:
                    Processor.OWLInversePropertyProcessor(axiom, ip);
                    break;
                case Processor.EquivalentProperty:
                    Processor.OWLEquivalentPropertyProcessor(axiom, ip);
                    break;
                case Processor.OWLDisjointClassesAxiom:
                    Processor.OWLDisjointClassesProcessor(axiom, ip);
                    break;
                case Processor.ObjectPropertyAssertion:
                    Processor.OWLObjectPropertyAssertionProcessor(axiom, ip);
                    break;

                default:
                    //OWLLogicalAxiom
                    break;
            }
        }
        if(ip == 1) log.info("axioms count " + index);
    }

    public static void main(String[] args) throws OWLOntologyCreationException {
        DictionaryInput.readTBox("data/dbpedia+travel.owl");
    }

    public static void readDictionary(String pathEncode) throws IOException {
        Dictionary.Decode = new String[Dictionary.indexEncode];
        String[] decode = Dictionary.getDecode();
        int index;
        FileInputStream in = new FileInputStream(pathEncode);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        while ((line = bfr.readLine()) != null) {
            List<String> list = Arrays.stream(line.split("\\s+")).collect(Collectors.toList());
            index = Integer.parseInt(list.get(0));
            decode[index] = list.get(1);
        }
    }
}
