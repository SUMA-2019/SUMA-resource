package com.tju.suma.jenaQuery;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class JenaTest {
    static HashSet<String> resultsSet = new HashSet<>();
    private static Logger log = Logger.getLogger(JenaTest.class);
    public static void jenaQuerySimple(String dataPath, String queryPath) throws IOException {
         jenaQuerySimple(dataPath, queryPath, null);
    }

    public static void jenaQuerySimple(String dataPath, String queryPath, String answerPath) throws IOException {
        //query
        query.readQuery(queryPath);
        Model model = ModelFactory.createMemModelMaker().createDefaultModel();
        //extended data
        model.read(dataPath);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(answerPath), StandardCharsets.UTF_8));
        List<String> queryList = query.getQueryList();
        Iterator<String> QueryIterator = queryList.iterator();
        int count = 0;
        while (QueryIterator.hasNext()) {
            resultsSet.clear();
            String queryString = QueryIterator.next();
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, model);

            ResultSet results = qe.execSelect();

            //输出查询结果
            StringBuilder ss = new StringBuilder();
            while (results.hasNext()) {
                QuerySolution next = results.next();
                RDFNode resource = next.get("?x");
                if (next.get("?y") != null) {
                    RDFNode resource1 = next.get("?y");
                    if (next.get("?z") != null) {
                        RDFNode resource2 = next.get("?z");
                        ss.setLength(0);
                        ss.append(resource.toString()).append(" ").append(resource1.toString()).append(" ").append(resource2.toString());
                    } else {
                        ss.setLength(0);
                        ss.append(resource.toString()).append(" ").append(resource1.toString());
                    }

                } else {
                    ss.setLength(0);
                    ss.append(resource.toString());
                }
                out.write(ss.toString());
                if (!ss.toString().startsWith("-")) {
                    resultsSet.add(ss.toString());
                }
                out.newLine();

            }
            qe.close();
            count++;
            log.info("q "+ count);
            log.info("resultsCount: " + resultsSet.size());
        }
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws Exception {
        String dataPath = "data/newThing_oubm1.nt";
        String queryPath = "data/test.sparql";
        jenaQuerySimple(dataPath, queryPath);
    }

}
