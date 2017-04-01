package org.maxwe.tao.server.common.utils;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by Pengwei Ding on 2017-03-10 18:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {

    public static void main(String[] args) throws Exception {
//        String url = "https://item.taobao.com/item.htm?spm=a230r.1.14.82.OwkDCo&id=41543156232&ns=1&abbucket=12#detail";
//        String url = "https://item.taobao.com/item.htm?id=41543156232&ns=1&spm=a230r.1.14.82.OwkDCo&abbucket=12#detail";
        String url = "https://item.taobao.com/item.htm?abbucket=12#detail&spm=a230r.1.14.82.OwkDCo";
//        String url = "https://item.taobao.com/item.htm";
        String[] splits = url.split("\\?");
        String id = null;
        String spm = null;
        if (splits != null && splits.length > 1) {
            String[] subSplits = splits[1].split("&");
            for (String split : subSplits) {
                if (split.startsWith("id=")) {
                    id = split;
                } else if (split.startsWith("spm=")) {
                    spm = split;
                }
            }
        }

        if (StringUtils.isEmpty(id) && !StringUtils.isEmpty(spm)) {
            url = url.replace(spm, "");
            if (url.contains("?&")) {
                url = url.replace("?&", "?");
            }
        } else {
            url = splits[0] + "?" + id;
        }
        System.out.println(url);
    }
}
