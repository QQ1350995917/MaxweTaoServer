package org.maxwe.tao.server.controller.user.seller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.core.Controller;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.Code;
import org.maxwe.tao.server.service.user.CSEntity;
import org.maxwe.tao.server.service.user.CSServices;
import org.maxwe.tao.server.service.user.ICSServices;
import org.maxwe.tao.server.service.user.seller.ISellerServices;
import org.maxwe.tao.server.service.user.seller.SellerEntity;
import org.maxwe.tao.server.service.user.seller.SellerServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-12-25 14:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SellerController extends Controller implements ISellerController {
    private ISellerServices iSellerServices = new SellerServices();
    private ICSServices icsServices = new CSServices();

    @Override
    public void exist() {
        String params = this.getPara("p");
        VSellerEntity requestVSellerEntity = JSON.parseObject(params, VSellerEntity.class);
        IResultSet iResultSet = new ResultSet();
        //重复检测
        boolean existAccount = iSellerServices.existSeller(requestVSellerEntity.getCellphone());
        if (existAccount) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVSellerEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone")));
    }

    @Override
    public void smsCode() {

    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VSellerEntity requestVSellerEntity = JSON.parseObject(params, VSellerEntity.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (!requestVSellerEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone", "password")));
            return;
        }
        //重复检测
        boolean existAccount = iSellerServices.existSeller(requestVSellerEntity.getCellphone());
        if (existAccount) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone", "password")));
            return;
        }
        //创建
        requestVSellerEntity.setSellerId(UUID.randomUUID().toString());
        SellerEntity seller = iSellerServices.createSeller(requestVSellerEntity);
        if (seller == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone", "password")));
            return;
        }
        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VSellerEntity(seller));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "t")));
    }

    @Override
    public void login() {
        String params = this.getPara("p");
        VSellerEntity requestVSellerEntity = JSON.parseObject(params, VSellerEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVSellerEntity.checkLoginParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone", "password")));
            return;
        }

        //查找
        SellerEntity sellerEntity = iSellerServices.retrieveSeller(requestVSellerEntity);
        if (sellerEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity sellerCS = new CSEntity(sellerEntity.getSellerId(), "seller");
        sellerCS.setCsId(UUID.randomUUID().toString());
        sellerCS.setToken(Code.getToken(sellerEntity.getCellphone()));
        CSEntity cs = icsServices.create(sellerCS);
        if (cs == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "cellphone", "password")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VSellerEntity(cs.getToken()));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "t")));
    }

    @Override
    public void logout() {
        String params = this.getPara("p");
        VSellerEntity requestVSellerEntity = JSON.parseObject(params, VSellerEntity.class);
        IResultSet iResultSet = new ResultSet();
        boolean b = icsServices.deleteByToken(requestVSellerEntity.getT());
        if (b){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "t")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void password() {
        String params = this.getPara("p");
        VSellerEntity requestVSellerEntity = JSON.parseObject(params, VSellerEntity.class);
        IResultSet iResultSet = new ResultSet();
        requestVSellerEntity.setPassword(requestVSellerEntity.getOrdPassword());
        SellerEntity sellerEntity = iSellerServices.retrieveSeller(requestVSellerEntity);
        if (sellerEntity == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class, "t","orderPassword","newPassword")));
            return;
        }
        requestVSellerEntity.setSellerId(sellerEntity.getSellerId());
        SellerEntity updateSellerEntity = iSellerServices.updateSellerPassword(requestVSellerEntity);
        if (updateSellerEntity == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSellerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSellerEntity.class,  "t","orderPassword","newPassword")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }
}
