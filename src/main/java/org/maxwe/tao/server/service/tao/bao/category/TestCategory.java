package org.maxwe.tao.server.service.tao.bao.category;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

/**
 * Created by Pengwei Ding on 2017-02-19 10:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestCategory {
    public static void main(String[] args) throws Exception {
        TaoCategoryRequestModel req = new TaoCategoryRequestModel();
        req.setParent_cid(0);

        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoCategoryResponseModel execute = taoBaoClient.execute(req);
        System.out.println(execute.getBody());
    }
}
