package com.tju.suma.axiomProcessor;

import com.tju.suma.bean.DicOwlMap;
import com.tju.suma.bean.DisjointClassesMap;
import com.tju.suma.dictionary.Dictionary;
import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquivalentClassProcessor {
    private static Logger log = Logger.getLogger(EquivalentClassProcessor.class);
    public static void OWLObjectIntersectionOfProcessor(OWLClassExpression axiom, int class1, int ip) {
        Iterator<OWLClassExpression> iterator = ((OWLObjectIntersectionOf)axiom).getOperandsAsList().iterator();
        int class2 ;
        while(iterator.hasNext()) {
            OWLClassExpression ax = iterator.next();
            if (ax instanceof OWLClass) {
                if(ip ==1){
                    class2 = Dictionary.encodeRdf(ax.toString());
                    DicOwlMap.addDicOwlSubCLassMap(Processor.SubClassOf, class1, class2);
                    DicOwlMap.addEquiSubClassOfRuleMap(class1,Processor.SubClassOf,class2);
                }
            }
            else if(ax instanceof OWLObjectAllValuesFrom){
                EquivalentClassProcessor.OWLObjectAllValuesFromProcessor(ax, class1, ip);
            }else if (ax instanceof OWLObjectMinCardinality) {
                EquivalentClassProcessor.OWLObjectMinCardinalityProcessor(ax, class1, ip);
            } else if (ax instanceof OWLObjectUnionOf) {
                EquivalentClassProcessor.OWLObjectUnionOfProcessor(ax, class1, ip);
            } else if (ax instanceof OWLObjectSomeValuesFrom){
                EquivalentClassProcessor.OWLObjectSomeValuesFromProcessor(ax, class1, ip);
            }
            else if (ax instanceof OWLObjectComplementOf){
                if(ip ==1){
                    class2 = getComplementClass ((OWLObjectComplementOf) ax);
                    DicOwlMap.addDicOwlSubCLassMap(Processor.SubClassOf, class1, class2);
                    DicOwlMap.addEquiSubClassOfRuleMap(class1,Processor.SubClassOf,class2);
                }
            }
            else{
                log.info("This equivalence class needs to be added to processed："+ax.toString());
            }
        }

    }
    public static void OWLObjectSomeValuesFromProcessor(OWLClassExpression ax, int class1, int ip) {
        String property = ((OWLObjectSomeValuesFrom) ax).getProperty().toString();
        int propertyInt = Dictionary.encodeRdf(property);
        if(ip == 0){
            Processor.graph.addVertex(propertyInt);
            return;
        }
        OWLClassExpression axTmp = ((OWLObjectSomeValuesFrom) ax).getFiller();
        String fillter = axTmp.toString();
        if(axTmp instanceof OWLClass){
            int fillterInt = Dictionary.encodeRdf(fillter);
            DicOwlMap.addDicOwlObjectSomeValuesMap(Processor.ObjectSomeValuesFrom, class1, propertyInt, fillterInt);
            DicOwlMap.addEquiDicSomeValuesMap(class1, Processor.ObjectSomeValuesFrom, propertyInt, fillterInt);
        }
        else{
            if(axTmp instanceof OWLObjectIntersectionOf){
                for (OWLClassExpression classTmp : ((OWLObjectIntersectionOf) axTmp).getOperandsAsList()) {
                    String classString = classTmp.toString();
                    int classInt = Dictionary.encodeRdf(classString);
                    if (classTmp instanceof OWLClass) {
                        DicOwlMap.addDicOwlObjectSomeValuesMap(Processor.ObjectSomeValuesFrom, class1, propertyInt, classInt);
                        DicOwlMap.addEquiDicSomeValuesMap(class1, Processor.ObjectSomeValuesFrom, propertyInt, classInt);
                    } else {
                        System.out.println("This OWLObjectSomeValues at OWLObjectIntersectionOf needs to be processed: " + ax.toString());
                    }
                }

            }
            else if(axTmp instanceof OWLObjectUnionOf){
                for (OWLClassExpression classTmp : ((OWLObjectUnionOf) axTmp).getOperandsAsList()) {
                    String classString = classTmp.toString();
                    int classInt = Dictionary.encodeRdf(classString);
                    if (classTmp instanceof OWLClass) {
                        DicOwlMap.addEquiDicSomeValuesMap(class1, Processor.ObjectSomeValuesFrom, propertyInt, classInt);
                    } else {
                        log.info("This OWLObjectSomeValues at OWLObjectUnionOf needs to be processed: " + ax.toString());
                    }
                }

            }
            else if(axTmp instanceof OWLHasValueRestriction){
                System.out.println("This OWLHasValueRestriction needs to be processed: " + axTmp.toString());
            }
            else if(axTmp instanceof OWLObjectOneOf){
                Iterator<OWLIndividual> iter = ((OWLObjectOneOf) axTmp).getOperandsAsList().iterator();
                List<String> list = new ArrayList<>();
                while(iter.hasNext()){
                    list.add(iter.next().toString());
                }
                int indual1 = Dictionary.encodeRdf(list.get(0));
                int indual2 = Dictionary.encodeRdf(list.get(1));
                DicOwlMap.addEquiDicSomeValuesMap(class1, Processor.ObjectSomeValuesOneOf, propertyInt, indual1);
                DicOwlMap.addEquiDicSomeValuesMap(class1, Processor.ObjectSomeValuesOneOf, propertyInt, indual2);
            }
            else{
                log.info("This OWLObjectSomeValues at OWLObjectOneOf needs to be processed: "+ax.toString());
            }

        }

    }
    public static void OWLObjectUnionOfProcessor(OWLClassExpression ax, int class1, int ip) {
        if(ip == 0) return;
        //正则表达式，取=和|之间的字符串，不包括=和|
        Pattern p = Pattern.compile("<(.*?)>");
        Matcher mm = p.matcher(ax.toString());
        List<String> list = new ArrayList<>();
        while(mm.find()) {
            list.add(mm.group(0));
        }
        int class2 = Dictionary.encodeRdf(list.get(0));
        int class3 = Dictionary.encodeRdf(list.get(1));

        DicOwlMap.addDicOwlSubCLassMap(Processor.SubClassOf, class2, class1);
        DicOwlMap.addDicOwlSubCLassMap(Processor.SubClassOf, class3, class1);

    }
    public static void OWLObjectMinCardinalityProcessor(OWLClassExpression ax, int class1, int ip) {
        String property = ((OWLObjectMinCardinality) ax).getProperty().toString();
        int propertyInt = Dictionary.encodeRdf(property);
        if(ip == 0){
            Processor.graph.addVertex(propertyInt);
            return;
        }
        int cardinality = ((OWLObjectMinCardinality)ax).getCardinality();
        if(((OWLObjectMinCardinality) ax).getFiller() instanceof OWLClass){
            String class2 = ((OWLObjectMinCardinality) ax).getFiller().toString();
            int class2Int = Dictionary.encodeRdf(class2);

            //>=1 == ObjectSomeValuesFrom
            if(cardinality == 1){
                DicOwlMap.addEquiDicSomeValuesMap(class1, Processor.ObjectSomeValuesFrom, propertyInt, class2Int);
                DicOwlMap.addDicOwlObjectSomeValuesMap(Processor.ObjectSomeValuesFrom, class1, propertyInt, class2Int);
            }
            else if(cardinality >1){
                DicOwlMap.addEquiMinCardinalityMap(class1, Processor.ObjectMinCardinality, cardinality, propertyInt, class2Int);
                DicOwlMap.addDicOwlMinCardinalityMap(Processor.ObjectMinCardinality, class1, cardinality, propertyInt, class2Int);
            }
        }
        else{
            log.info("This MinCardinality needs to be processed: "+ax.toString());
        }

    }
    public static void OWLObjectAllValuesFromProcessor(OWLClassExpression ax, int class1, int ip) {
        String property = ((OWLObjectAllValuesFrom)ax).getProperty().toString();
        int propertyInt = Dictionary.encodeRdf(property);
        if(ip == 0){
            Processor.graph.addVertex(propertyInt);
            return;
        }
        OWLClassExpression fillter = ((OWLObjectAllValuesFrom) ax).getFiller();
        int classAllValues = 0;
        if(fillter instanceof OWLObjectComplementOf){
            classAllValues = getComplementClass((OWLObjectComplementOf) fillter);
        }
        else if(fillter instanceof OWLClass){
            classAllValues = Dictionary.encodeRdf(fillter.toString());
        }
        int type = ax.typeIndex();
        DicOwlMap.addDicOwlObjectAllValuesMap(type, class1, propertyInt, classAllValues);
        DicOwlMap.addDicOwlRileAllValuesMap(class1, propertyInt, classAllValues);
        DicOwlMap.addEquiDicAllValueMap(class1,type, propertyInt, classAllValues);

    }

    private static int getComplementClass(OWLObjectComplementOf fillter) {
        int classAllValues;
        String cc = fillter.getOperand().toString();
        int ccInt = Dictionary.encodeRdf(cc);
        int ccDisjoint = DisjointClassesMap.getDisjointClassesMap(ccInt);
        //把DisjointClassesMap看作补集，近似
        if(ccDisjoint == -1){
            StringTokenizer st = new StringTokenizer(cc, "#");
            List<String> list=new ArrayList<>();
            while(st.hasMoreElements()) {
                list.add(st.nextToken());
            }
            classAllValues = Dictionary.encodeRdf(list.get(0) + "#ComplementOf" + list.get(1));
        }
        else{
            classAllValues = ccDisjoint;
        }
        return classAllValues;
    }
}
