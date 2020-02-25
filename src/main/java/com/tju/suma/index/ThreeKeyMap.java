package com.tju.suma.index;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ThreeKeyMap {
    private static final Map<Integer, Map<Integer,Map <Integer,Integer>>> IndexThreeKey = new ConcurrentHashMap<>();

    public static boolean checkDuplicate(int rs, int rp, int ro, int index) {
        if (IndexThreeKey.containsKey(rp)) {
            //rp rs ro index
            Map<Integer, Map<Integer, Integer>> IndexTwoKey = IndexThreeKey.get(rp);
            if(IndexTwoKey.containsKey(rs)){
                Map<Integer, Integer> IndexOneKey = IndexTwoKey.get(rs);
                if(IndexOneKey.containsKey(ro)){
                    return true;
                }
                else{
                    IndexOneKey.put(ro, index);
                    return false;
                }
            }
            else{
                IndexTwoKey.put(rs, new HashMap<Integer, Integer>() {
                    {put(ro, index);}
                });
                return false;

            }

        } else {
            IndexThreeKey.put(rp, new HashMap<Integer, Map <Integer,Integer>>(){{
                put(rs, new HashMap<Integer, Integer>(){{put(ro, index);}});
            }});
            return false;
        }
    }
    public static boolean checkDuplicate(int rs, int rp, int ro) {
        if (IndexThreeKey.containsKey(rp)) {
            //rp rs ro index
            Map<Integer, Map<Integer, Integer>> IndexTwoKey = IndexThreeKey.get(rp);
            if (IndexTwoKey.containsKey(rs)) {
                Map<Integer, Integer> IndexOneKey = IndexTwoKey.get(rs);
                return IndexOneKey.containsKey(ro);
            }
        }
        return false;
    }

}
