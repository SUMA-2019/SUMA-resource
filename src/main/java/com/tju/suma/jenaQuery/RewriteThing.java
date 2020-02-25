package com.tju.suma.jenaQuery;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class RewriteThing {
    public static void rewriteThing(String path1, String path2) throws IOException {
        Path fpath= Paths.get(path1);
        BufferedReader bfr= Files.newBufferedReader(fpath);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2), StandardCharsets.UTF_8));
        String line;
        boolean flag;
        StringBuilder ss = new StringBuilder();
        while((line=bfr.readLine())!=null) {
            flag = false;
            StringTokenizer st = new StringTokenizer(line, " ");
            List<String> list=new ArrayList<>();
            while(st.hasMoreElements()) {
                list.add(st.nextToken());
            }
            Iterator<String> iterorList = list.iterator();
            ss.setLength(0);
            while(iterorList.hasNext()) {
                if (flag) {
                    ss.append(" ");
                }
                String ss1 = iterorList.next();
                if (ss1.contains("owl:Thing")) {
                    ss.append("<").append(ss1).append(">");
                } else {
                    ss.append(ss1);
                }
                flag = true;
            }
            out.write(ss.toString());
            out.newLine();
        }
        bfr.close();
        out.flush();
        out.close();
    }
}
