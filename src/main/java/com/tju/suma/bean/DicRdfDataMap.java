package com.tju.suma.bean;

import com.tju.suma.index.ThreeKeyMap;
import com.tju.suma.index.TwoKeyMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DicRdfDataMap {

    private static final Map<Integer, DicRdfDataBean> dicDataMap=new ConcurrentHashMap<>();
    private static final Map<Integer,DicRdfDataBean> dicStashMap=new ConcurrentHashMap<>();
    private static final Map<Integer,DicRdfDataBean> dicIteratorMap=new ConcurrentHashMap<>();


    private DicRdfDataMap(){}

    public static Map<Integer, DicRdfDataBean> getDicDataMap(){ return dicDataMap; }
    public static Map<Integer,DicRdfDataBean> getDicStashMap(){ return dicStashMap; }
    public static Map<Integer,DicRdfDataBean> getDicIteratorMap(){ return dicIteratorMap; }




    public static DicRdfDataBean getDataBean(int index) {
        if(index < dicDataMap.size()){
            return dicDataMap.get(index);
        }
        else if(index < dicDataMap.size()+ dicIteratorMap.size()){
            return dicIteratorMap.get(index);
        }
        else if(index < dicDataMap.size()+ dicIteratorMap.size() + dicStashMap.size()){
            return dicStashMap.get(index);
        }
        else{
            System.out.println("out of index");
            return null;
        }
    }

    public static void addNewRdfDataBean( int rs, int rp, int ro) {
        int index = dicDataMap.size()+dicIteratorMap.size()+dicStashMap.size();
        DicRdfDataBean dicDataBean = new DicRdfDataBean();
        dicDataBean.setRs(rs);
        dicDataBean.setRp(rp);
        dicDataBean.setRo(ro);
        int nsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp, index);
        dicDataBean.setNsp(nsp, index);
        int nop = TwoKeyMap.getFirstIndexOpFromMap(rp, ro, index);
        dicDataBean.setNop(nop, index);
        dicDataBean.setNp(-1);
        dicStashMap.put(index, dicDataBean);
        ThreeKeyMap.checkDuplicate(rs, rp, ro, index);
    }

    public static void addSourceRdfDataBean(int index, int rs, int rp, int ro) {
        DicRdfDataBean dicDataBean = new DicRdfDataBean();
        dicDataBean.setRs(rs);
        dicDataBean.setRp(rp);
        dicDataBean.setRo(ro);
        int nsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp, index);
        dicDataBean.setNsp(nsp, index);
        int nop = TwoKeyMap.getFirstIndexOpFromMap(rp, ro, index);
        dicDataBean.setNop(nop, index);
        dicDataBean.setNp(-1);
        dicDataMap.put(index, dicDataBean);
        ThreeKeyMap.checkDuplicate(rs, rp, ro, index);
    }

    public static void addSourceRdfDataBean(int rs, int rp, int ro) {
        int index = dicDataMap.size();
        DicRdfDataBean dicDataBean = new DicRdfDataBean();
        dicDataBean.setRs(rs);
        dicDataBean.setRp(rp);
        dicDataBean.setRo(ro);
        int nsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp, index);
        dicDataBean.setNsp(nsp, index);
        int nop = TwoKeyMap.getFirstIndexOpFromMap(rp, ro, index);
        dicDataBean.setNop(nop, index);
        dicDataBean.setNp(-1);
        dicDataMap.put(index, dicDataBean);
        ThreeKeyMap.checkDuplicate(rs, rp, ro, index);
    }


    public static void addNewRdfDataBean( int rs, int rp, int ro, int nsp) {
        int index = dicDataMap.size()+dicIteratorMap.size()+dicStashMap.size();
        DicRdfDataBean dicDataBean = new DicRdfDataBean();
        dicDataBean.setRs(rs);
        dicDataBean.setRp(rp);
        dicDataBean.setRo(ro);
        dicDataBean.setNsp(nsp, index);
        int nop = TwoKeyMap.getFirstIndexOpFromMap(rp, ro, index);
        dicDataBean.setNop(nop, index);
        dicDataBean.setNp(-1);
        dicStashMap.put(index, dicDataBean);
        ThreeKeyMap.checkDuplicate(rs, rp, ro, index);
    }
}
