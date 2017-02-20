package org.maxwe.tao.server.service.tao.bao.coupon;

import com.alibaba.fastjson.JSON;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

/**
 * Created by Pengwei Ding on 2017-02-17 13:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestCoupon {

    public static void main(String[] args) throws Exception {
        TaoCouponRequestModel req = new TaoCouponRequestModel();
        req.setPlatform(1L);
        req.setNum_iids("5450836062,520904375034");
        req.setPid("mm_120134623_21142533_71254342");
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoCouponResponseModel execute = taoBaoClient.execute(req);
        System.out.println(execute.getBody());
        TaoCouponResponseModel couponResponseModel = JSON.parseObject(execute.getBody(), TaoCouponResponseModel.class);
        System.out.println(couponResponseModel);
    }
}
