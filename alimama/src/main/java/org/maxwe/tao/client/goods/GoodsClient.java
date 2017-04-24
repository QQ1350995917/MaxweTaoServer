package org.maxwe.tao.client.goods;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

import java.util.LinkedList;


/**
 * Created by Pengwei Ding on 2017-04-15 17:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 阿里妈妈商品列表客户端
 */
public class GoodsClient {

    private static final LinkedList<String> specialLinkedList = new LinkedList();

    static {
        specialLinkedList.add("http://c.b1wt.com");
        specialLinkedList.add("https://c.b1wt.com");
        specialLinkedList.add("http://c.b0yp.com");
        specialLinkedList.add("https://c.b0yp.com");
    }

    public static String goodsList(GoodsRequestModel goodsRequestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(goodsRequestModel.getURL());
        httpGet.setHeader("Content-type", "application/json");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
        return resultJson;
    }

    public static String goodsSearch(GoodsRequestModel goodsRequestModel) throws Exception {
        boolean isSpecialDomain = false;
        for (String domain : specialLinkedList) {
            if (!TextUtils.isEmpty(goodsRequestModel.getQ()) &&
                    goodsRequestModel.getQ().startsWith(domain)) {
                isSpecialDomain = true;
                break;
            }
        }

        if (isSpecialDomain) {
            String finalUrl = goodsTrueUrl(goodsRequestModel.getQ());
            if (TextUtils.isEmpty(finalUrl)) {
                return null;
            } else {
                String cleanUrl = GoodsRequestModel.clearQueryUrl(finalUrl);
                goodsRequestModel.setQ(cleanUrl);
            }
        }

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(goodsRequestModel.getURL());
        httpGet.setHeader("Content-type", "application/json");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
        return resultJson;
    }

    private static String goodsTrueUrl(String url) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
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
