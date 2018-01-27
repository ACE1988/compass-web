package com.sdxd.api.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/24.
 */
public class Map_AccountCode {

    public static Map<String,String> map = new HashMap<>();

    static {
        map.put("P","60");
        map.put("C","65");
        map.put("G","70");
        map.put("I","75");
        map.put("M","80");
        map.put("E","85");
        map.put("X","99");
        map.put("S","90");
        map.put("T","55");
        map.put("V","50");
        map.put("W","99");

    }

    public static String get(String key){

            return map.get(key);
    }

}
