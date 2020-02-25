package com.tju.suma.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InversePropertyMap {
    public static final Map<Integer, Integer> InverseMap = new ConcurrentHashMap<>();
    public static final Map<Integer, Integer> InverseMapDecode = new ConcurrentHashMap<>();
    public static final List<Integer> InverseProperty = new ArrayList<>();
    public static Map<Integer, Integer> getInverseMapDecode(){ return InverseMapDecode; }

    @Override
    public String toString() {
        return String.format("InversePropertyMap InverseMap{%s}",InverseMap);
    }

}
