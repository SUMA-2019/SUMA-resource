package com.tju.suma.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;

public class compareRoleWrite {
    static HashSet<String> data_no_rewrite = new HashSet<>();
    static HashSet<String> data_rewrite = new HashSet<>();
    public static void main(String []args) throws IOException {
        String path_no = "data/result_new_no_rewrite.nt";
        String path_rewrite = "data/result_new.nt";
        readDataNo(path_no);
        readData(path_rewrite);
        data_rewrite.removeAll(data_no_rewrite);
        Iterator<String> iter = data_rewrite.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
//        System.out.println(data_pellet);
        System.out.println(data_rewrite.size());

    }

    private static void readDataNo(String path_no) throws IOException {
        Path fpath= Paths.get(path_no);
        BufferedReader bfr= Files.newBufferedReader(fpath);
        String line;
        while((line=bfr.readLine())!=null) {
            data_no_rewrite.add(line.trim());
        }
    }
    private static void readData(String path_no) throws IOException {
        Path fpath= Paths.get(path_no);
        BufferedReader bfr= Files.newBufferedReader(fpath);
        String line;
        while((line=bfr.readLine())!=null) {
            data_rewrite.add(line.trim());
        }
    }
}
