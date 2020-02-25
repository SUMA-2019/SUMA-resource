package com.tju.suma.io;

import com.tju.suma.bean.*;
import com.tju.suma.dictionary.Dictionary;
import com.tju.suma.test.SUMARunTest;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DictionaryOutput {
    private static Logger log = Logger.getLogger(DictionaryOutput.class.getClass());
    public static void outWriteDicDataMap(String pathAboxNew) throws IOException {
        int count = 0;
        int eqiv_count = 0;
        int inverse_count = 0;
        Map<Integer, DicRdfDataBean> totalData = DicRdfDataMap.getDicDataMap();
        String[] decodeMap = Dictionary.getDecode();
        Map<Integer, Integer> inverseMapDecode = InversePropertyMap.getInverseMapDecode();
        Map<Integer, Integer> EquivalentMapDecode = EquivalentPropertyMap.getEquivalentPropertyMapDecode();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathAboxNew), StandardCharsets.UTF_8));
        out.write("<unknown:namespace> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#Ontology> .");
        out.newLine();
        out.write("<unknown:namespace> <http://www.w3.org/2002/07/owl#imports> <http://swat.cse.lehigh.edu/onto/univ-bench.owl> .");
        out.newLine();
        for (Map.Entry<Integer, DicRdfDataBean> entry : totalData.entrySet()) {
            int rs = entry.getValue().getRs();
            String Rs;
            if (rs < 0) {
                Rs = "<" + rs + ">";
            } else {
                Rs = decodeMap[rs];
            }

            int rp = entry.getValue().getRp();
            String Rp;
            if (rp < 0) {
                Rp = "<" + rp + ">";
            } else {
                Rp = decodeMap[rp];
            }

            int ro = entry.getValue().getRo();
            String Ro;
            if (ro < 0) {
                Ro = "<" + ro + ">";
            } else {
                Ro = decodeMap[ro];
            }
            if (inverseMapDecode.containsKey(rp)) {
                String Rp1 = decodeMap[inverseMapDecode.get(rp)];
                out.write(Ro + " " + Rp1 + " " + Rs + " .");
                inverse_count++;
                count++;
            }
            if (EquivalentMapDecode.containsKey(rp)) {
                String Rp1 = decodeMap[EquivalentMapDecode.get(rp)];
                out.write(Rs + " " + Rp1 + " " + Ro + " .");
                eqiv_count++;
                count++;
            }
            out.write(Rs + " " + Rp + " " + Ro + " .");
            count++;
            out.newLine();
        }
        out.flush();
        out.close();
        log.info("The number of facts after materialization: " + count);
        log.info("The number of facts reduced in materialization due to equivalent role rewriting: " + eqiv_count);
        log.info("The number of facts reduced in materialization due to inverse role rewriting: " + inverse_count);
    }

    public static void encodeMap(String pathEncode) throws IOException {
        Map<String, Integer> encode = Dictionary.getEncode();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathEncode), StandardCharsets.UTF_8));
        for (Map.Entry<String, Integer> entry : encode.entrySet()) {
            out.write(entry.getValue().toString());
            out.write(" " + entry.getKey());
            out.newLine();
        }
        out.flush();
        out.close();

        encode.clear();
        System.gc();
    }


}
