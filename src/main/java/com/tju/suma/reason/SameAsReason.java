package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;
import com.tju.suma.index.TwoKeyMap;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SameAsReason {
    public static List<HashSet<Integer>> equiPool = new ArrayList<>();
    static Map<Integer, Integer> equiPoolIndex = new ConcurrentHashMap<>();
    static Map<Integer, Integer> equiRepresentation = new ConcurrentHashMap<>();
    private static Logger log = Logger.getLogger(SameAsReason.class);
    static boolean boolSameAs(int rs) {
        if(equiRepresentation.containsKey(rs)){
            return equiRepresentation.get(rs) == rs;
        }
        return true;
    }
    static void loopRsRpFindRo(int rs, int rp, int ro) {
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp);
        if(firstTripleIsp == -1){ return; }

        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do{
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at loopRsRpFindRo");
            indexNew = dicDataBeanIterator.getNsp();
            int roTmp = dicDataBeanIterator.getRo();
            if(ro != roTmp) {
                comEquiPool(ro, roTmp);
            }
        }while(indexNew != -1);
    }


    private static void addEquivRsTriple(HashSet<Integer> tmpPool, int tmp) {
        List<Integer> rpRoTriples = TwoKeyMap.findAllTriplesFromRs(tmp);
        for (int tmp22 : tmpPool) {
            if (tmp22 != tmp) {
                Iterator<Integer> rpRoList = rpRoTriples.iterator();
                while (rpRoList.hasNext()) {
                    int rp = rpRoList.next();
                    int ro = rpRoList.next();
                    if (!ThreeKeyMap.checkDuplicate(tmp22, rp, ro)) {
                        DicRdfDataMap.addSourceRdfDataBean(tmp22, rp, ro);
                    }
                }
            }

        }
    }
    public static void addEquivIndividual() {
        Map<Integer, DicRdfDataBean> totalData = DicRdfDataMap.getDicDataMap();
        for (HashSet<Integer> tmpPool : equiPool) {
            for (int tmp : tmpPool) {
                addEquivRsTriple(tmpPool, tmp);
                addEquivRoTriple(tmpPool, tmp);
            }
        }
        log.info("The number of facts after adding sameAs: "+totalData.size());
    }

    private static void addEquivRoTriple(HashSet<Integer> tmpPool, int tmp) {
        List<Integer> rsRpTriples = TwoKeyMap.findAllTriplesFromRo(tmp);
        for (int tmp22 : tmpPool) {
            if (tmp22 != tmp) {
                Iterator<Integer> rsRpList = rsRpTriples.iterator();
                while (rsRpList.hasNext()) {
                    int rs = rsRpList.next();
                    int rp = rsRpList.next();
                    if (!ThreeKeyMap.checkDuplicate(rs, rp, tmp22)) {
                        DicRdfDataMap.addSourceRdfDataBean(rs, rp, tmp22);
                    }
                }
            }

        }
    }
    static void loopRpRoFindRs(int rs, int rp, int ro) {
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(rp, ro);
        if(firstTripleIop == -1){ return; }

        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do{
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at loopRpRoFindRs");
            indexNew = dicDataBeanIterator.getNop();
            int rsTmp = dicDataBeanIterator.getRs();
            if(rs != rsTmp) {
                comEquiPool(rs, rsTmp);
            }
        }while(indexNew != -1);
    }

    private static void comEquiPool(int rs, int rsTmp) {
        int rsEquiv = findEquivPoolIndex(rs);
        int rsTmpEquiv = findEquivPoolIndex(rsTmp);
        if(rsTmpEquiv ==0 && rsEquiv == 0){
            HashSet<Integer> newPool = new HashSet<>();
            newPool.add(rs);
            newPool.add(rsTmp);
            equiPool.add(newPool);
            //避免0冲突，存的是index of pool +1
            equiPoolIndex.put(rs,equiPool.size());
            equiPoolIndex.put(rsTmp,equiPool.size());
        }
        else if(rsTmpEquiv !=0 && rsEquiv != 0){
            if(rsTmpEquiv != rsEquiv){
                //池子合并
                int minn;
                int maxx;
                if(rsTmpEquiv < rsEquiv){
                    minn = rsTmpEquiv;
                    maxx = rsEquiv;
                }
                else{
                    maxx = rsTmpEquiv;
                    minn = rsEquiv;
                }
                //池子maxx装进池子minn
                HashSet<Integer> temp2Pool = equiPool.get(maxx - 1);
                HashSet<Integer> temp1Pool = equiPool.get(minn - 1);
                for (Integer tmp : temp2Pool) {
                    temp1Pool.add(tmp);
                    equiPoolIndex.put(tmp, minn);
                }
                temp2Pool.clear();
            }
        }
        else if(rsTmpEquiv == 0){
            HashSet<Integer> tempPool = equiPool.get(rsEquiv - 1);
            tempPool.add(rsTmp);
            equiPoolIndex.put(rsTmp,rsEquiv);
        }
        else {
            HashSet<Integer> tempPool = equiPool.get(rsTmpEquiv - 1);
            tempPool.add(rs);
            equiPoolIndex.put(rs,rsTmpEquiv);
        }
        reFreshEquiRepre(rs);
    }

    private static void reFreshEquiRepre(int rs) {
        int poolIndex = equiPoolIndex.get(rs)-1;
        HashSet<Integer> poolTmp = equiPool.get(poolIndex);
        int minNew = getMin(poolTmp);
        for (int ii : poolTmp) {
            if (equiRepresentation.containsKey(ii)) {
                int tmp = equiRepresentation.get(ii);
                if (minNew != ii && minNew != tmp) {
                    TwoKeyMap.replaceWithMinIsp(ii, minNew);
                    TwoKeyMap.replaceWithMinIop(ii, minNew);
                }
            } else {
                if (minNew == ii) {
                    equiRepresentation.put(ii, ii);
                } else {
                    equiRepresentation.put(ii, minNew);
                    TwoKeyMap.replaceWithMinIsp(ii, minNew);
                    TwoKeyMap.replaceWithMinIop(ii, minNew);
                }
            }
        }
    }





    private static int getMin(HashSet<Integer> poolTmp) {
        Iterator<Integer> i = poolTmp.iterator();
        int min = Integer.MAX_VALUE;
        int ii;
        while(i.hasNext()){
            ii = i.next();
            if(ii <= min){
                min = ii;
            }
        }
        return min;
    }

    public static int findEquivPoolIndex(int rs) {
        if(equiPoolIndex.containsKey(rs)){
            return equiPoolIndex.get(rs);
        }
        else{
            equiPoolIndex.put(rs,0);
            return 0;
        }
    }
}
