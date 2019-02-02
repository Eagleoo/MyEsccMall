package com.yda.esccmall.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class EncryptionUtil {

    final private static String SECRET = "9suf9su79F899f9f9FO00f23DDdfe3DF";
    final public static String KEY = "2394839284";

    public static String getSign(Map<String, String> map, String uri,String timestamps) {
        String str = "contentlength=" + EncryptionUtil.getRequestCountLength(map) + "&key=" + KEY + "&method=POST&timestamp=" + timestamps + "&uri=" + UrlUtil.getURLEncoderString(uri) + "&secret=" + md5(SECRET).toUpperCase();
//        str = UrlUtil.getURLEncoderString(str);
        Log.e("加密前的数据", "str" + str);
//        str="contentlength=52&key=6800168364&method=POST&timestamp=1528961560&uri=%252Fasp%252Findex.asp&secret=1D5018602E28AD20174BE6BB478EEEC9";
         String md5Str = md5(str).toUpperCase();
        Log.e("加密after", "md5" + md5Str);
        return md5Str;
    }

    public static String md5(String string) {
//        if (TextUtils.isEmpty(string)) {
//            return "";
//        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getRequestCountLength(Map<String, String> map) {
        Map<String, String> map1 = clearNullValue(map);
//        Map<String, String> resultMap = sortMapByKey(map1);
        Map<String, String> resultMap = map1;
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            Log.e("after", entry.getKey() + " " + entry.getValue());
        }
        StringBuilder p = new StringBuilder();
        if (null != resultMap) {
            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
//                p.append("&" + entry.getKey() + "=" + entry.getValue());
                p.append("&" + entry.getKey() + "=" + UrlUtil.getURLEncoderString(entry.getValue()));
            }
        }
        String urlString = p.toString().replaceFirst("&", "");
        Log.e("请求拼接", "urlString" + urlString);
        return urlString.length();
    }

    /**
     * 去除 空值
     */
    private static Map<String, String> clearNullValue(Map<String, String> map) {
        Set<String> keys = map.keySet();
        Map<String, String> map1 = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {

            Log.e("before", entry.getKey() + " " + entry.getValue());
            if (entry.getValue() != null && !entry.getValue().equals("")) {
                map1.put(entry.getKey(), entry.getValue());
            }
        }

        return map1;
    }
    //对请求参数 进行 排序 a-z

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

}
