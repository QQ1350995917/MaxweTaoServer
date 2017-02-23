package org.maxwe.tao.server.service.tao.bao.convert;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.maxwe.tao.server.service.tao.mami.URLTransEntity;

/**
 * Created by Pengwei Ding on 2017-02-22 21:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoTransServices {

    public static URLTransEntity trans(TaoTransRequestModel taoTransRequestModel) throws Exception {
        String url = "http://pub.alimama.com/urltrans/urltrans.json?" +
                "siteid=" + taoTransRequestModel.getSiteid() + "&" +
                "adzoneid=" + taoTransRequestModel.getAdzoneid() + "&" +
                "promotionURL=" + taoTransRequestModel.getPromotionURL() + "&" +
                "t=" + System.currentTimeMillis()+"&" +
                "_input_charset=utf-8";

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", taoTransRequestModel.getCookie());
        httpGet.setHeader("Content-type","application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            TaoTransResponseModel taoTransResponseModel = JSON.parseObject(resultJson, TaoTransResponseModel.class);
            return taoTransResponseModel.getData();
        }
        return null;
    }
}
