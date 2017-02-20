package org.maxwe.tao.server.service.tao.jidi;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Pengwei Ding on 2017-02-20 21:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {
    private static void test() throws Exception {
        int pageIndex = 1;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://api.tkjidi.com/getGoodsLink?appkey=db7818534504f08a4b5a8c584a6af3ca&type=www_lingquan&page=" + pageIndex);
        HttpResponse response = client.execute(httpget);
        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            /**读取服务器返回过来的json字符串数据**/
            String strResult = EntityUtils.toString(response.getEntity());
            /**把json字符串转换成json对象**/
            JiDiGoodsModel jiDiGoodsModel = JSONObject.parseObject(strResult, JiDiGoodsModel.class);

            System.out.println(strResult);
        } else {

        }
    }
    public static void main(String[] args) throws Exception {
    }
}
