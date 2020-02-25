package com.tju.suma.reason;

import java.util.HashSet;

public class FunctionalObjectPropertyReason {
    static void reason(int rs, int rp, int ro) {
        int rsEquiv = SameAsReason.findEquivPoolIndex(rs);
        //没有相等individual
        if (rsEquiv == 0) {
            SameAsReason.loopRsRpFindRo(rs, rp, ro);
        } else {
            HashSet<Integer> set = SameAsReason.equiPool.get(rsEquiv - 1);
            for (Integer tmp : set) {
                SameAsReason.loopRsRpFindRo(tmp, rp, ro);
            }
        }
    }

    static void inverseReason(int rs, int rp, int ro) {
        int roEquiv = SameAsReason.findEquivPoolIndex(ro);
        //没有相等individual
        if (roEquiv == 0) {
            SameAsReason.loopRpRoFindRs(rs, rp, ro);
        } else {
            HashSet<Integer> set = SameAsReason.equiPool.get(roEquiv - 1);
            for (Integer tmp : set) {
                SameAsReason.loopRpRoFindRs(rs, rp, tmp);
            }
        }
    }
}
