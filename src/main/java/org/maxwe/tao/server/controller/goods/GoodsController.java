package org.maxwe.tao.server.controller.goods;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.service.api.APIConstants;
import org.maxwe.tao.server.service.api.goods.GoodsResponseModel;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-11 12:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsController extends Controller implements IGoodsController {

    @Override
    public void query() {
        IResultSet iResultSet = new ResultSet();
        try {
            GoodsRequestModel goodsRequestModel = new GoodsRequestModel();
            goodsRequestModel.setMethodName("taobao.tbk.item.get");
            goodsRequestModel.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
            goodsRequestModel.setQ("女装");
            goodsRequestModel.setCat("16");
            goodsRequestModel.setItemloc("杭州");
            goodsRequestModel.setSort("tk_rate_des");
            goodsRequestModel.setIs_tmall(false);
            goodsRequestModel.setIs_overseas(false);
            goodsRequestModel.setStart_price(0);
            goodsRequestModel.setEnd_price(100);
            goodsRequestModel.setStart_tk_rate(1000);
            goodsRequestModel.setEnd_tk_rate(1234);
            goodsRequestModel.setPlatform(1);
            goodsRequestModel.setPage_no(1);
            goodsRequestModel.setPage_size(20);
            TaobaoClient taoBaoClient = APIConstants.getTaoBaoClient();
            GoodsResponseModel execute = taoBaoClient.execute(goodsRequestModel);
            String body = execute.getBody();
            Map map = JSON.parseObject(body, Map.class);
            System.out.println(body);

            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(map);
            renderJson(JSON.toJSONString(iResultSet));
        } catch (ApiException e) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            e.printStackTrace();
        }
    }
}
