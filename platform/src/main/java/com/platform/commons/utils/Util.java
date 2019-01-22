package com.platform.commons.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2017/6/30.
 */
public class Util {

    /**
     * JSONObject 对象转 map
     *
     * @param jsonObject
     * @return
     */
    public static HashMap<String, String> toHashMap(JSONObject jsonObject) {
        HashMap<String, String> data = new HashMap<String, String>();
        Set<String> keySet = jsonObject.keySet();
        // 遍历jsonObject数据，添加到Map对象
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            data.put(key, String.valueOf(value));
        }

        return data;
    }

    /**
     * JSONObject 对象转 map
     *
     * @param jsonObject
     * @return
     */
    public static HashMap<String, Object> toHashMapObject(JSONObject jsonObject) {
        HashMap<String, Object> data = new HashMap<>();
        Set<String> keySet = jsonObject.keySet();
        // 遍历jsonObject数据，添加到Map对象
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            data.put(key, String.valueOf(value));
        }

        return data;
    }


    /**
     * JSONObject 对象转 map
     *
     * @param jsonObject
     * @return
     */
    public static HashMap<String, Object> toHashMap2(JSONObject jsonObject) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        Set<String> keySet = jsonObject.keySet();
        // 遍历jsonObject数据，添加到Map对象
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            data.put(key, String.valueOf(value));
        }

        return data;
    }


    /**
     * JSONObject 对象转 map
     *
     * @param jsonObject
     * @return
     */
    public static HashMap<String, Object> toMap(JSONObject jsonObject) {
        HashMap<String, Object> data = new HashMap<>();
        Set<String> keySet = jsonObject.keySet();
        // 遍历jsonObject数据，添加到Map对象
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            data.put(key, String.valueOf(value));
        }

        return data;
    }


    /**
     * 32位MD5加密
     *
     * @param str str
     * @return return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }


}
