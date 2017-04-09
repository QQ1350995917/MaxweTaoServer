package org.maxwe.tao.server.service.tao.alimama.goods;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.utils.SearchUrlUtils;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponseDataEntity;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponsePageEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-24 20:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 商品获取和转链服务
 */
public class GoodsServices {
    private static final Logger logger = Logger.getLogger(GoodsServices.class.getName());
    private static final LinkedList<String> specialLinkedList = new LinkedList();

    static {
        specialLinkedList.add("http://c.b1wt.com");
        specialLinkedList.add("https://c.b1wt.com");
    }

    public static List<AliResponsePageEntity> searchForGoods(AliGoodsRequestModel aliGoodsRequestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        boolean isSpecialDomain = false;
        for (String domain : specialLinkedList) {
            if (!StringUtils.isEmpty(aliGoodsRequestModel.getQ()) &&
                    aliGoodsRequestModel.getQ().startsWith(domain)) {
                isSpecialDomain = true;
                break;
            }
        }

        if (isSpecialDomain) {
            String finalUrl = specialDomainHandl(aliGoodsRequestModel.getQ());
            if (StringUtils.isEmpty(finalUrl)) {
                return null;
            } else {
                String cleanUrl = SearchUrlUtils.cleanUrl(finalUrl);
                aliGoodsRequestModel.setQ(cleanUrl);
            }
        }
        HttpGet httpGet = new HttpGet(aliGoodsRequestModel.getUrl());
        httpGet.setHeader("Cookie", aliGoodsRequestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            AliGoodsResponseModel aliGoodsResponseModel = JSON.parseObject(resultJson, AliGoodsResponseModel.class);
            if (aliGoodsRequestModel != null) {
                AliResponseDataEntity data = aliGoodsResponseModel.getData();
                if (data != null) {
                    List<AliResponsePageEntity> pageList = data.getPageList();
                    if (pageList != null) {
                        return pageList;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 特殊域名的链接处理
     *
     * @param url
     * @return
     */
    public static String specialDomainHandl(String url) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        String http = null;
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            String[] split = resultJson.split("\\r\\n");
            for (String text : split) {
                if (text.contains("var url = ")) {
                    http = text.substring(text.indexOf("http"), text.length() - 2);
                    break;
                }
            }
        }
        return http;
    }
}
