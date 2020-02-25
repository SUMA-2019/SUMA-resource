package com.tju.suma.rewrite;

import com.tju.suma.bean.DicOwlBean;
import com.tju.suma.bean.DicOwlMap;
import com.tju.suma.dictionary.Dictionary;

import java.util.List;
import java.util.Map;

public class EquiClassRuleRewrite {
    public static void main(String[] args) {
        rewrite();
    }

    public static void rewrite() {
        for (Map.Entry<Integer, List<DicOwlBean>> iterEntity : DicOwlMap.EquiDicRuleMap.entrySet()) {
            int class1 = iterEntity.getKey();
            List<DicOwlBean> owlBeanList = iterEntity.getValue();
            if (owlBeanList.size() == 1) {
                DicOwlBean owlBean = owlBeanList.get(0);
                int type = owlBean.getType();
                if (type == 3005) {
                    List<Integer> ruleHead = owlBean.getRuleHead();
                    addEquiSomeValue(class1, ruleHead.get(0), ruleHead.get(1));
                } else if (type == 3008) {
                    List<Integer> ruleHead = owlBean.getRuleHead();
                    addEquiMin(class1, ruleHead.get(0), ruleHead.get(1), ruleHead.get(2));
                }
            } else if (owlBeanList.size() == 2) {
                if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 3005) {
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property = owlBeanList.get(1).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(1);
                    addEquiClassSomeValue(class1, class2, property, class3);

                } else if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 3006) {//NonScienceStudent
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property = owlBeanList.get(1).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(1);
                    addEquiNonScienceStudent(class1, class2, property, class3);
                } else if (owlBeanList.get(0).getType() == 3006 && owlBeanList.get(1).getType() == 3005) {//GraduateStudent
                    int property = owlBeanList.get(0).getRuleHead().get(0);
                    int class3 = owlBeanList.get(0).getRuleHead().get(1);
                    addEquiGraduateStudent(class1, property, class3);
                } else if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 2002) {
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(0);
                    addQuietDestination(class1, class2, class3);
                }
            } else if (owlBeanList.size() == 3) {
                if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 3005 && owlBeanList.get(2).getType() == 3008) {
                    //ObjectMinCardinality(1 <http://www.owl-ontologies.com/travel.owl#hasAccommodation>  ObjectMinCardinality(2 <http://www.owl-ontologies.com/travel.owl#hasActivity>
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property1 = owlBeanList.get(1).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(1);
                    int count = owlBeanList.get(2).getRuleHead().get(0);
                    int property2 = owlBeanList.get(2).getRuleHead().get(1);
                    int class4 = owlBeanList.get(2).getRuleHead().get(2);
                    addFamilyDestination(class1, class2, property1, class3, count, property2, class4);
                } else if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 3005 && owlBeanList.get(2).getType() == 3005) {
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property1 = owlBeanList.get(1).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(1);

                    int property2 = owlBeanList.get(2).getRuleHead().get(0);
                    int class4 = owlBeanList.get(2).getRuleHead().get(1);
                    addBudgetHotelDestination(class1, class2, property1, class3, property2, class4);
                } else if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 4000 && owlBeanList.get(2).getType() == 4000) {
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property = owlBeanList.get(1).getRuleHead().get(0);
                    int indual1 = owlBeanList.get(1).getRuleHead().get(1);
                    int indual2 = owlBeanList.get(2).getRuleHead().get(1);
                    addBudgetAccommodation(class1, class2, property, indual1, indual2);

                } else {
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property = owlBeanList.get(1).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(1);
                    int class4 = owlBeanList.get(2).getRuleHead().get(1);
                    addWomanCollege(class1, class2, property, class3, class4);
                }

            } else if (owlBeanList.size() == 4) {
                if (owlBeanList.get(0).getType() == 2002 && owlBeanList.get(1).getType() == 3005 && owlBeanList.get(2).getType() == 3005 && owlBeanList.get(3).getType() == 3005) {
                    int class2 = owlBeanList.get(0).getRuleHead().get(0);
                    int property1 = owlBeanList.get(1).getRuleHead().get(0);
                    int class3 = owlBeanList.get(1).getRuleHead().get(1);
                    int property2 = owlBeanList.get(2).getRuleHead().get(0);
                    int class4 = owlBeanList.get(2).getRuleHead().get(1);
                    int class5 = owlBeanList.get(3).getRuleHead().get(1);
                    addBackpackersDestination(class1, class2, property1, class3, property2, class4, class5);
                }
            }
        }
    }

    private static void addBudgetAccommodation(int class1, int class2, int property, int indual1, int indual2) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(38);
        dicOwlBean.setRuleHead(class1, class2, property, indual1, indual2);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(39);//class2
        dicOwlBean1.setRuleHead(class1, class2, property, indual1, indual2);
        String key1 = "*" + property + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);
    }

    private static void addBackpackersDestination(int class1, int class2, int property1, int class3, int property2, int class4, int class5) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(32);//class2
        dicOwlBean.setRuleHead(class1, class2, property1, class3, property2, class4, class5);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(33);//r
        dicOwlBean1.setRuleHead(class1, class2, property1, class3, property2, class4, class5);
        String key1 = "*" + property1 + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);

        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(34);//class3
        dicOwlBean2.setRuleHead(class1, class2, property1, class3, property2, class4, class5);
        String key2 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);

        DicOwlBean dicOwlBean3 = new DicOwlBean();
        dicOwlBean3.setType(35);//r
        dicOwlBean3.setRuleHead(class1, class2, property1, class3, property2, class4, class5);
        String key3 = "*" + property2 + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean3, key3);

        DicOwlBean dicOwlBean4 = new DicOwlBean();
        dicOwlBean4.setType(36);//class3
        dicOwlBean4.setRuleHead(class1, class2, property1, class3, property2, class4, class5);
        String key4 = "*0" + class4;
        DicOwlMap.addDicOwlMap(dicOwlBean4, key4);

        DicOwlBean dicOwlBean5 = new DicOwlBean();
        dicOwlBean5.setType(37);//class3
        dicOwlBean5.setRuleHead(class1, class2, property1, class3, property2, class4, class5);
        String key5 = "*0" + class5;
        DicOwlMap.addDicOwlMap(dicOwlBean5, key5);
    }

    private static void addBudgetHotelDestination(int class1, int class2, int property1, int class3, int property2, int class4) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(28);//class2
        dicOwlBean.setRuleHead(class1, class2, property1, class3, property2, class4);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(29);//r
        dicOwlBean1.setRuleHead(class1, class2, property1, class3, property2, class4);
        String key1 = "*" + property1 + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);

        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(30);//class3
        dicOwlBean2.setRuleHead(class1, class2, property1, class3, property2, class4);
        String key2 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);

        DicOwlBean dicOwlBean3 = new DicOwlBean();
        dicOwlBean3.setType(31);//class3
        dicOwlBean3.setRuleHead(class1, class2, property1, class3, property2, class4);
        String key3 = "*0" + class4;
        DicOwlMap.addDicOwlMap(dicOwlBean3, key3);
    }

    private static void addQuietDestination(int class1, int class2, int class3) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(26);//class2
        dicOwlBean.setRuleHead(class1, class2, class3);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(27);//class3
        dicOwlBean1.setRuleHead(class1, class2, class3);
        String key1 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);
    }

    private static void addFamilyDestination(int class1, int class2, int property1, int class3, int count, int property2, int class4) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(23);//class2
        dicOwlBean.setRuleHead(class1, class2, property1, class3, count, property2, class4);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(24);//r
        dicOwlBean1.setRuleHead(class1, class2, property1, class3, count, property2, class4);
        String key1 = "*" + property1 + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);

        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(25);//class3
        dicOwlBean2.setRuleHead(class1, class2, property1, class3, count, property2, class4);
        String key2 = "*" + property2 + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);
    }

    public static String find(int class2) {
        Map<String, Integer> encode = Dictionary.getEncode();
        for (Map.Entry<String, Integer> tmp : encode.entrySet()) {
            String ssTmp = tmp.getKey();
            int valueTmp = tmp.getValue();
            if (valueTmp == class2) {
                return ssTmp;
            }
        }
        return null;
    }



    private static void addEquiNonScienceStudent(int class1, int class2, int r, int class3) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(20);//class2
        dicOwlBean.setRuleHead(class1,class2,r,class3);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(21);//r
        dicOwlBean1.setRuleHead(class1,class2,r,class3);
        String key1 = "*" + r + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);
        if(class3 == 1){//owl:Thing
            return;
        }
        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(22);//class3
        dicOwlBean2.setRuleHead(class1,class2,r,class3);
        String key2 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);
    }

    private static void addEquiGraduateStudent(int class1, int property, int class3) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(18);//class2
        dicOwlBean.setRuleHead(class1,property,class3);
        String key = "*" + property + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(19);//class3
        dicOwlBean2.setRuleHead(class1,property,class3);
        String key2 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);
    }

    private static void addEquiClassSomeValue(int class1, int class2, int r, int class3) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(0);//class2
        dicOwlBean.setRuleHead(class1,class2,r,class3);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(1);//r
        dicOwlBean1.setRuleHead(class1,class2,r,class3);
        String key1 = "*" + r + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);
        if(class3 == 1){//owl:Thing
            return;
        }
        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(2);//class3
        dicOwlBean2.setRuleHead(class1,class2,r,class3);
        String key2 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);

    }

    private static void addWomanCollege(int class1, int class2, int property, int class3, int class4) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(14);
        dicOwlBean.setRuleHead(class1, class2, property, class3, class4);
        String key = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(15);
        dicOwlBean1.setRuleHead(class1, class2, property, class3, class4);
        String key1 = "*" + property + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);

        DicOwlBean dicOwlBean2 = new DicOwlBean();
        dicOwlBean2.setType(16);
        dicOwlBean2.setRuleHead(class1, class2, property, class3, class4);
        String key2 = "*0" + class3;
        DicOwlMap.addDicOwlMap(dicOwlBean2, key2);

        DicOwlBean dicOwlBean3 = new DicOwlBean();
        dicOwlBean3.setType(17);
        dicOwlBean3.setRuleHead(class1, class2, property, class3, class4);
        String key3 = "*0" + class4;
        DicOwlMap.addDicOwlMap(dicOwlBean3, key3);
    }

    private static void addEquiMin(int class1, int count, int property, int class2) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(12);
        dicOwlBean.setRuleHead(class1, count, property, class2);
        String key = "*" + property + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean, key);
        if (class2 == 1 ) return;
        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(13);//class2
        dicOwlBean1.setRuleHead(class1, count, property, class2);
        String key1 = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);

    }

    private static void addEquiSomeValue(int class1, int property, int class2) {
        DicOwlBean dicOwlBean = new DicOwlBean();
        dicOwlBean.setType(10);//class2
        dicOwlBean.setRuleHead(class1, property, class2);
        String key = "*" + property + "*";
        DicOwlMap.addDicOwlMap(dicOwlBean, key);

        DicOwlBean dicOwlBean1 = new DicOwlBean();
        dicOwlBean1.setType(11);//class2
        dicOwlBean1.setRuleHead(class1, property, class2);
        String key1 = "*0" + class2;
        DicOwlMap.addDicOwlMap(dicOwlBean1, key1);
    }


}
