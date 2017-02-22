package org.maxwe.tao.server.service.tao.bao.convert;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet method = new HttpGet(url);
        method.setHeader("Cookie", taoTransRequestModel.getCookie());
        method.setHeader("Content-type","application/json");
        method.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        HttpResponse result = httpClient.execute(method);
        if (result.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(result.getEntity());
            TaoTransResponseModel taoTransResponseModel = JSON.parseObject(resultJson, TaoTransResponseModel.class);
            return taoTransResponseModel.getData();
        }
        return null;
    }
}
