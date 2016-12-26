package org.maxwe.tao.server.controller.user.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.core.Controller;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.utils.Code;
import org.maxwe.tao.server.service.user.CSEntity;
import org.maxwe.tao.server.service.user.CSServices;
import org.maxwe.tao.server.service.user.ICSServices;
import org.maxwe.tao.server.service.user.proxy.IProxyServices;
import org.maxwe.tao.server.service.user.proxy.ProxyEntity;
import org.maxwe.tao.server.service.user.proxy.ProxyServices;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-12-25 14:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ProxyController extends Controller implements IProxyController {
    private IProxyServices iProxyServices = new ProxyServices();
    private ICSServices icsServices = new CSServices();

    @Override
    public void exist() {
        String params = this.getPara("p");
        VProxyEntity requestVProxyEntity = JSON.parseObject(params, VProxyEntity.class);
        IResultSet iResultSet = new ResultSet();
        //重复检测
        boolean existAccount = iProxyServices.existProxy(requestVProxyEntity.getCellphone());
        if (existAccount) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "cellphone")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVProxyEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "cellphone")));
    }

    @Override
    public void smsCode() {

    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VProxyEntity requestVProxyEntity = JSON.parseObject(params, VProxyEntity.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (!requestVProxyEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "cellphone", "password")));
            return;
        }
        //重复检测
        boolean existAccount = iProxyServices.existProxy(requestVProxyEntity.getCellphone());
        if (existAccount) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(ProxyEntity.class, "cellphone", "password")));
            return;
        }
        //创建
        requestVProxyEntity.setProxyId(UUID.randomUUID().toString());
        ProxyEntity proxy = iProxyServices.createProxy(requestVProxyEntity);
        if (proxy == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(ProxyEntity.class, "cellphone", "password")));
            return;
        }
        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VProxyEntity(proxy));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(ProxyEntity.class, "t")));
    }

    @Override
    public void login() {
        String params = this.getPara("p");
        VProxyEntity requestVProxyEntity = JSON.parseObject(params, VProxyEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVProxyEntity.checkLoginParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "cellphone", "password")));
            return;
        }

        //查找
        ProxyEntity proxyEntity = iProxyServices.retrieveProxy(requestVProxyEntity);
        if (proxyEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity proxyCS = new CSEntity(proxyEntity.getProxyId(), "proxy");
        proxyCS.setCsId(UUID.randomUUID().toString());
        proxyCS.setToken(Code.getToken(proxyEntity.getCellphone()));
        CSEntity cs = icsServices.create(proxyCS);
        if (cs == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "cellphone", "password")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VProxyEntity(cs.getToken()));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "t")));
    }

    @Override
    public void logout() {
        String params = this.getPara("p");
        VProxyEntity requestVProxyEntity = JSON.parseObject(params, VProxyEntity.class);
        IResultSet iResultSet = new ResultSet();
        boolean b = icsServices.deleteByToken(requestVProxyEntity.getT());
        if (b) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "t")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void password() {
        String params = this.getPara("p");
        VProxyEntity requestVProxyEntity = JSON.parseObject(params, VProxyEntity.class);
        IResultSet iResultSet = new ResultSet();
        requestVProxyEntity.setPassword(requestVProxyEntity.getOrdPassword());
        ProxyEntity proxyEntity = iProxyServices.retrieveProxy(requestVProxyEntity);
        if (proxyEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }
        requestVProxyEntity.setProxyId(proxyEntity.getProxyId());
        ProxyEntity updateProxyEntity = iProxyServices.updateProxyPassword(requestVProxyEntity);
        if (updateProxyEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void retrieveProxyByPId(String pid) {
        String params = this.getPara("p");
        VProxyEntity requestVProxyEntity = JSON.parseObject(params, VProxyEntity.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = icsServices.retrieveByToken(requestVProxyEntity.getT());
        if (csEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "t")));
            return;
        }
        LinkedList<ProxyEntity> proxyEntities = iProxyServices.retrieveProxyByPId(csEntity.getMappingId());
        if (proxyEntities == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVProxyEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VProxyEntity.class, "t")));
            return;
        }
        LinkedList<VProxyEntity> responseVProxyEntities = new LinkedList<>();
        for (ProxyEntity proxyEntity : proxyEntities) {
            responseVProxyEntities.add(new VProxyEntity(proxyEntity));
        }
        if (responseVProxyEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVProxyEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }
}
