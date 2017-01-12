package org.maxwe.tao.server.controller.account.agent;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.controller.account.model.SessionModel;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.MarkUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.common.utils.TokenUtils;
import org.maxwe.tao.server.controller.account.agent.model.AgentModel;
import org.maxwe.tao.server.controller.account.agent.model.BankModel;
import org.maxwe.tao.server.controller.account.model.ExistModel;
import org.maxwe.tao.server.controller.account.model.LoginModel;
import org.maxwe.tao.server.controller.account.model.ModifyModel;
import org.maxwe.tao.server.controller.account.model.RegisterModel;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-09 18:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理账户接口
 */
public class AgentController extends Controller implements IAgentController {
    private final Logger logger = Logger.getLogger(AgentController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();

    @Override
    @Before(TokenInterceptor.class)
    public void bank() {
        String params = this.getAttr("p");
        BankModel requestModel = JSON.parseObject(params, BankModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("bank : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(),requestModel.getApt());
        CSEntity existCSEntity = SessionContext.getCSEntity(csEntity);
        AgentEntity agentEntity = iAgentServices.retrieveById(existCSEntity.getId());
        if (agentEntity == null) {
            this.logger.info("bank : 没有找到要更新的用户 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 如果密码不等
        if (!StringUtils.equals(requestModel.getPassword(), agentEntity.getPassword())) {
            this.logger.info("bank : 密码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 已经绑定过了
        if (!StringUtils.isEmpty(agentEntity.getZhifubao())) {
            this.logger.info("bank : 重复绑定 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 更新
        AgentEntity updateAgentEntity = iAgentServices.updateBank(agentEntity);
        if (updateAgentEntity == null) {
            this.logger.info("bank : 更新失败 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
        } else{
            requestModel.setTimestamp(System.currentTimeMillis());
            this.logger.info("bank : 绑定成功 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void exist() {
        String params = this.getAttr("p");
        ExistModel requestModel = JSON.parseObject(params, ExistModel.class);
        IResultSet iResultSet = new ResultSet();
        if (requestModel == null || !requestModel.isParamsOk()) {
            this.logger.info("exist : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        //重复检测
        AgentEntity agentEntity = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (agentEntity != null) {
            this.logger.info("exist : 检测到重复账户 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            this.logger.info("exist : 账户重复性检测通过 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));

        }
    }

    @Override
    public void register() {
        String params = this.getAttr("p");
        RegisterModel requestModel = JSON.parseObject(params, RegisterModel.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (requestModel == null || !requestModel.isParamsOK()) {
            this.logger.info("register : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
            return;
        }
        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("register : 请求参数中验证码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
            return;
        }
        //重复检测 同一种类型下的同一个电话号码算是重复
        AgentEntity existAgentEntity = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (existAgentEntity != null) {
            this.logger.info("register : 重复注册 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
            return;
        }

        AgentEntity agentEntity = new AgentEntity();
        agentEntity.setId(UUID.randomUUID().toString());
        agentEntity.setMark(MarkUtils.enMark(requestModel.getCellphone()));
        agentEntity.setCellphone(requestModel.getCellphone());
        agentEntity.setPassword(PasswordUtils.enPassword(requestModel.getPassword()));

        AgentEntity saveAgentEntity = iAgentServices.create(agentEntity);
        if (saveAgentEntity == null) {
            this.logger.info("create : 注册失败-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
        } else {
            CSEntity csEntity = new CSEntity(saveAgentEntity.getId(), saveAgentEntity.getCellphone(), TokenUtils.getToken(saveAgentEntity.getCellphone(), requestModel.getPassword()),requestModel.getApt());
            SessionContext.addCSEntity(csEntity);
            this.logger.info("create : 注册成功 " + requestModel.toString());

            //创建
            SessionModel sessionModel = new SessionModel(csEntity.getToken(), saveAgentEntity.getMark(), saveAgentEntity.getCellphone());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(sessionModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
        }
    }

    @Override
    public void lost() {
        String params = this.getAttr("p");
        RegisterModel requestModel = JSON.parseObject(params, RegisterModel.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (requestModel == null || !requestModel.isParamsOK()) {
            this.logger.info("lost : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        // 注册检测
        AgentEntity existAgent = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (existAgent == null) {
            // 电话号码没有注册
            this.logger.info("lost : 电话号码没有注册，无法使用找回密码 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("lost : 验证码错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        existAgent.setPassword(PasswordUtils.enPassword(requestModel.getPassword()));
        AgentEntity updateAgent = iAgentServices.updatePassword(existAgent);
        if (updateAgent == null) {
            this.logger.info("lost : 找回密码失败-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            CSEntity csEntity = new CSEntity(updateAgent.getId(), updateAgent.getCellphone(), TokenUtils.getToken(updateAgent.getCellphone(), requestModel.getPassword()),requestModel.getApt());
            SessionContext.addCSEntity(csEntity);
            this.logger.info("lost : 找回密码成功 " + requestModel.toString());
            //创建
            SessionModel sessionModel = new SessionModel(csEntity.getToken(), updateAgent.getMark(), updateAgent.getCellphone());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(sessionModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void login() {
        String params = this.getAttr("p");
        LoginModel requestModel = JSON.parseObject(params, LoginModel.class);
        IResultSet iResultSet = new ResultSet();
        if (requestModel == null || !requestModel.isParamsOK()) {
            this.logger.info("login : 登录参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, LoginModel.propertyFilter));
            return;
        }

        //查找
        AgentEntity agentEntity = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (agentEntity == null || !StringUtils.equals(agentEntity.getPassword(), PasswordUtils.enPassword(requestModel.getPassword()))) {
            this.logger.info("login : 用户名或密码错误，无法登陆 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, LoginModel.propertyFilter));
        } else {
            CSEntity csEntity = new CSEntity(agentEntity.getId(), agentEntity.getCellphone(), TokenUtils.getToken(agentEntity.getCellphone(), requestModel.getPassword()),requestModel.getApt());
            SessionContext.addCSEntity(csEntity);
            this.logger.info("login : 登录成功 " + requestModel.toString());

            SessionModel sessionModel = new SessionModel(csEntity.getToken(), agentEntity.getMark(), agentEntity.getCellphone());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(sessionModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before(TokenInterceptor.class)
    public void password() {
        String params = this.getAttr("p");
        ModifyModel requestModel = JSON.parseObject(params, ModifyModel.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(),requestModel.getApt());
        CSEntity existCSEntity = SessionContext.getCSEntity(csEntity);

        AgentEntity existAgentEntity = iAgentServices.retrieveById(existCSEntity.getId());
        if (existAgentEntity == null) {
            this.logger.info("password : 修改密码没有查询到该记录-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, ModifyModel.propertyFilter));
            return;
        }

        if (!StringUtils.equals(existAgentEntity.getPassword(), requestModel.getOldPassword())) {
            this.logger.info("password : 修改密码新旧密码不一致 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            renderJson(JSON.toJSONString(iResultSet, ModifyModel.propertyFilter));
            return;
        }

        existAgentEntity.setPassword(requestModel.getNewPassword());
        AgentEntity updateAgentEntity = iAgentServices.updatePassword(existAgentEntity);
        if (updateAgentEntity == null) {
            this.logger.info("password : 修改密码-服务器内部更新错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, ModifyModel.propertyFilter));
        } else {
            CSEntity newCSEntity = new CSEntity(updateAgentEntity.getId(), updateAgentEntity.getCellphone(), TokenUtils.getToken(updateAgentEntity.getCellphone(), requestModel.getNewPassword()),requestModel.getApt());
            SessionContext.addCSEntity(newCSEntity);
            this.logger.info("password : 修改密码成功 " + requestModel.toString());

            SessionModel sessionModel = new SessionModel(newCSEntity.getToken(), updateAgentEntity.getMark(), updateAgentEntity.getCellphone());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(sessionModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before(TokenInterceptor.class)
    public void logout() {
        String params = this.getAttr("p");
        SessionModel requestModel = JSON.parseObject(params, SessionModel.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(),requestModel.getApt());
        SessionContext.delCSEntity(csEntity);
        this.logger.info("logout : 退出成功 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(TokenInterceptor.class)
    public void mine() {
        String params = this.getAttr("p");
        AgentModel requestModel = JSON.parseObject(params, AgentModel.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT(),requestModel.getApt());
        AgentEntity agentEntity = iAgentServices.retrieveById(SessionContext.getCSEntity(csEntity).getId());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet, new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if ("id".equals(name)
                        || "password".equals(name)
                        || "status".equals(name)
                        || "pId".equals(name)
                        || "named".equals(name)
                        || "weight".equals(name)
                        ) {
                    return false;
                }
                return true;
            }
        });
        renderJson(resultJson);
    }
}
