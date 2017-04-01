package org.maxwe.tao.server.common.utils;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by Pengwei Ding on 2017-04-01 17:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 处理转链的时候url的信息
 */
public class SearchUrlUtils {

    /**
     * 清理url上的多余干扰信息
     * @param url 原始URL
     * @return 清理后的URL
     */
    public static String cleanUrl(String url){
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
                return url.replace("?&", "?");
            }
        } else {
            return splits[0] + "?" + id;
        }
        return url;
    }
}
