package com.tju.suma.dictionary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {
    public static Integer indexEncode = 2;
    private static final Map<String, Integer> Encode=new ConcurrentHashMap<>();
    public static String[] Decode=null;
    public static Map<String, Integer> getEncode(){ return Encode; }
    public static String[] getDecode(){ return Decode; }

    /**
     *dictionary put type and owl:Thing
     */
    public static void init() {
        Encode.put("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",0);
        Encode.put("owl:Thing",1);

    }
    public static int encodeRdf(String ss) {
        int ssIndex;
        if(!Encode.containsKey(ss)){
            Encode.put(ss, indexEncode);
            ssIndex = indexEncode;
            indexEncode ++;
        }
        else{
            ssIndex = Encode.get(ss);
        }
        return ssIndex;
    }

}
