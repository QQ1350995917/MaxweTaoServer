package org.maxwe.tao.server.service.tao.bao.shop;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

/**
 * Created by Pengwei Ding on 2017-02-19 14:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestShop {
    public static void main(String[] args) throws Exception {
        TaoShopRequestModel req = new TaoShopRequestModel();
        req.setUser_id(374544688L);
        req.setCount(1L);
        req.setPlatform(1L);
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoShopResponseModel execute = taoBaoClient.execute(req);
        System.out.println(execute.getBody());
    }
}
