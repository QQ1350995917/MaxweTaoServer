package org.maxwe.tao.server.service.tao.alimama.convert;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by Pengwei Ding on 2017-02-24 21:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AliConvertServices {
    public static AliConvertEntity convertAlimamaByGoodsId(AliConvertRequestModel aliConvertRequestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(aliConvertRequestModel.getUrl());
        httpGet.setHeader("Cookie", aliConvertRequestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            AliConvertResponseModel aliConvertResponseModel = JSON.parseObject(resultJson, AliConvertResponseModel.class);
            if (aliConvertResponseModel != null) {
                AliConvertEntity data = aliConvertResponseModel.getData();
                if (data != null) {
                    return data;
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
}
