package org.maxwe.tao.server.service.tao.bao.convert;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

/**
 * Created by Pengwei Ding on 2017-02-19 15:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestConvert {
    public static void main(String[] args) throws Exception {
        TaoConvertRequestModel req = new TaoConvertRequestModel();
        req.setFields("num_iid,click_url");
        req.setNum_iids("535933437297");
        req.setAdzone_id(71254342);
        req.setPlatform(1);
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoConvertResponseModel execute = taoBaoClient.execute(req);
        System.out.println(execute.getBody());
        System.out.println();
    }
}
