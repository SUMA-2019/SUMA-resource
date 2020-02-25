package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;
import com.tju.suma.index.TwoKeyMap;

import java.util.List;
import java.util.Objects;

import static com.tju.suma.reason.DicSerialReason.typeEncode;

public class ObjectAllValuesFromReason {
    static void reason(int rs, List<Integer> head) {
        int rp = head.get(0);
        int class2 = head.get(1);
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp);
        if(firstTripleIsp == -1){ return; }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIsp;
        do{
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator at reason is null");
            indexNew = dicDataBeanIterator.getNsp();
            int ro1 = dicDataBeanIterator.getRo();
            if(!ThreeKeyMap.checkDuplicate(ro1, typeEncode, class2)) {
                //rs rp ro1
                DicRdfDataMap.addNewRdfDataBean(ro1, typeEncode, class2);
            }
        }while(indexNew != -1);
    }
    static void inverseReason(int rs, List<Integer> head) {
        int rp = head.get(0);
        int class2 = head.get(1);
        int firstTripleIop = TwoKeyMap.getFirstIndexOpFromMap(rp, rs);
        if(firstTripleIop == -1){ return; }
        DicRdfDataBean dicDataBeanIterator;
        int indexNew = firstTripleIop;
        do{
            dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
            Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator at inverseReason is null");
            indexNew = dicDataBeanIterator.getNop();
            int rs1 = dicDataBeanIterator.getRs();
            if(!ThreeKeyMap.checkDuplicate(rs1, typeEncode, class2)) {
                //rs rp ro1
                DicRdfDataMap.addNewRdfDataBean(rs1, typeEncode, class2);
            }
        }while(indexNew != -1);
    }
}
