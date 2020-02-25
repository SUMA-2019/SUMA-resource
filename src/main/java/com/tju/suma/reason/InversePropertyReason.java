package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;

import java.util.List;
import java.util.Map;

public class InversePropertyReason {
    public static void reason(Map<Integer, DicRdfDataBean> totalData, Map<Integer, DicRdfDataBean> iteratorMap, Map<Integer, DicRdfDataBean> stashMap, int rs, List<Integer> head, int ro) {
        int rp = head.get(0);
        int index = totalData.size() + iteratorMap.size() + stashMap.size();
        if (!ThreeKeyMap.checkDuplicate(ro, rp, rs, index)) {
            DicRdfDataMap.addNewRdfDataBean(ro, rp, rs);
        }
    }
}
