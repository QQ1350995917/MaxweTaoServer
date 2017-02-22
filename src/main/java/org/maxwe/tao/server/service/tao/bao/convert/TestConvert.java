package org.maxwe.tao.server.service.tao.bao.convert;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Pengwei Ding on 2017-02-19 15:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestConvert {
    public static void main(String[] args) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet("http://taobao.com");
        StringEntity entity = new StringEntity("{}", "utf-8");
        entity.setContentEncoding("UTF-8");
//        method.setEntity(entity);
        HttpResponse result = httpClient.execute(method);
        /**请求发送成功，并得到响应**/
        if (result.getStatusLine().getStatusCode() == 200) {
            String str = "";
            try {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(result.getEntity());
                System.out.println(str);
            } catch (Exception e) {
            }
        }
    }
}
