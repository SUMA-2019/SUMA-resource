package com.tju.suma.bean;

import com.tju.suma.axiomProcessor.Processor;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DicOwlMap {
    private static final Map<String, List<DicOwlBean>> DicRuleMap = new ConcurrentHashMap<>();
    public static final Map<Integer, List<DicOwlBean>> EquiDicRuleMap = new ConcurrentHashMap<>();

    public static Map<String, List<DicOwlBean>> getRuleMap(){ return DicRuleMap; }
    
    public static void addDicOwlMap(int type, int class1, int class2) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(type);
        dicOwlBean.setRuleHead(class2);
        //subClassOf
        if (type == 2002){ 
            if(class2 == 0) return;
            String key = "*0" + class1;
            addDicOwlMap(dicOwlBean, key);

        }
       else{
           //ObjectPropertyRange  ObjectPropertyDomain SubObjectPropertyOf
            String key = "*" + class1 + "*";
            addDicOwlMap(dicOwlBean, key);
        }


    }
    public static void addDicOwlMap(DicOwlBean dicOwlBean, String key) {
        if (DicRuleMap.containsKey(key)) {
            List<DicOwlBean> lists = DicRuleMap.get(key);
            for (DicOwlBean aa : lists) {
                if (aa.equals(dicOwlBean)) {
                    return;
                }
            }
            DicRuleMap.get(key).add(dicOwlBean);
        } else {
            List<DicOwlBean> listOwlBean = new ArrayList<>();
            listOwlBean.add(dicOwlBean);
            DicRuleMap.put(key, listOwlBean);
        }
    }

    public static void addEquiSubClassOfRuleMap(int class1, int subClassOf, int class2) {
        DicOwlBean bean = new DicOwlBean();
        bean.setType(subClassOf);
        bean.setRuleHead(class2);
        EquiDicRuleMap.get(class1).add(bean);
    }

    public static void addDicOwlPropertyRangeMap(int type, int pro, int ran) {
        Pair<Integer, Integer> pair = rewriteProperty(type, pro);
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(ran);
        String key = "*" + pair.getKey() + "*";
        addDicOwlMap(dicOwlBean, key);

    }

    public static void addDicOwlInversePropertyMap(int type, int pro, int ran) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(type);
        dicOwlBean.setRuleHead(ran);
        String key = "*" + pro + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlEquivalentPropertyMap(int type, int pro, int ran) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(type);
        dicOwlBean.setRuleHead(ran);
        String key = "*" + pro + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlPropertyDomainMap(int type, int pro, int dom) {
        Pair<Integer, Integer> pair = rewriteProperty(type, pro);
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(dom);
        String key = "*" + pair.getKey() + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlObjectSomeValuesMap(int type, int class1, int propertyInt, int fillterInt) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey(),fillterInt);
        String key = "*0" + class1;
        addDicOwlMap(dicOwlBean, key);
    }
    public static void addDicOwlObjectHasValueMap(int type, int class1, int propertyInt, int fillterInt) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey(),fillterInt);
        String key = "*0" + class1;
        addDicOwlMap(dicOwlBean, key);
    }
    public static void addEquiDicSomeValuesMap(int class1, int type, int propertyInt, int class2Int) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey(),class2Int);
        EquiDicRuleMap.get(class1).add(dicOwlBean);
    }
    private static Pair<Integer, Integer> rewriteProperty(int type, int propertyInt) {
        if(!Processor.isRoleWriting) return new Pair<>(propertyInt, type);
        if(EquivalentPropertyMap.EquivalentPropertyMap.containsKey(propertyInt)){
            propertyInt = EquivalentPropertyMap.EquivalentPropertyMap.get(propertyInt);
        }
        if(InversePropertyMap.InverseMap.containsKey(propertyInt)){
            propertyInt = InversePropertyMap.InverseMap.get(propertyInt);
            type = Processor.typeInverse.get(type);
        }
        return new Pair<>(propertyInt, type);
    }

    public static void addDicOwlSubCLassMap(int type, int sub, int sup) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(type);
        dicOwlBean.setRuleHead(sup);
//        if(class2 == 0) return;
        String key = "*0" + sub;
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlSubPropertyMap(int type, int sub, int sup) {
        if(InversePropertyMap.InverseMap.containsKey(sub)){
            if(InversePropertyMap.InverseMap.containsKey(sup)){
                DicOwlMap.addDicOwlMap(type, InversePropertyMap.InverseMap.get(sub), InversePropertyMap.InverseMap.get(sup));
            }
        }
        else if(InversePropertyMap.InverseMap.containsKey(sup)){
        }
        else{
            DicOwlMap.addDicOwlMap(type, sub, sup);
        }
    }

    public static void addDicOwlSymmetricPropertyMap(int type, int pro) {
        Pair<Integer, Integer> pair = rewriteProperty(type, pro);
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey());
        String key = "*" + pro + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlTransitivePropertyMap(int type, int pro) {
        Pair<Integer, Integer> pair = rewriteProperty(type, pro);
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey());
        String key = "*" + pro + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlInverseFunctionalMap(int type, int pro) {
        Pair<Integer, Integer> pair = rewriteProperty(type, pro);
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey());
        String key = "*" + pro + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlFunctionalPropertyMap(int type, int pro) {
        Pair<Integer, Integer> pair = rewriteProperty(type, pro);
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey());
        String key = "*" + pro + "*";
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addDicOwlObjectAllValuesMap(int type, int class1, int propertyInt, int classAllValues) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        dicOwlBean.setType(pair.getValue());
        dicOwlBean.setRuleHead(pair.getKey(),classAllValues);
        String key = "*0" + class1;
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addEquiDicAllValueMap(int class1, int type, int propertyInt, int classAllValues) {
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        DicOwlBean bean = new DicOwlBean();
        bean.setType(pair.getValue());
        bean.setRuleHead(pair.getKey(), classAllValues);
        EquiDicRuleMap.get(class1).add(bean);
    }

    public static void addDicOwlMinCardinalityMap(int type, int class1, int cardinality, int propertyInt, int class2Int) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        dicOwlBean.setType(pair.getValue());//class2
        dicOwlBean.setRuleHead(cardinality,pair.getKey(),class2Int);
        String key = "*0" + class1;
        addDicOwlMap(dicOwlBean, key);
    }

    public static void addEquiMinCardinalityMap(int class1, int type, int cardinality, int propertyInt, int class2Int) {
        DicOwlBean bean = new DicOwlBean();
        Pair<Integer, Integer> pair = rewriteProperty(type, propertyInt);
        bean.setType(pair.getValue());
        bean.setRuleHead(cardinality, pair.getKey(), class2Int);
        DicOwlMap.EquiDicRuleMap.get(class1).add(bean);
    }

    public static void addDicOwlRileAllValuesMap(int class1, int propertyInt, int classAllValues) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(55);
        dicOwlBean.setRuleHead(class1,classAllValues);
        String key = "*" + propertyInt + "*";
        addDicOwlMap(dicOwlBean, key);
    }
}
