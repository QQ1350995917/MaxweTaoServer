package org.maxwe.tao.server.controller.goods;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
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
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void query() {
        String params = this.getAttr("p");
        GoodsRequestModel requestModel = JSON.parseObject(params, GoodsRequestModel.class);
        IResultSet iResultSet = new ResultSet();
        try {
            GoodsRequestModel goodsRequestModel = new GoodsRequestModel();
            if(requestModel != null ){
                if (!StringUtils.isEmpty(requestModel.getQ())){
                    goodsRequestModel.setQ(requestModel.getQ());
                }

                if (!StringUtils.isEmpty(requestModel.getCat())){
                    goodsRequestModel.setCat(requestModel.getCat());
                }

                if (!StringUtils.isEmpty(requestModel.getItemloc())){
                    goodsRequestModel.setItemloc(requestModel.getItemloc());
                }

                if (!StringUtils.isEmpty(requestModel.getSort())){
                    goodsRequestModel.setSort(requestModel.getSort());
                }

                if (requestModel.getIs_tmall()){
                    goodsRequestModel.setIs_tmall(true);
                }

                if (requestModel.getIs_overseas()){
                    goodsRequestModel.setIs_overseas(true);
                }

                if (requestModel.getStart_price() >= 0 && requestModel.getStart_price() < 99999999){
                    goodsRequestModel.setStart_price(requestModel.getStart_price());
                }

                if (requestModel.getEnd_price() >= 0 && requestModel.getEnd_price() < 99999999){
                    goodsRequestModel.setEnd_price(requestModel.getEnd_price());
                }

                if (requestModel.getStart_tk_rate() >= 0 && requestModel.getStart_tk_rate() < 9999){
                    goodsRequestModel.setStart_tk_rate(requestModel.getStart_tk_rate());
                }

                if (requestModel.getEnd_tk_rate() >= 0 && requestModel.getEnd_tk_rate() < 9999){
                    goodsRequestModel.setEnd_tk_rate(requestModel.getEnd_tk_rate());
                }

                if (requestModel.getPage_no() >0 && requestModel.getPage_no() < 99999999){
                    goodsRequestModel.setPage_no(requestModel.getPage_no());
                }

                if (requestModel.getPage_size() > 0 && requestModel.getPage_size() <= 100){
                    goodsRequestModel.setPage_size(requestModel.getPage_size());
                }
            }

            TaobaoClient taoBaoClient = APIConstants.getTaoBaoClient();
            GoodsResponseModel execute = taoBaoClient.execute(goodsRequestModel);
            String body = execute.getBody();
            Map map = JSON.parseObject(body, Map.class);

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
