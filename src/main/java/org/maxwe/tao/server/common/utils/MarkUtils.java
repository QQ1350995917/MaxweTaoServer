package org.maxwe.tao.server.common.utils;

import java.util.*;

/**
 * Created by Pengwei Ding on 2016-12-26 16:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成和解析系统需要的编码
 */
public class MarkUtils {

    private static final String[] string0 = {"J", "V", "Z"};
    private static final String[] string1 = {"F", "B", "I", "Q"};
    private static final String[] string2 = {"L", "H"};
    private static final String[] string3 = {"D", "T"};
    private static final String[] string4 = {"R", "N", "Y"};
    private static final String[] string5 = {"A", "G", "O"};
    private static final String[] string6 = {"C", "U"};
    private static final String[] string7 = {"P", "X"};
    private static final String[] string8 = {"S", "W"};
    private static final String[] string9 = {"M", "E", "K"};

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

    public static String enMark(String cellphone) {
        if (cellphone.length() != 11) {
            throw new UnknownFormatConversionException(cellphone);
        }
        LinkedList<String> strings = new LinkedList<>();
        for (int i = 2; i < cellphone.length(); i++) {
            strings.add(cellphone.substring(i, i + 1));
        }
        String last = strings.getLast();
        for (int i = 0; i < strings.size(); i++) {
            String encode = encodeInEncodeMap(strings.get(i));
            strings.remove(i);
            strings.add(i, encode);
        }

        String fillIn = new Random().nextInt(10) + "";
        strings.add(Integer.parseInt(last), encodeInEncodeMap(fillIn));
        String result = "";
        for (int i = 0; i < strings.size(); i++) {
            result += strings.get(i);
        }

        String lastFillIn = new Random().nextInt(10) + "";
        if (Integer.parseInt(lastFillIn) % 2 == 0) {

        }
        return result + encodeInEncodeMap(lastFillIn);
    }

    public static String deMark(String accessCode) {
        if (accessCode.length() != 11) {
            throw new UnknownFormatConversionException(accessCode);
        }
        String lastCode = accessCode.substring(accessCode.length() - 1, accessCode.length());
        String lastFillIn = decodeInDecodeMap(lastCode);
        if (Integer.parseInt(lastFillIn) % 2 == 0) {

        }
        accessCode = accessCode.substring(0, accessCode.length() - 1);
        LinkedList<String> strings = new LinkedList<>();
        for (int i = 0; i < accessCode.length(); i++) {
            strings.add(accessCode.substring(i, i + 1));
        }
        String last = strings.getLast();
        String lastIndex = decodeInDecodeMap(last);
        strings.remove(Integer.parseInt(lastIndex));
        String result = "";
        for (int i = 0; i < strings.size(); i++) {
            String inCodeMap = decodeInDecodeMap(strings.get(i));
            result += inCodeMap;
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        String cellphone = "18511694468";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            String enAccessCode = enMark(cellphone);
            System.out.println(enAccessCode);
            String deAccessCode = deMark(enAccessCode);
            System.out.println(deAccessCode);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
