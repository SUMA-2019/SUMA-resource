package com.tju.suma.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EquivalentPropertyMap {
    public static final Map<Integer, Integer> EquivalentPropertyMap = new ConcurrentHashMap<>();
    public static final Map<Integer, Integer> EquivalentPropertyMapDecode = new ConcurrentHashMap<>();
    public static final List<Integer> EquivalentPropertyList = new ArrayList<>();

    public static Map<Integer, Integer> getEquivalentPropertyMapDecode() {
        return EquivalentPropertyMapDecode;
    }

    public static void setEquivalentPropertyDecode(int second, int first) {
        EquivalentPropertyMapDecode.put(second,first);
    }

    @Override
    public String toString() {
        return "EquivalentPropertyMap{}";
    }

    public static void setEquivalentProperty(int first, int second) {
        EquivalentPropertyMap.put(first,second);
    }
}
