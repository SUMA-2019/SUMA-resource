package com.tju.suma.jenaQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class query {
    private static final List<String> queryList = new ArrayList<>();

    public static List<String> getQueryList() {
        return queryList;
    }

    static int queryCount = 0;

    public static void readQuery(String pathQuery) {
        Path fpath = Paths.get(pathQuery);
        try {
            BufferedReader bfr = Files.newBufferedReader(fpath);
            String line;
            StringBuilder query = new StringBuilder();
            while ((line = bfr.readLine()) != null) {
                if (!line.contains("^")) {
                    query.append(line).append("\n");
                    if (line.contains("}")) {
                        queryList.add(query.toString());
                        queryCount++;
                        query.setLength(0);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readQuery("data/standard_and_gap.sparql");
        System.out.println(queryList);
        System.out.println(queryCount);
    }
}
