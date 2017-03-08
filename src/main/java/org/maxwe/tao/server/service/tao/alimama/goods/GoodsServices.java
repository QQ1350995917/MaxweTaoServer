package org.maxwe.tao.server.service.tao.alimama.goods;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponseDataEntity;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponsePageEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-24 20:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsServices {

    public static List<AliResponsePageEntity> searchForGoods(AliGoodsRequestModel aliGoodsRequestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
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
}
