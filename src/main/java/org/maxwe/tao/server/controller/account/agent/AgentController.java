package org.maxwe.tao.server.controller.account.agent;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.TokenContext;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.common.utils.TokenUtils;
import org.maxwe.tao.server.controller.account.agent.model.*;
import org.maxwe.tao.server.controller.account.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.level.ILevelServices;
import org.maxwe.tao.server.service.level.LevelEntity;
import org.maxwe.tao.server.service.level.LevelServices;

/**
 * Created by Pengwei Ding on 2017-01-09 18:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理账户接口
 */
public class AgentController extends Controller implements IAgentController {
    private final Logger logger = Logger.getLogger(AgentController.class.getName());
    private IAgentServices iAgentServices = new AgentServices();
    private ILevelServices iLevelServices = new LevelServices();

    @Override
    @Before({AppInterceptor.class})
    public void exist() {
        String params = this.getAttr("p");
        AccountExistRequestModel requestModel = JSON.parseObject(params, AccountExistRequestModel.class);
        if (requestModel == null || !requestModel.isAccountExistRequestParamsOk()) {
            this.logger.info("exist : 请求参数错误 " + requestModel.toString());
            AccountExistResponseModel agentExistResponseModel = new AccountExistResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            agentExistResponseModel.setMessage("请您输入正确的手机号码");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        //重复检测
        AgentEntity agentEntity = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (agentEntity != null) {
            this.logger.info("exist : 检测到重复账户 " + requestModel.toString());
            AccountExistResponseModel agentExistResponseModel = new AccountExistResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            agentExistResponseModel.setMessage("您输入的手机号码已被注册");
            agentExistResponseModel.setExistence(true);
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            this.logger.info("exist : 账户重复性检测通过 " + requestModel.toString());
            AccountExistResponseModel agentExistResponseModel = new AccountExistResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_SUCCESS);
            agentExistResponseModel.setMessage("您输入的手机号码可用");
            agentExistResponseModel.setExistence(false);
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class})
    public void signup() {
        String params = this.getAttr("p");
        AccountSignUpRequestModel requestModel = JSON.parseObject(params, AccountSignUpRequestModel.class);
        //参数检测
        if (requestModel == null || !requestModel.isAccountSignUpParamsOk()) {
            this.logger.info("register : 请求参数错误 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            agentExistResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("register : 请求参数中验证码错误 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_CONFLICT);
            agentExistResponseModel.setMessage("您输入的验证码错误");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        //重复检测 同一种类型下的同一个电话号码算是重复
        AgentEntity existAgent = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (existAgent != null) {
            this.logger.info("register : 重复注册 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            agentExistResponseModel.setMessage("您输入手机号码已被注册，请直接登录");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AgentEntity createAgent = new AgentEntity();
        createAgent.setCellphone(requestModel.getCellphone());
        createAgent.setPassword(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()));
        AgentEntity saveAgent = iAgentServices.create(createAgent);
        if (saveAgent == null) {
            this.logger.info("create : 注册失败-服务器内部错误 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            agentExistResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity csEntity = new CSEntity(saveAgent.getId(), saveAgent.getCellphone(), TokenUtils.getToken(saveAgent.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(csEntity);
            this.logger.info("create : 注册成功 " + requestModel.toString());
            //创建
            TokenModel sessionModel = new TokenModel(csEntity.getToken(), saveAgent.getId(), saveAgent.getCellphone());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel, sessionModel);
            agentExistResponseModel.setCode(ResponseModel.RC_SUCCESS);
            agentExistResponseModel.setMessage("注册成功");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }


    }

    @Override
    @Before({AppInterceptor.class})
    public void signin() {
        String params = this.getAttr("p");
        AccountSignInRequestModel requestModel = JSON.parseObject(params, AccountSignInRequestModel.class);
        if (requestModel == null || !requestModel.isAccountSignInParamsOk()) {
            this.logger.info("login : 登录参数错误 " + requestModel.toString());
            AccountSignInResponseModel accountSignInResponseModel = new AccountSignInResponseModel(requestModel);
            accountSignInResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            accountSignInResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(accountSignInResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        //查找
        AgentEntity agentEntity = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (agentEntity == null || !StringUtils.equals(agentEntity.getPassword(), PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()))) {
            this.logger.info("login : 用户名或密码错误，无法登陆 " + requestModel.toString());
            AccountSignInResponseModel accountSignInResponseModel = new AccountSignInResponseModel(requestModel);
            accountSignInResponseModel.setCode(ResponseModel.RC_FORBIDDEN);
            accountSignInResponseModel.setMessage("账户或密码错误");
            renderJson(JSON.toJSONString(accountSignInResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity csEntity = new CSEntity(agentEntity.getId(), agentEntity.getCellphone(), TokenUtils.getToken(agentEntity.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(csEntity);
            this.logger.info("login : 登录成功 " + requestModel.toString());
            TokenModel sessionModel = new TokenModel(csEntity.getToken(), agentEntity.getId(), agentEntity.getCellphone());
            AccountSignInResponseModel accountSignInResponseModel = new AccountSignInResponseModel(requestModel, sessionModel);
            accountSignInResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountSignInResponseModel.setMessage("登录成功");
            renderJson(JSON.toJSONString(accountSignInResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));

        }
    }


    @Override
    @Before({AppInterceptor.class})
    public void lost() {
        String params = this.getAttr("p");
        AccountLostRequestModel requestModel = JSON.parseObject(params, AccountLostRequestModel.class);
        //参数检测
        if (requestModel == null || !requestModel.isAccountLostRequestParamsOk()) {
            this.logger.info("lost : 请求参数错误 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            accountLostResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("lost : 验证码错误 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_CONFLICT);
            accountLostResponseModel.setMessage("您输入的验证码错误");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 注册检测
        AgentEntity existAgent = iAgentServices.retrieveByCellphone(requestModel.getCellphone());
        if (existAgent == null) {
            // 电话号码没有注册
            this.logger.info("lost : 电话号码没有注册，无法使用找回密码 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            accountLostResponseModel.setMessage("该号码没有注册，请前往注册");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        existAgent.setPassword(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()));
        AgentEntity updateAgent = iAgentServices.updatePassword(existAgent);
        if (updateAgent == null) {
            this.logger.info("lost : 找回密码失败-服务器内部错误 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            accountLostResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity csEntity = new CSEntity(updateAgent.getId(), updateAgent.getCellphone(), TokenUtils.getToken(updateAgent.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(csEntity);
            this.logger.info("lost : 找回密码成功 " + requestModel.toString());
            //创建
            TokenModel sessionModel = new TokenModel(csEntity.getToken(), updateAgent.getId(), updateAgent.getCellphone());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel, sessionModel);
            accountLostResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountLostResponseModel.setMessage("重置密码成功");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void password() {
        String params = this.getAttr("p");
        AccountModifyRequestModel requestModel = JSON.parseObject(params, AccountModifyRequestModel.class);
        AgentEntity existAgentEntity = iAgentServices.retrieveById(requestModel.getId());
        if (existAgentEntity == null) {
            this.logger.info("password : 修改密码没有查询到该记录-服务器内部错误 " + requestModel.toString());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            accountModifyResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (!StringUtils.equals(existAgentEntity.getPassword(), PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()))) {
            this.logger.info("password : 修改密码新旧密码不一致 " + requestModel.toString());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_CONFLICT);
            accountModifyResponseModel.setMessage("您输入的旧密码有误");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        existAgentEntity.setPassword(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()));
        AgentEntity updateAgentEntity = iAgentServices.updatePassword(existAgentEntity);
        if (updateAgentEntity == null) {
            this.logger.info("password : 修改密码-服务器内部更新错误 " + requestModel.toString());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            accountModifyResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity newCSEntity = new CSEntity(updateAgentEntity.getId(), updateAgentEntity.getCellphone(), TokenUtils.getToken(updateAgentEntity.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(newCSEntity);
            this.logger.info("password : 修改密码成功 " + requestModel.toString());
            TokenModel sessionModel = new TokenModel(newCSEntity.getToken(), updateAgentEntity.getId(), updateAgentEntity.getCellphone());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel, sessionModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountModifyResponseModel.setMessage("重置密码成功");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void signout() {
        String params = this.getAttr("p");
        AccountSignOutRequestModel requestModel = JSON.parseObject(params, AccountSignOutRequestModel.class);
        CSEntity csEntity = new CSEntity(0, requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        TokenContext.delCSEntity(csEntity);
        this.logger.info("logout : 退出成功 " + requestModel.toString());
        AccountSignOutResponseModel accountSignOutResponseModel = new AccountSignOutResponseModel();
        accountSignOutResponseModel.setCode(ResponseModel.RC_SUCCESS);
        accountSignOutResponseModel.setMessage("退出成功");
        renderJson(JSON.toJSONString(accountSignOutResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mine() {
        String params = this.getAttr("p");
        AgentMineRequestModel requestModel = JSON.parseObject(params, AgentMineRequestModel.class);
        AgentEntity agentEntity = iAgentServices.retrieveById(requestModel.getId());
        if (agentEntity == null) {
            AgentMineResponseModel agentMineResponseModel = new AgentMineResponseModel(requestModel);
            agentMineResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            agentMineResponseModel.setMessage("获取信息失败");
            renderJson(JSON.toJSONString(agentMineResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        LevelEntity levelEntity = iLevelServices.retrieveByLevel(agentEntity.getLevel());
        AgentModel agentModel = new AgentModel(agentEntity, levelEntity);
        AgentMineResponseModel agentMineResponseModel = new AgentMineResponseModel(requestModel);
        agentMineResponseModel.setCode(ResponseModel.RC_SUCCESS);
        agentMineResponseModel.setAgent(agentModel);
        agentMineResponseModel.setMessage("获取信息成功");
        renderJson(JSON.toJSONString(agentMineResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void bank() {
        String params = this.getAttr("p");
        AgentBankRequestModel requestModel = JSON.parseObject(params, AgentBankRequestModel.class);
        if (requestModel == null || !requestModel.isAgentBankRequestParamsOk()) {
            this.logger.info("bank : 请求参数错误 " + requestModel.toString());
            AgentBankResponseModel agentBankResponseModel = new AgentBankResponseModel(requestModel);
            agentBankResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            agentBankResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(agentBankResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        AgentEntity agentEntity = iAgentServices.retrieveById(requestModel.getId());
        if (agentEntity == null) {
            this.logger.info("bank : 没有找到要绑定的用户 " + requestModel.toString());
            AgentBankResponseModel agentBankResponseModel = new AgentBankResponseModel(requestModel);
            agentBankResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            agentBankResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(agentBankResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 如果密码不等
        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), agentEntity.getPassword())) {
            this.logger.info("bank : 密码错误 " + requestModel.toString());
            AgentBankResponseModel agentBankResponseModel = new AgentBankResponseModel(requestModel);
            agentBankResponseModel.setCode(ResponseModel.RC_CONFLICT);
            agentBankResponseModel.setMessage("您输入的密码有误");
            renderJson(JSON.toJSONString(agentBankResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 已经绑定过了
        if (!StringUtils.isEmpty(agentEntity.getZhifubao())) {
            this.logger.info("bank : 重复绑定 " + requestModel.toString());
            AgentBankResponseModel agentBankResponseModel = new AgentBankResponseModel(requestModel);
            agentBankResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            agentBankResponseModel.setMessage("您已经绑定过账号");
            renderJson(JSON.toJSONString(agentBankResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        agentEntity.setTrueName(requestModel.getTrueName());
        agentEntity.setZhifubao(requestModel.getZhifubao());
        agentEntity.setBankTime(System.currentTimeMillis());
        // 更新
        AgentEntity updateAgentEntity = iAgentServices.updateBank(agentEntity);
        if (updateAgentEntity == null) {
            this.logger.info("bank : 更新失败 " + requestModel.toString());
            AgentBankResponseModel agentBankResponseModel = new AgentBankResponseModel(requestModel);
            agentBankResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            agentBankResponseModel.setMessage("绑定失败，请重试");
            renderJson(JSON.toJSONString(agentBankResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            AgentBankResponseModel agentBankResponseModel = new AgentBankResponseModel(requestModel);
            agentBankResponseModel.setCode(ResponseModel.RC_SUCCESS);
            agentBankResponseModel.setMessage("绑定成功");
            agentBankResponseModel.setBankTime(System.currentTimeMillis());
            renderJson(JSON.toJSONString(agentBankResponseModel, new SerializeFilter[]{TokenModel.propertyFilter,TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }
}
