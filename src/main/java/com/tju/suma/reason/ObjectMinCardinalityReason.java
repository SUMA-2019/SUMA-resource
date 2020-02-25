package com.tju.suma.reason;

import com.tju.suma.bean.DicRdfDataBean;
import com.tju.suma.bean.DicRdfDataMap;
import com.tju.suma.index.ThreeKeyMap;
import com.tju.suma.index.TwoKeyMap;

import java.util.List;
import java.util.Objects;

import static com.tju.suma.reason.DicSerialReason.*;

public class ObjectMinCardinalityReason {
    static void reason(int rs, List<Integer> head) {
        int cardinality = head.get(0);
        int rp = head.get(1);
        int class2 = head.get(2);
        int firstTripleIsp = TwoKeyMap.getFirstIndexSpFromMap(rs, rp);
        if (firstTripleIsp == -1) {
            //add cardinality
            int i = 0;
            while (i < cardinality) {
                addSomeValueFrom(rs, rp, class2);
                i++;
            }
        } else {
            int exist = 0;
            DicRdfDataBean dicDataBeanIterator;
            int indexNew = firstTripleIsp;
            do {
                dicDataBeanIterator = DicRdfDataMap.getDataBean(indexNew);
                Objects.requireNonNull(dicDataBeanIterator, "dicDataBeanIterator is null at ObjectMinCardinalityReason");
                indexNew = dicDataBeanIterator.getNsp();
                if (class2 == 1) {
                    exist++;
                } else {
                    int ro1 = dicDataBeanIterator.getRo();
                    if (ThreeKeyMap.checkDuplicate(ro1, typeEncode, class2)) {
                        //rs rp ro1
                        exist++;
                    }
                }

            } while (indexNew != -1);
            int i = 0;
            if ((cardinality - exist) <= 0) {
                return;
            }
            while (i < (cardinality - exist)) {
                addSomeValueFrom(rs, rp, class2);
                i++;
            }
        }
    }


    private static void addSomeValueFrom(int rs, int rp, int class2) {
        int ro = anonymous;
        if (!someValueFlag && rs < 0) {
            someValue++;
            someValueFlag = true;
        }
        DicRdfDataMap.addNewRdfDataBean(rs, rp, ro);
        anonymous--;
        DicRdfDataMap.addNewRdfDataBean(ro, typeEncode, class2);
    }
}
