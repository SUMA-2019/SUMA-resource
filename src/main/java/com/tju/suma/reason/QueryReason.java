package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;
import com.tju.suma.index.TwoKeyMap;
import com.tju.suma.rewrite.EquiClassRuleRewrite;


import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import static com.tju.suma.reason.DicSerialReason.*;

public class QueryReason {

    static void type55Reason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int classValue = head.get(1);
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        if (!ThreeKeyMap.checkDuplicate(ro, typeEncode, classValue)) {
            DicRdfDataMap.addNewRdfDataBean(ro, typeEncode, classValue);
        }
    }

    static void type22Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property = head.get(2);
        int class3 = head.get(3);
        List<Integer> rsList = queryRsByRpRo(property, rs);
        for (int rsTmp : rsList) {
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (checkAllValue(rsTmp, property, class3)) {
                    DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                }
            }
        }
    }

    static void type21Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property = head.get(2);
        int class3 = head.get(3);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) return;
        if (checkAllValue(rs, property, class3)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }

    static void type20Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property = head.get(2);
        int class3 = head.get(3);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        if (checkAllValue(rs, property, class3)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }

    static void type19Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property = head.get(1);
        int class3 = head.get(2);
        List<Integer> rsList = queryRsByRpRo(property, rs);
        for (int rsTmp : rsList) {
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (checkAllValue(rsTmp, property, class3)) {
                    DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                }
            }
        }
    }

    private static List<Integer> queryRsByRpRo(int property, int rs) {
        List<Integer> rsList = new ArrayList<>();
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property, rs);
        if (firstTripleIop == -1) {
            return rsList;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at queryRsByRpRo");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            rsList.add(rsTmp);
        } while (indexNew != -1);
        return rsList;
    }

    static void type18Reason(int rs, int rp, List<Integer> head) {
        int class1 = head.get(0);
        int class3 = head.get(2);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        if (checkAllValue(rs, rp, class3)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }


    static void type16Reason(int rs, List<Integer> head) {
        //rs type class3
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property = head.get(2);
        int class3 = head.get(3);
        int class4 = head.get(4);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property, rs);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type16Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();

            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class2)) {
                    if (checkAllValue(rsTmp, property, class3)) {
                        if (checkAllValue(rsTmp, property, class4)) {
                            DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                        }
                    }
                }
            }
        } while (indexNew != -1);
    }

    static boolean checkAllValue(int rs, int property, int class3) {
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, property);
        if (firstTripleIsp == -1) {
            return true;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at checkAllValue");
            indexNew = dicDataBeanIterator.getNsp();
            int ro = dicDataBeanIterator.getRo();
            if (!ThreeKeyMap.checkDuplicate(ro, typeEncode, class3))
                return false;
        } while (indexNew != -1);
        return true;
    }

    static void type15Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property = head.get(2);
        int class3 = head.get(3);
        int class4 = head.get(4);
        if (checkClass(rs, class1)) return;
        if (!checkClass(rs, class2)) return;
        if (checkAllValue(rs, property, class3) && checkAllValue(rs, property, class4))
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
    }

    private static boolean checkClass(int rs, int class1) {
        return ThreeKeyMap.checkDuplicate(rs, typeEncode, class1);
    }

    static void type14Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property = head.get(2);
        int class3 = head.get(3);
        int class4 = head.get(4);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        if (checkAllValue(rs, property, class3) && checkAllValue(rs, property, class4))
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
    }


    static void type12Reason(int rs, int rp, List<Integer> head) {
        int class1 = head.get(0);
        int count = head.get(1);
        int class2 = head.get(3);
        int count1 = 0;
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp);
        if (firstTripleIsp == -1) return;
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type12Reason");
            indexNew = dicDataBeanIterator.getNsp();
            int ro = dicDataBeanIterator.getRo();
            if (class2 == 1) {
                count1++;
            } else {
                if (ThreeKeyMap.checkDuplicate(ro, typeEncode, class2)) {
                    count1++;
                }
            }

        } while (indexNew != -1);
        if (count1 >= count) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }

    }

    static void type11Reason(int ro, List<Integer> head) {
        int class1 = head.get(0);
        int property = head.get(1);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property, ro);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type11Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rs = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
                DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
            }
        } while (indexNew != -1);
    }

    static void type10Reason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(2);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) return;
        if (class2 == 1) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
            return;
        }
        if (ThreeKeyMap.checkDuplicate(ro, typeEncode, class2)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }

    }

    static void equivalentClass3Reason(int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int r = head.get(2);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(r, ro);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at equivalentClass3Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rs = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
                    DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
                }
            }
        } while (indexNew != -1);

    }

    static void equivalentRoleReason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int class3 = head.get(3);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        boolean flag1 = ThreeKeyMap.checkDuplicate(rs, typeEncode, class2);
        if (flag1) {
            if (class3 == 1) {
                DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
            } else {
                boolean flag2 = ThreeKeyMap.checkDuplicate(ro, typeEncode, class3);
                if (flag2) {
                    DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
                }
            }
        }

    }

    static void equivalentClass2Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int r = head.get(2);
        int class3 = head.get(3);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, r);
        if (firstTripleIsp == -1) {
            return;
        }
        if (class3 == 1) {//owl:Thing
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at equivalentClass2Reason");
            indexNew = dicDataBeanIterator.getNsp();
            int ro = dicDataBeanIterator.getRo();
            if (ThreeKeyMap.checkDuplicate(ro, typeEncode, class3)) {
                DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
                return;
            }
        } while (indexNew != -1);

    }

    public static void type23Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int count = head.get(4);
        int property2 = head.get(5);
        int class4 = head.get(6);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, property1);
        if (firstTripleIsp == -1) {
            return;
        }
        if (class3 == 1) {
            int count1 = checkMinCount(rs, property2, class4);
            if (count1 >= count) {
                DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
            }
        }

    }

    private static int checkMinCount(int rs, int property2, int class4) {
        int count1 = 0;
        int firstTripleIsp1 = TwoKeyMap.getFirstIndexSpFromMap(rs, property2);
        if (firstTripleIsp1 == -1) return 0;
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp1;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at checkMinCount");
            indexNew = dicDataBeanIterator.getNsp();
            int ro = dicDataBeanIterator.getRo();
            if (class4 == 1) {
                count1++;
            } else {
                if (ThreeKeyMap.checkDuplicate(ro, typeEncode, class4)) {
                    count1++;
                }
            }

        } while (indexNew != -1);
        return count1;
    }

    public static void type24Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int count = head.get(4);
        int property2 = head.get(5);
        int class4 = head.get(6);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        int count1 = checkMinCount(rs, property2, class4);
        if (count1 >= count) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }

    public static void type25Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int count = head.get(4);
        int property2 = head.get(5);
        int class4 = head.get(6);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        } else if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, property1);
        if (firstTripleIsp == -1) {
            return;
        }
        int count1 = checkMinCount(rs, property2, class4);
        if (count1 >= count) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }

    public static void type26Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class3 = head.get(2);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class3)) {
            return;
        }
        DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
    }

    public static void type27Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
    }

    public static void type28Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int property2 = head.get(4);
        int class4 = head.get(5);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, property1);
        if (firstTripleIsp == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type28Reason");
            indexNew = dicDataBeanIterator.getNsp();
            int ro1 = dicDataBeanIterator.getRo();
            if (ThreeKeyMap.checkDuplicate(ro1, typeEncode, class3)) {
                if (ThreeKeyMap.checkDuplicate(ro1, typeEncode, class4)) {
                    DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
                    return;
                }
            }
        } while (indexNew != -1);


    }

    public static void type29Reason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int property2 = head.get(4);
        int class4 = head.get(5);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        if (ThreeKeyMap.checkDuplicate(ro, typeEncode, class3)) {
            if (ThreeKeyMap.checkDuplicate(ro, typeEncode, class4)) {
                DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
            }
        }
    }

    public static void type30Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class4 = head.get(5);
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class4)) {
            return;
        }
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property1, rs);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type30Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class2)) {
                    DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                }
            }
        } while (indexNew != -1);
    }

    public static void type31Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class3 = head.get(3);
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class3)) {
            return;
        }
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property1, rs);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type31Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class2)) {
                    DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                }
            }
        } while (indexNew != -1);
    }

    public static void type32Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int property2 = head.get(4);
        int class4 = head.get(5);
        int class5 = head.get(6);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!checkSomeValue(rs, property1, class3)) return;
        if (checkSomeValue(rs, property2, class4) || checkSomeValue(rs, property2, class5)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }

    private static boolean checkSomeValue(int rs, int property1, int class3) {
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, property1);
        if (firstTripleIsp == -1) {
            return false;
        }
        if (class3 == 1) {
            return true;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at checkSomeValue");
            indexNew = dicDataBeanIterator.getNsp();
            int ro1 = dicDataBeanIterator.getRo();
            if (ThreeKeyMap.checkDuplicate(ro1, typeEncode, class3)) {
                return true;
            }
        } while (indexNew != -1);
        return false;
    }

    public static void type33Reason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int class3 = head.get(3);
        int property2 = head.get(4);
        int class4 = head.get(5);
        int class5 = head.get(6);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(ro, typeEncode, class3)) {
            return;
        }
        if (checkSomeValue(rs, property2, class4) || checkSomeValue(rs, property2, class5)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }

    }

    public static void type34Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int property2 = head.get(4);
        int class4 = head.get(5);
        int class5 = head.get(6);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property1, rs);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type34Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class2)) {
                    if (checkSomeValue(rsTmp, property2, class4) || checkSomeValue(rsTmp, property2, class5)) {
                        DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                    }
                }
            }
        } while (indexNew != -1);
    }

    public static void type35Reason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int class4 = head.get(5);
        int class5 = head.get(6);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(ro, typeEncode, class4) && !ThreeKeyMap.checkDuplicate(ro, typeEncode, class5)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        if (checkSomeValue(rs, property1, class3)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }

    public static void type36Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int property2 = head.get(4);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property2, rs);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type36Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class2)) {
                    if (checkSomeValue(rsTmp, property1, class3)) {
                        DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                    }
                }
            }
        } while (indexNew != -1);
    }

    public static void type37Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int property1 = head.get(2);
        int class3 = head.get(3);
        int property2 = head.get(4);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(property2, rs);
        if (firstTripleIop == -1) {
            return;
        }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do {
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at type37Reason");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            if (!ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class1)) {
                if (ThreeKeyMap.checkDuplicate(rsTmp, typeEncode, class2)) {
                    if (checkSomeValue(rsTmp, property1, class3)) {
                        DicRdfDataMap.addNewRdfDataBean(rsTmp, typeEncode, class1);
                    }
                }
            }
        } while (indexNew != -1);
    }

    public static void type38Reason(int rs, List<Integer> head) {
        int class1 = head.get(0);
        int property = head.get(2);
        int indual1 = head.get(3);
        int indual2 = head.get(4);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (ThreeKeyMap.checkDuplicate(rs, property, indual1) || ThreeKeyMap.checkDuplicate(rs, property, indual2)) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }

    }

    public static void type39Reason(int rs, int ro, List<Integer> head) {
        int class1 = head.get(0);
        int class2 = head.get(1);
        int indual1 = head.get(3);
        int indual2 = head.get(4);
        if (ThreeKeyMap.checkDuplicate(rs, typeEncode, class1)) {
            return;
        }
        if (!ThreeKeyMap.checkDuplicate(rs, typeEncode, class2)) {
            return;
        }
        if (ro == indual1 || ro == indual2) {
            DicRdfDataMap.addNewRdfDataBean(rs, typeEncode, class1);
        }
    }
}
