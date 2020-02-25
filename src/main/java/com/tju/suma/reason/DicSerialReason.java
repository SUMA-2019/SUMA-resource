package com.tju.suma.reason;

import com.tju.suma.bean.DicOwlBean;
import com.tju.suma.bean.DicOwlMap;
import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DicSerialReason {
    public static int anonymous = -2;
    public static final int typeEncode = 0;
    public static int someValue = 1;
    public static boolean someValueFlag = false;
    private static Logger log = Logger.getLogger(DicSerialReason.class);


    public static void reason(int n) {
        int loopCount = 1;
        //遍历数据
        Map<Integer, DicRdfDataBean> totalData = DicRdfDataMap.getDicDataMap();
        //存放每次新生成的数据,每次循环结束把新数据copy到iteratorMap进行第二轮迭代
        Map<Integer, DicRdfDataBean> stashMap = DicRdfDataMap.getDicStashMap();
        //迭代数据，第二轮以后迭代 iteratorMap，每次结束把数据copy到totalData进行存储
        Map<Integer, DicRdfDataBean> iteratorMap = DicRdfDataMap.getDicIteratorMap();
        //规则
        Map<String, List<DicOwlBean>> totalRule = DicOwlMap.getRuleMap();
        //索引
        log.info("----------------------Start Materialization--------------------------");
        while (true) {
            log.info("loopCount " + loopCount + " dataCount "+(totalData.size() + iteratorMap.size()));
            someValueFlag = false;
            Iterator<Map.Entry<Integer, DicRdfDataBean>> entries;
            if (loopCount == 1) {
                entries = totalData.entrySet().iterator();
            } else {
                entries = iteratorMap.entrySet().iterator();
            }
            try {
                while (entries.hasNext()) {
                    Map.Entry<Integer, DicRdfDataBean> entry = entries.next();
                    DicRdfDataBean rdfData = entry.getValue();
                    List<String> ruleKey = new ArrayList<>();

                    int Rs = rdfData.getRs();
                    int Rp = rdfData.getRp();
                    int Ro = rdfData.getRo();
                    boolean rsBool = SameAsReason.boolSameAs(Rs);
                    boolean rpBool = SameAsReason.boolSameAs(Rp);
                    boolean roBool = SameAsReason.boolSameAs(Ro);
                    if (!(rsBool && rpBool && roBool)) {
                        continue;
                    }
                    convertDataToRuleKey(ruleKey, Rp, Ro);

                    ruleKey.forEach(str -> {
                        if (totalRule.containsKey(str)) {
                            List<DicOwlBean> OwlRule = totalRule.get(str);
                            for (DicOwlBean typeHead : OwlRule) {
                                int type = typeHead.getType();
                                List<Integer> head = typeHead.getRuleHead();
                                switchReasonType(totalData, stashMap, iteratorMap, Rs, Rp, Ro, type, head);

                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("happen error");
            }
            if (stashMap.size() == 0) {
                //没有新数据产生
                log.info("No new data was generated!");
                totalData.putAll(iteratorMap);
                stashMap.clear();
                iteratorMap.clear();
                break;
            }
            if (someValue >= 20) {
                log.info(20 + "-step universal model is finished");
                totalData.putAll(stashMap);
                totalData.putAll(iteratorMap);
                stashMap.clear();
                iteratorMap.clear();
                break;
            }
            if (loopCount >= n) {
                log.info(n + "-step universal model is finished");
                totalData.putAll(stashMap);
                totalData.putAll(iteratorMap);
                stashMap.clear();
                iteratorMap.clear();
                break;
            }
            totalData.putAll(iteratorMap);
            iteratorMap.clear();
            iteratorMap.putAll(stashMap);
            stashMap.clear();
            loopCount++;

        }

    }

    private static void switchReasonType(Map<Integer, DicRdfDataBean> totalData, Map<Integer, DicRdfDataBean> stashMap, Map<Integer, DicRdfDataBean> iteratorMap, int rs, int rp, int ro, int type, List<Integer> head) {
        switch (type) {
            case 2013://SubObjectPropertyOf 2013
                SubPropertyReason.reason(totalData, iteratorMap, stashMap, rs, head, ro);
                break;
            case 2022://ObjectPropertyDomain 2022
            case 2002://SubClassOf 2002
                BasicReason.reason(totalData, iteratorMap, stashMap, rs, head);
                break;
            case 2023://ObjectPropertyRange 2023
                BasicReason.reason(totalData, iteratorMap, stashMap, ro, head);
                break;
            case 3005:
                ObjectSomeValuesFromReason.reason(totalData, iteratorMap, stashMap, rs, head);
                break;
            case 0:
                QueryReason.equivalentClass2Reason(rs, head);
                break;
            case 1:
                QueryReason.equivalentRoleReason(rs, ro, head);
                break;
            case 2:
                QueryReason.equivalentClass3Reason(rs, head);
                break;
            case 2019:
                TransitiveReason.reason(rs, rp, ro);
                break;
            case 2014://InverseProperty = 2014;
                InversePropertyReason.reason(totalData, iteratorMap, stashMap, rs, head, ro);
                break;
            case 2012://EquivalentProperty = 2012;
                EquivalentPropertyReason.reason(totalData, iteratorMap, stashMap, rs, head, ro);
                break;
            case 3006:
                ObjectAllValuesFromReason.reason(rs, head);
                break;
            case 3007:
                ObjectHasValueReason.reason(rs, head);
                break;
            case 3008:
                ObjectMinCardinalityReason.reason(rs, head);
                break;
            case -3006://ObjectAllValuesFrom
                ObjectAllValuesFromReason.inverseReason(rs, head);
                break;
            case 2017:
                SymmetricObjectPropertyReason.reason(rs, rp, ro);
                break;
            case 2016:
                FunctionalObjectPropertyReason.inverseReason(rs, rp, ro);
                break;
            case 2015:
                FunctionalObjectPropertyReason.reason(rs, rp, ro);
                break;
            case 10:
                QueryReason.type10Reason(rs, ro, head);
                break;
            case 11:
                QueryReason.type11Reason(ro, head);
                break;
            case 12:
                QueryReason.type12Reason(rs, rp, head);
                break;
            case 14:
                QueryReason.type14Reason(rs, head);
                break;
            case 15:
                QueryReason.type15Reason(rs, head);
                break;
            case 16:
            case 17:
                QueryReason.type16Reason(rs, head);
                break;
            case 18:
                QueryReason.type18Reason(rs, rp, head);
                break;
            case 19:
                QueryReason.type19Reason(rs, head);
                break;
            case 20:
                QueryReason.type20Reason(rs, head);
                break;
            case 21:
                QueryReason.type21Reason(rs, head);
                break;
            case 22:
                QueryReason.type22Reason(rs, head);
                break;
            case 23:
                QueryReason.type23Reason(rs, head);
                break;
            case 24:
                QueryReason.type24Reason(rs, head);
                break;
            case 25:
                QueryReason.type25Reason(rs, head);
                break;
            case 26:
                QueryReason.type26Reason(rs, head);
                break;
            case 27:
                QueryReason.type27Reason(rs, head);
                break;
            case 28:
                QueryReason.type28Reason(rs, head);
                break;
            case 29:
                QueryReason.type29Reason(rs, ro, head);
                break;
            case 30:
                QueryReason.type30Reason(rs, head);
                break;
            case 31:
                QueryReason.type31Reason(rs, head);
                break;
            case 32:
                QueryReason.type32Reason(rs, head);
                break;
            case 33:
                QueryReason.type33Reason(rs, ro, head);
                break;
            case 34:
                QueryReason.type34Reason(rs, head);
                break;
            case 35:
                QueryReason.type35Reason(rs, ro, head);
                break;
            case 36:
                QueryReason.type36Reason(rs, head);
                break;
            case 37:
                QueryReason.type37Reason(rs, head);
                break;
            case 38:
                QueryReason.type38Reason(rs, head);
                break;
            case 39:
                QueryReason.type39Reason(rs, ro, head);
                break;
            case 55:
                QueryReason.type55Reason(rs, ro, head);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }


    private static void convertDataToRuleKey(List<String> ruleKey, int rp, int ro) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("*");
        sBuilder.append(rp);
        sBuilder.append(ro);
        String key1 = sBuilder.toString();
        ruleKey.add(key1);
        sBuilder.setLength(0);
        String key2 = sBuilder.append("*").append(rp).append("*").toString();
        ruleKey.add(key2);
    }
}
