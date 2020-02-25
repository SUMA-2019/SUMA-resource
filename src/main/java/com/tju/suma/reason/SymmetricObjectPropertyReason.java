package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;

public class SymmetricObjectPropertyReason {
    static void reason(int rs, int rp, int ro) {
        if(!ThreeKeyMap.checkDuplicate(ro, rp, rs)) {
            DicRdfDataMap.addNewRdfDataBean(ro, rp, rs);
        }

    }
}
