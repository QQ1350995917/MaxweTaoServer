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
        specialLinkedList.add("http://c.b6wq.com");
        specialLinkedList.add("https://c.b6wq.com");
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

//        String finalUrl = specialDomainHandl(aliGoodsRequestModel.getQ());
//        if (StringUtils.isEmpty(finalUrl)) {
//            return null;
//        } else {
//            String cleanUrl = SearchUrlUtils.cleanUrl(finalUrl);
//            aliGoodsRequestModel.setQ(cleanUrl);
//        }

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
        String s = specialDomainHandl("https://detail.tmall.com/item.htm?id=38077266102&ut_sk=1.VH3TzEap4sYDAOQIz5Pp4y3L_21380790_1492498520418.Copy.1&sourceType=item&price=79.97&origin_price=98&suid=7E5E4456-4FCC-4734-9A42-01D9A04BE2F0&un=f734e8809ee8d36fcca3f2c0d66365a2&share_crt_v=1&cpp=1&shareurl=true&spm=a313p.22.24h.33931060898&short_name=h.UjfP1h&cv=kXhMqL2ZIc&sm=8ff4f2&app=chrome");
        System.out.println(s);
    }
}
