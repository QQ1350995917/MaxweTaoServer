package org.maxwe.tao.server.service.tao.bao.spread;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoResponse;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-19 16:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestSpread {
    public static void main(String[] args) throws Exception {
        TaoSpreadRequestModel req = new TaoSpreadRequestModel();
        LinkedList<TaoSpreadEntity> list2 = new LinkedList<>();
        TaoSpreadEntity obj3 = new TaoSpreadEntity();
        list2.add(obj3);
        obj3.setUrl("https://item.taobao.com/item.htm?id=543359825908");
        req.setRequests(list2);

        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaobaoResponse execute = taoBaoClient.execute(req);
        System.out.println(execute.getBody());
    }
}
