package org.maxwe.tao.server.common.utils;

import java.util.*;

/**
 * Created by Pengwei Ding on 2017-01-10 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GrantCodeUtils {
    private static final String[] string0 = {"V", "9", "Z", "N"};
    private static final String[] string1 = {"F", "G", "B", "Q", "7"};
    private static final String[] string2 = {"L", "2", "H", "J"};
    private static final String[] string3 = {"D", "T"};
    private static final String[] string4 = {"R", "E", "Y", "6"};
    private static final String[] string5 = {"A", "O", "5"};
    private static final String[] string6 = {"U", "P", "1"};
    private static final String[] string7 = {"C", "X", "4"};
    private static final String[] string8 = {"S", "W", "8"};
    private static final String[] string9 = {"M", "K", "3"};

    private static final HashMap<String, String[]> encodeMap = new HashMap<>();
    private static final HashMap<String, String> decodeMap = new HashMap<>();

    static {
        encodeMap.put("0", string0);
        encodeMap.put("1", string1);
        encodeMap.put("2", string2);
        encodeMap.put("3", string3);
        encodeMap.put("4", string4);
        encodeMap.put("5", string5);
        encodeMap.put("6", string6);
        encodeMap.put("7", string7);
        encodeMap.put("8", string8);
        encodeMap.put("9", string9);

        Set<Map.Entry<String, String[]>> entries = encodeMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            for (String value : values) {
                decodeMap.put(value, key);
            }
        }
    }


    private static String encodeInEncodeMap(String code) {
        String[] encodes = encodeMap.get(code);
        int index = new Random().nextInt(encodes.length);
        String encode = encodes[index];
        return encode;
    }


    private static String decodeInDecodeMap(String code) {
        String decode = decodeMap.get(code);
        return decode;
    }

    private static String enGrantCode(String number) {
        String result = "";
        for (int i = 0; i < number.length(); i++) {
            String encode = encodeInEncodeMap(number.substring(i, i + 1));
            result += encode;
        }
        return result;
    }

    private static String deGrantCode(String grantCode) {
        String result = "";
        for (int i = 0; i < grantCode.length(); i++) {
            String decode = decodeInDecodeMap(grantCode.substring(i, i + 1));
            result += decode;
        }
        return result;
    }


    public static String genGrantCode() {
        Random random = new Random();
        int index0 = random.nextInt(10);
        int index1 = random.nextInt(10);
        int index2 = random.nextInt(10);
        int index3 = random.nextInt(10);
        int index4 = random.nextInt(10);
        int index5 = random.nextInt(10);
        int index6 = random.nextInt(10);
        int index7 = random.nextInt(10);
        String number = "" + index0 + index1 + index2 + index3 + index4 + index5 + index6 + index7 + "";
        return enGrantCode(number);
    }


    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < 1000000; i++) {
            linkedList.add(genGrantCode());
        }
        System.out.println(linkedList.size());

        int counter = 0;
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(i);
            boolean add = set.add(enGrantCode(linkedList.get(i)));
            if (!add) {
                counter++;
            }
            System.out.println("===");
        }
        System.out.println("不重复量：" + set.size());
        System.out.println("重复量：" + counter);
        System.out.println(DateTimeUtils.parseLongToFullTime(System.currentTimeMillis()));

    }
}







