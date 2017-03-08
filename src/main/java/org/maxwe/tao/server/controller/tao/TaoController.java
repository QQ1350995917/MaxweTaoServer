package org.maxwe.tao.server.controller.tao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.controller.tao.model.alimama.AuctionRequestModel;
import org.maxwe.tao.server.controller.tao.model.alimama.AuctionResponseModel;
import org.maxwe.tao.server.controller.tao.model.alimama.GoodsRequestModel;
import org.maxwe.tao.server.controller.tao.model.alimama.GoodsResponseModel;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponsePageEntity;
import org.maxwe.tao.server.service.tao.alimama.convert.AliConvertEntity;
import org.maxwe.tao.server.service.tao.alimama.convert.AliConvertRequestModel;
import org.maxwe.tao.server.service.tao.alimama.convert.AliConvertServices;
import org.maxwe.tao.server.service.tao.alimama.goods.AliGoodsRequestModel;
import org.maxwe.tao.server.service.tao.alimama.goods.GoodsServices;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-11 12:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class TaoController extends Controller implements ITaoController {
    private final Logger logger = Logger.getLogger(TaoController.class.getName());

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void search() {
        String params = this.getAttr("p");
        logger.info("search : params = " + params);
        GoodsRequestModel requestModel = JSON.parseObject(params, GoodsRequestModel.class);
        if (!requestModel.isGoodsRequestParamsOk()) {
            logger.info("search : 参数错误 params = " + params);
            GoodsResponseModel responseModel = new GoodsResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AliGoodsRequestModel aliGoodsRequestModel = new AliGoodsRequestModel();
        aliGoodsRequestModel.setToPage(requestModel.getToPage());
        aliGoodsRequestModel.setPerPageSize(requestModel.getPerPageSize());
        aliGoodsRequestModel.setQ(requestModel.getQ());
        aliGoodsRequestModel.setCookie(requestModel.getCookie());
        aliGoodsRequestModel.setSortType(requestModel.getSortType());
        aliGoodsRequestModel.setUrlType(requestModel.getUrlType());

        try {
            logger.info("search : 查询条件 " + requestModel.toString());
            List<AliResponsePageEntity> aliResponsePageEntities = GoodsServices.searchForGoods(aliGoodsRequestModel);
            if (aliResponsePageEntities == null) {
                logger.info("search : 查询结果 null ");
                GoodsResponseModel responseModel = new GoodsResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_NOT_FOUND);
                responseModel.setMessage("没有数据，请更换查询参数");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
                return;
            }
            if (aliResponsePageEntities.size() > 0) {
                GoodsResponseModel responseModel = new GoodsResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_EMPTY);
                responseModel.setMessage("没有数据，请更换查询参数");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            } else {
                logger.info("search : 查询结果总量 " + aliResponsePageEntities.size());
                logger.debug("search : 查询结果信息 " + aliResponsePageEntities.toString());
                GoodsResponseModel responseModel = new GoodsResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_SUCCESS);
                responseModel.setMessage("查询成功");
                responseModel.setGoodsEntities(aliResponsePageEntities);
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            }
        } catch (Exception e) {
            logger.info("search : 查询结果异常");
            e.printStackTrace();
            GoodsResponseModel responseModel = new GoodsResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("查询错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void auction() {
        String params = this.getAttr("p");
        AuctionRequestModel requestModel = JSON.parseObject(params, AuctionRequestModel.class);
        if (requestModel == null || !requestModel.isAuctionRequestParamsOk()) {
            AuctionResponseModel responseModel = new AuctionResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AliConvertRequestModel aliConvertRequestModel = new AliConvertRequestModel();
        aliConvertRequestModel.setSiteid(requestModel.getSiteid());
        aliConvertRequestModel.setAuctionid(requestModel.getAuctionid());
        aliConvertRequestModel.setAdzoneid(requestModel.getAdzoneid());
        aliConvertRequestModel.setCookie(requestModel.getCookie());

        try {
            AliConvertEntity aliConvertEntity = AliConvertServices.convertAlimamaByGoodsId(aliConvertRequestModel);
            if (aliConvertEntity == null) {
                AuctionResponseModel responseModel = new AuctionResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
                responseModel.setMessage("参数错误，请重试");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            } else {
                AuctionResponseModel responseModel = new AuctionResponseModel(requestModel);
                responseModel.setCode(ResponseModel.RC_SUCCESS);
                responseModel.setAuction(aliConvertEntity);
                responseModel.setMessage("转链成功");
                renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AuctionResponseModel responseModel = new AuctionResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("参数错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{GoodsRequestModel.propertyFilter, TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }
}
