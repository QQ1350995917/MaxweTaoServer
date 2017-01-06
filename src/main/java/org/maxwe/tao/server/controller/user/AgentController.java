package org.maxwe.tao.server.controller.user;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.taobao.api.ApiException;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.Code;
import org.maxwe.tao.server.common.utils.IPUtils;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.user.CSEntity;
import org.maxwe.tao.server.service.user.agent.AgentEntity;
import org.maxwe.tao.server.service.user.agent.AgentServices;
import org.maxwe.tao.server.service.user.agent.IAgentServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-12-25 14:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AgentController extends Controller implements IAgentController {
    private final Logger logger = Logger.getLogger(AgentController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();

    private PropertyFilter commonPropertyFilter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if ("agentId".equals(name) || "agentPId".equals(name) || "level".equals(name)){
                return false;
            }
            return true;
        }
    };

    public void index() {
        String info = "==========OK agent index==========";
        this.logger.info(info);
        renderJson(info);
    }

    @Override
    public void exist() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null) {
            this.logger.info("exist : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
            return;
        }
        //重复检测
        AgentEntity existAgent = iAgentServices.existAgent(requestVAgentEntity);

        if (existAgent != null) {
            String password = null;
            if (requestVAgentEntity.getType() == 1) {
                password = existAgent.getPassword1();
            } else if (requestVAgentEntity.getType() == 2) {
                password = existAgent.getPassword2();
            }
            if (password != null) {
                this.logger.info("exist : 检测到重复账户 " + params);
                iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
                iResultSet.setData(requestVAgentEntity);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
                return;
            }
        }

        this.logger.info("exist : 账户存在性检测通过 " + params);
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
            this.logger.info("smsCode : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
            return;
        }

        if (SMSManager.isCacheAddress(IPUtils.getIpAddress(this.getRequest()))) {
            this.logger.info("smsCode : 请求频繁 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_TO_MANY.getCode());
            iResultSet.setData(requestVAgentEntity.getCellphone());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "t")));
            return;
        }

        try {
            SMSManager.sendSMS(requestVAgentEntity.getCellphone());
            this.logger.info("smsCode : 验证码" + SMSManager.getCellphoneCode(requestVAgentEntity.getCellphone()) + "已经发送 " + params);
        } catch (ApiException e) {
            this.logger.error("smsCode : 验证码发送错误 " + params + "\r\n" + e.getMessage());
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
            this.logger.info("create : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        // 验证码检测
        if (!StringUtils.equals(requestVAgentEntity.getCellPhoneCode(), SMSManager.getCellphoneCode(requestVAgentEntity.getCellphone()))) {
            this.logger.info("create : 请求参数中验证码错误 " + params + "\r\n" + SMSManager.getCellphoneCode(requestVAgentEntity.getCellphone()));
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        //重复检测 同一种类型下的同一个电话号码算是重复
        AgentEntity existAgent = iAgentServices.existAgent(requestVAgentEntity);
        if (existAgent != null) {
            // 电话号码重复
            String password = null;
            if (requestVAgentEntity.getType() == 1) {
                password = existAgent.getPassword1();
            } else if (requestVAgentEntity.getType() == 2) {
                password = existAgent.getPassword2();
            }
            if (password != null) {
                // 电话号码注册类型重复
                this.logger.info("create : 重复注册 " + params);
                iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
                iResultSet.setData(requestVAgentEntity);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "cellphone", "password")));
                return;
            }
        }

        AgentEntity agent;
        if (existAgent == null) {
            //直接创建
            this.logger.info("create : 直接注册 " + params);
            requestVAgentEntity.setAgentId(UUID.randomUUID().toString());
            requestVAgentEntity.setPassword1(requestVAgentEntity.getPassword());
            requestVAgentEntity.setPassword2(requestVAgentEntity.getPassword());
            agent = iAgentServices.createAgent(requestVAgentEntity);
        } else {
            this.logger.info("create : 更新注册 " + params);
            if (requestVAgentEntity.getType() == 1) {
                existAgent.setPassword1(requestVAgentEntity.getPassword());
            } else if (requestVAgentEntity.getType() == 2) {
                existAgent.setPassword2(requestVAgentEntity.getPassword());
            }
            agent = iAgentServices.updateAgentType(existAgent);
        }

        if (agent == null) {
            this.logger.info("create : 注册失败-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity agentCS = new CSEntity(agent.getAgentId(), agent.getCellphone(), Code.getToken(agent.getCellphone(), requestVAgentEntity.getPassword()), requestVAgentEntity.getType());
        SessionContext.addCSEntity(agentCS);
        this.logger.info("create : 注册成功 " + params);

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
            this.logger.info("lost : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        // 注册检测
        AgentEntity existAgent = iAgentServices.existAgent(requestVAgentEntity);
        if (existAgent == null) {
            // 电话号码没有注册
            this.logger.info("lost : 电话号码没有注册，无法使用找回密码 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        } else {
            // 电话号码注册，但是该类型下的没注册
            String password = null;
            if (requestVAgentEntity.getType() == 1) {
                password = existAgent.getPassword1();
            } else if (requestVAgentEntity.getType() == 2) {
                password = existAgent.getPassword2();
            }
            if (password == null) {
                this.logger.info("lost : 电话号码没有注册该类型业务，无法使用找回密码 " + params);
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
                iResultSet.setData(requestVAgentEntity);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
                return;
            }
        }

        // 验证码检测
        if (!StringUtils.equals(requestVAgentEntity.getCellPhoneCode(), SMSManager.getCellphoneCode(requestVAgentEntity.getCellphone()))) {
            this.logger.info("lost : 验证码错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }
        if (requestVAgentEntity.getType() == 1) {
            existAgent.setPassword1(requestVAgentEntity.getPassword());
        } else if (requestVAgentEntity.getType() == 2) {
            existAgent.setPassword2(requestVAgentEntity.getPassword());
        }
        existAgent.setType(requestVAgentEntity.getType());
        AgentEntity agent = iAgentServices.updateAgentPassword(existAgent);
        if (agent == null) {
            this.logger.info("lost : 找回密码失败-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity agentCS = new CSEntity(agent.getAgentId(), agent.getCellphone(), Code.getToken(agent.getCellphone(), requestVAgentEntity.getPassword()), requestVAgentEntity.getType());
        SessionContext.addCSEntity(agentCS);
        this.logger.info("lost : 找回密码成功 " + params);
        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void login() {
        String params = this.getPara("p");
        this.logger.info("login = " + params);
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || !requestVAgentEntity.checkLoginParams()) {
            this.logger.info("login : 登录参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        //查找
        requestVAgentEntity.setPassword1(requestVAgentEntity.getPassword());
        requestVAgentEntity.setPassword2(requestVAgentEntity.getPassword());
        AgentEntity agentEntity = iAgentServices.retrieveAgent(requestVAgentEntity);
        if (agentEntity == null) {
            this.logger.info("login : 用户没有注册，无法登陆 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone", "password")));
            return;
        }

        CSEntity agentCS = new CSEntity(agentEntity.getAgentId(), agentEntity.getCellphone(), Code.getToken(agentEntity.getCellphone(), requestVAgentEntity.getPassword()), requestVAgentEntity.getType());
        SessionContext.addCSEntity(agentCS);
        this.logger.info("login : 登录成功 " + params);

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(TokenInterceptor.class)
    public void logout() {
        String params = this.getPara("p");
        this.logger.info("login = " + params);
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || StringUtils.isEmpty(requestVAgentEntity.getT())) {
            this.logger.info("logout : 退出参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t")));
            return;
        }
        CSEntity csEntity = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        SessionContext.delCSEntity(csEntity);
        this.logger.info("logout : 退出成功 " + params);

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(TokenInterceptor.class)
    public void password() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVAgentEntity == null || !requestVAgentEntity.checkModifyPasswordParams()) {
            this.logger.info("password : 修改密码参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        CSEntity csEntity = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        CSEntity existCSEntity = SessionContext.getCSEntity(csEntity);
        AgentEntity existAgentEntity = iAgentServices.retrieveAgentById(existCSEntity.getAgentId());
        if (existAgentEntity == null) {
            this.logger.info("password : 修改密码没有查询到该记录-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        if (requestVAgentEntity.getType() == 1) {
            if (!existAgentEntity.getPassword1().equals(requestVAgentEntity.getOrdPassword())) {
                this.logger.info("password : 修改密码旧密码不一致 " + params);
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
                iResultSet.setData(requestVAgentEntity);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
                return;
            }
            existAgentEntity.setPassword1(requestVAgentEntity.getNewPassword());
        } else if (requestVAgentEntity.getType() == 2) {
            if (!existAgentEntity.getPassword2().equals(requestVAgentEntity.getOrdPassword())) {
                this.logger.info("password : 修改密码旧密码不一致 " + params);
                iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
                iResultSet.setData(requestVAgentEntity);
                iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
                return;
            }
            existAgentEntity.setPassword2(requestVAgentEntity.getNewPassword());
        }
        existAgentEntity.setType(requestVAgentEntity.getType());
        AgentEntity updateAgentEntity = iAgentServices.updateAgentPassword(existAgentEntity);
        if (updateAgentEntity == null) {
            this.logger.info("password : 修改密码-服务器内部更新错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAgentEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "orderPassword", "newPassword")));
            return;
        }

        CSEntity agentCS = new CSEntity(updateAgentEntity.getAgentId(), updateAgentEntity.getCellphone(), Code.getToken(updateAgentEntity.getCellphone(), requestVAgentEntity.getNewPassword()), requestVAgentEntity.getType());
        SessionContext.addCSEntity(agentCS);
        this.logger.info("password : 修改密码成功 " + params);

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    /**
     * 获取自己的信息
     */
    @Override
    @Before(TokenInterceptor.class)
    public void agent() {
        String params = this.getPara("p");
        VAgentEntity requestVAgentEntity = JSON.parseObject(params, VAgentEntity.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity agentCS = new CSEntity(null, requestVAgentEntity.getCellphone(), requestVAgentEntity.getT(), requestVAgentEntity.getType());
        AgentEntity agentEntity = iAgentServices.retrieveAgentById(SessionContext.getCSEntity(agentCS).getAgentId());
        VAgentEntity result = new VAgentEntity(agentEntity);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(result);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "t", "cellphone", "cellphone", "code", "type", "level", "status", "haveCodes", "spendCodes", "leftCodes", "createTime")));
    }
}
