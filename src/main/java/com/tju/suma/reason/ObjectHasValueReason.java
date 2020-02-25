package com.tju.suma.reason;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;

import java.util.List;

public class ObjectHasValueReason {
    public static void reason(int rs, List<Integer> head) {
        int rp = head.get(0);
        int class2 = head.get(1);
        if(!ThreeKeyMap.checkDuplicate(rs, rp, class2)) {
            DicRdfDataMap.addNewRdfDataBean(rs, rp, class2);
        }
    }
}
