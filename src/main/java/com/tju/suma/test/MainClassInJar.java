package com.tju.suma.test;

import com.tju.suma.axiomProcessor.Processor;
import com.tju.suma.dictionary.Dictionary;
import com.tju.suma.io.DictionaryInput;
import com.tju.suma.io.DictionaryOutput;
import com.tju.suma.jenaQuery.RewriteThing;
import com.tju.suma.reason.DicSerialReason;
import com.tju.suma.reason.SameAsReason;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.IOException;

import static com.tju.suma.jenaQuery.JenaTest.jenaQuerySimple;

public class MainClassInJar {
    public  static boolean flagTTL = false;
    public static void main(String[] args) throws Exception {
        int index = 0;

        String pathTBox = null;
        String pathABox = null;
        int n = 7;
        String pathData = null;
        boolean isQueryByJena = false;
        String pathDataThing = "newThing_oubm1.nt";
        String queryPath = null;
        String answerPath = "result_new.nt";
        if(args.length>index){
            pathTBox = args[index];
            index++;
        }
        if(args.length>index){
            pathABox = args[index];
            if(args[index].endsWith(".ttl")){
                flagTTL = true;
            }
            index++;
        }
        if(args.length>index){
            pathData = args[index];
            index++;
        }
        if(args.length>index){
            Processor.isRoleWriting = Boolean.parseBoolean(args[index]);
            index++;
        }
        if(args.length>index){
            n = Integer.parseInt(args[index]);
            index++;
        }
        if(args.length>index){
            isQueryByJena = Boolean.parseBoolean(args[index]);
            index++;
        }
        if(args.length>index){
            queryPath = args[index];
        }
        materialization(pathTBox, pathABox, n);
        writeFile(pathData);
        if(isQueryByJena){
            queryByJena(pathData, pathDataThing, queryPath, answerPath);
        }
    }

    private static void writeFile(String pathData) throws IOException {
        DictionaryOutput.outWriteDicDataMap(pathData);
    }

    private static void queryByJena(String pathABox, String pathABoxThing, String queryPath, String answerPath) throws IOException {
        RewriteThing.rewriteThing(pathABox, pathABoxThing);
        jenaQuerySimple(pathABoxThing, queryPath, answerPath);
    }

    public static void materialization(String pathTBox, String pathABox, int n) throws OWLOntologyCreationException, IOException {
        new Dictionary();
//        规则预处理
        DictionaryInput.readTBox(pathTBox);
        //数据预处理
        preDealData(pathABox);
        DictionaryOutput.encodeMap("encode.txt");
        //单线程推理
        reason(n);
        DictionaryInput.readDictionary("encode.txt");

    }

    private static void reason(int n) {
        long startTime3 = System.currentTimeMillis();
        DicSerialReason.reason(n);
        long startTime4=System.currentTimeMillis();
        System.out.println("reason time: "+(startTime4-startTime3)+"ms");
        SameAsReason.addEquivIndividual();
    }

    private static void preDealData(String pathABox) throws IOException {
        long startTime1 = System.currentTimeMillis();
        if(flagTTL){
            DictionaryInput.readTtlABox(pathABox);
        }
        else{
            DictionaryInput.readABox(pathABox);
        }
        long startTime2 = System.currentTimeMillis();
        System.out.println("reading time: "+(startTime2-startTime1)+ "ms");
    }
}
