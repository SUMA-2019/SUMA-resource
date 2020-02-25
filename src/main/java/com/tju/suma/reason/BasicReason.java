package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;

import java.util.List;
import java.util.Map;

public class BasicReason {
    static void reason(Map<Integer, DicRdfDataBean> totalData, Map<Integer, DicRdfDataBean> iteratorMap, Map<Integer, DicRdfDataBean> stashMap, int rs, List<Integer> head) {
        int ro = head.get(0);
        int index = totalData.size()+iteratorMap.size()+stashMap.size();
        if(!ThreeKeyMap.checkDuplicate(rs, DicSerialReason.typeEncode,ro,index)){
            DicRdfDataMap.addNewRdfDataBean(rs, DicSerialReason.typeEncode, ro);
        }
    }
}
