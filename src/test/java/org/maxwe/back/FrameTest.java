package org.maxwe.back;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-07-28 11:51.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FrameTest {

    public static void main(String[] args) {

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("a","1");
        hashMap1.put("b","2");
        hashMap1.put("c","3");

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("a","1");
        hashMap2.put("b","2");
        hashMap2.put("c", "3");

        LinkedList<Map> objects = new LinkedList<>();
        objects.add(hashMap1);
        objects.add(hashMap2);

    }

}
