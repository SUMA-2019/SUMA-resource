package com.tju.suma.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test_bug {
    public static void output_bug_line_in_file() throws IOException {
        String pathABox="newThing_oubm1.nt";
        Path fpath= Paths.get(pathABox);
        BufferedReader bfr= Files.newBufferedReader(fpath);
        String line;
        int index1 = 1;
        while((line=bfr.readLine())!=null) {
            if(index1>=(29903268-2)&&index1<=(29903268+10)){
                if(line.equals("")){
                    System.out.println("kong");
                }
                System.out.println(line);
            }
            index1 ++;
        }
        bfr.close();
    }
    public static void main(String[] args) throws Exception {
        output_bug_line_in_file();
    }
}
