package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;
import com.tju.suma.index.TwoKeyMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tju.suma.reason.DicSerialReason.*;

public class ObjectSomeValuesFromReason {
    static void reason(Map<Integer, DicRdfDataBean> totalData, Map<Integer, DicRdfDataBean> iteratorMap, Map<Integer, DicRdfDataBean> stashMap, int rs, List<Integer> head) {
        int rp = head.get(0);
        int class2 = head.get(1);
        int index = totalData.size()+iteratorMap.size()+stashMap.size();
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp, index);
        if(firstTripleIsp != -1){
            if(class2 == 1){
                return;
            }
            DicRdfDataBean dicDataBeanIterator;
            int indexNew = firstTripleIsp;
            do{
                dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
                Objects.requireNonNull(dicDataBeanIterator,"dicDataBeanIterator at ObjectSomeValuesFromReason");
                indexNew = dicDataBeanIterator.getNsp();
                int ro = dicDataBeanIterator.getRo();
                if(ThreeKeyMap.checkDuplicate(ro, typeEncode, class2)){
                    return;
                }
            }while(indexNew != -1);
        }
        addSomeValueFrom(rs, rp, class2, firstTripleIsp);
    }
    private static void addSomeValueFrom(int rs, int rp, int class2, int firstTripleIsp) {
        int ro = anonymous;
        if (!someValueFlag && rs < 0) {
            someValue++;
            someValueFlag = true;
        }
        DicRdfDataMap.addNewRdfDataBean(rs, rp, ro, firstTripleIsp);
        anonymous--;
        DicRdfDataMap.addNewRdfDataBean(ro, typeEncode, class2);
    }
}
