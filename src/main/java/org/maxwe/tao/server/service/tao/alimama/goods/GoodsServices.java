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
import org.maxwe.tao.server.controller.meta.MetaController;
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

    public static List<AliResponsePageEntity> searchForGoods(
        AliGoodsRequestModel aliGoodsRequestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        boolean isSpecialDomain = MetaController.isSpecialLink(aliGoodsRequestModel.getQ());
//        boolean isSpecialDomain = true;
        if (isSpecialDomain) {
            String finalUrl = specialDomainHandl(aliGoodsRequestModel.getQ());
            if (StringUtils.isEmpty(finalUrl)) {
                logger.info("特殊链接判断：是特殊链接" + aliGoodsRequestModel.getQ() + ""
                    + ", 执行链接是：null");
                return null;
            } else {
                String cleanUrl = SearchUrlUtils.cleanUrl(finalUrl);
                logger.info("特殊链接判断：是特殊链接" + aliGoodsRequestModel.getQ() + ""
                    + ", 执行链接是：" + finalUrl + ""
                    + ", 最终链接是：" + cleanUrl);
                aliGoodsRequestModel.setQ(cleanUrl);
            }
        } else {
            logger.info("特殊链接判断：不是特殊链接" + aliGoodsRequestModel.getQ());
        }

        HttpGet httpGet = new HttpGet(aliGoodsRequestModel.getUrl());
        httpGet.setHeader("Cookie", aliGoodsRequestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            AliGoodsResponseModel aliGoodsResponseModel =
                JSON.parseObject(resultJson, AliGoodsResponseModel.class);
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
        httpGet.setHeader("User-Agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        String http = null;
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            System.out.println(resultJson);
            String[] split = resultJson.split("\\r\\n");
            for (String text : split) {
                if (text.contains("var url = ")) {
                    http = text.substring(text.indexOf("http"), text.length() - 2);
                    break;
                }
                //                if (text.contains("htm?itemId=")){
                //                    String trueUrl = "http://item.taobao.com/item.htm?id=";
                //                    int indexOf = text.indexOf("htm?itemId=");
                //                    String substring = text.substring(indexOf, indexOf + 12);
                //                    return trueUrl + substring;
                //                }
            }
        }
        return http;
    }

    public static void main(String[] args) throws Exception {
        String s = specialDomainHandl("http://c.b0yp.com/h.5ifYaj?cv=Ki6VJINDCy&sm=22e192");
        System.out.println(s);
    }
}
