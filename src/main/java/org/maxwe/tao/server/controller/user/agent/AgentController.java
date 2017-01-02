package org.maxwe.tao.server.controller.user.agent;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.core.Controller;
import com.taobao.api.ApiException;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.Code;
import org.maxwe.tao.server.common.utils.IPUtils;
import org.maxwe.tao.server.service.user.CSEntity;
import org.maxwe.tao.server.service.user.CSServices;
import org.maxwe.tao.server.service.user.ICSServices;
import org.maxwe.tao.server.service.user.agent.IAgentServices;
import org.maxwe.tao.server.service.user.agent.AgentEntity;
import org.maxwe.tao.server.service.user.agent.AgentServices;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-12-25 14:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AgentController extends Controller implements IAgentController {
    private IAgentServices iAgentServices = new AgentServices();
    private ICSServices icsServices = new CSServices();

    @Override
    public void exist() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
            return;
        }
        //重复检测
        AgentEntity existAgent = iAgentServices.existAgent(requestVAgentEntity);
        if (existAgent != null && existAgent.getType() == requestVAgentEntity.getType()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVAgentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
    }

    @Override
    public void smsCode() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || !CellPhoneUtils.isCellphone(requestVAgentEntity.getCellphone())) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
            return;
        }
        if (SMSManager.isCacheAddress(IPUtils.getIpAddress(this.getRequest()))) {
            iResultSet.setCode(IResultSet.ResultCode.RC_TO_MANY.getCode());
            iResultSet.setData(requestVAgentEntity.getCellphone());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "t")));
            return;
        }

        try {
            SMSManager.sendSMS(requestVAgentEntity.getCellphone());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVAgentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (requestVAgentEntity == null || !requestVAgentEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        // 验证码检测
        if (!StringUtils.equals(requestVAgentEntity.getCellPhoneCode(), SMSManager.getCellphoneCode(requestVAgentEntity.getCellphone()))) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        //重复检测 同一种类型下的同一个电话号码算是重复
        AgentEntity existAgent = iAgentServices.existAgent(requestVAgentEntity);
        if (existAgent != null && existAgent.getType() == requestVAgentEntity.getType()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "cellphone", "password")));
            return;
        }

        AgentEntity agent;
        if (existAgent == null) {
            //直接创建
            requestVAgentEntity.setAgentId(UUID.randomUUID().toString());
            agent = iAgentServices.createAgent(requestVAgentEntity);
        } else {
            //修改类型值
            int type = existAgent.getType() + requestVAgentEntity.getType();
            existAgent.setType(type);
            agent = iAgentServices.updateAgentType(existAgent);
        }

        if (agent == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity agentCS = new CSEntity(agent.getAgentId(), requestVAgentEntity.getType());
        agentCS.setCsId(UUID.randomUUID().toString());
        agentCS.setToken(Code.getToken(agent.getCellphone()));
        CSEntity cs = icsServices.create(agentCS);
        if (cs == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void lost() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (requestVAgentEntity == null || !requestVAgentEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        // 注册检测
        AgentEntity existAgent = iAgentServices.existAgent(requestVAgentEntity);
        if (existAgent == null || existAgent.getType() != requestVAgentEntity.getType()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        // 验证码检测
        if (!StringUtils.equals(requestVAgentEntity.getCellPhoneCode(), SMSManager.getCellphoneCode(requestVAgentEntity.getCellphone()))) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        existAgent.setPassword(requestVAgentEntity.getPassword());
        AgentEntity agent = iAgentServices.updateAgentPassword(existAgent);
        if (agent == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity agentCS = new CSEntity(agent.getAgentId(), requestVAgentEntity.getType());
        agentCS.setCsId(UUID.randomUUID().toString());
        agentCS.setToken(Code.getToken(agent.getCellphone()));
        CSEntity cs = icsServices.create(agentCS);
        if (cs == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void login() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || !requestVAgentEntity.checkLoginParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        //查找
        AgentEntity agentEntity = iAgentServices.retrieveAgent(requestVAgentEntity);
        if (agentEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity agentCS = new CSEntity(agentEntity.getAgentId(), requestVAgentEntity.getType());
        agentCS.setCsId(UUID.randomUUID().toString());
        agentCS.setToken(Code.getToken(agentEntity.getCellphone()));
        CSEntity cs = icsServices.create(agentCS);
        if (cs == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void logout() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || StringUtils.isEmpty(requestVAgentEntity.getT())) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t")));
            return;
        }
        boolean b = icsServices.deleteByToken(requestVAgentEntity.getT());
        if (b) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void password() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || !requestVAgentEntity.checkModifyPasswordParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        CSEntity existCSEntity = icsServices.retrieveByToken(requestVAgentEntity.getT());
        AgentEntity existAgentEntity = existCSEntity == null ? null : iAgentServices.retrieveAgentById(existCSEntity.getMappingId());
        if (existAgentEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        if (!existAgentEntity.getPassword().equals(requestVAgentEntity.getOrdPassword())) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        existAgentEntity.setPassword(requestVAgentEntity.getNewPassword());
        AgentEntity updateAgentEntity = iAgentServices.updateAgentPassword(existAgentEntity);
        if (updateAgentEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        existCSEntity.setToken(Code.getToken(updateAgentEntity.getCellphone()));
        CSEntity csEntity = icsServices.updateToken(existCSEntity);
        if (csEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(csEntity.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void retrieveAgentsByPId(String pid) {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = icsServices.retrieveByToken(requestVAgentEntity.getT());
        if (csEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t")));
            return;
        }
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveAgentByPId(csEntity.getMappingId());
        if (agentEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t")));
            return;
        }
        LinkedList<VAgentEntity> responseVAgentEntities = new LinkedList<>();
        for (AgentEntity agentEntity : agentEntities) {
            responseVAgentEntities.add(new VAgentEntity(agentEntity));
        }
        if (responseVAgentEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVAgentEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }
}
