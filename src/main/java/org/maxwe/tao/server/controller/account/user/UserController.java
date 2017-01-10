package org.maxwe.tao.server.controller.account.user;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.model.SessionModel;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.MarkUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.common.utils.TokenUtils;
import org.maxwe.tao.server.controller.account.agent.model.AgentModel;
import org.maxwe.tao.server.controller.account.model.ExistModel;
import org.maxwe.tao.server.controller.account.model.LoginModel;
import org.maxwe.tao.server.controller.account.model.ModifyModel;
import org.maxwe.tao.server.controller.account.model.RegisterModel;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.user.IUserServices;
import org.maxwe.tao.server.service.account.user.UserEntity;
import org.maxwe.tao.server.service.account.user.UserServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.history.HistoryServices;
import org.maxwe.tao.server.service.history.IHistoryServices;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-01-09 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserController extends Controller implements IUserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    private IUserServices iUserServices = new UserServices();
    private IHistoryServices iHistoryServices = new HistoryServices();
    @Override
    @Before(TokenInterceptor.class)
    public void active() {
        String params = this.getPara("p");
        ActiveModel requestModel = JSON.parseObject(params, ActiveModel.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestModel.isParamsOk()) {
            this.logger.info("active : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        HistoryEntity historyEntity = iHistoryServices.retrieveByActCode(requestModel.getActCode());
        if (historyEntity == null
                || !StringUtils.isEmpty(historyEntity.getToId())
                || historyEntity.getType() != 1){
            this.logger.info("active : 请求参数错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }


        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        UserEntity userEntity = iUserServices.retrieveById(SessionContext.getCSEntity(agentCS).getId());
        historyEntity.setToId(userEntity.getId());
        HistoryEntity updateHistoryEntity = iHistoryServices.updateToId(historyEntity);
        if (updateHistoryEntity == null){
            this.logger.info("active : 激活失败-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("active : 激活成功 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));

    }

    @Override
    public void exist() {
        String params = this.getPara("p");
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
        UserEntity userEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (userEntity != null) {
            this.logger.info("exist : 检测到重复账户 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("exist : 账户重复性检测通过 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void register() {
        String params = this.getPara("p");
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
        UserEntity existUserEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (existUserEntity != null) {
            this.logger.info("register : 重复注册 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setMark(MarkUtils.deMark(requestModel.getCellphone()));
        userEntity.setCellphone(requestModel.getCellphone());
        userEntity.setPassword(PasswordUtils.enPassword(requestModel.getPassword()));

        UserEntity saveUserEntity = iUserServices.create(userEntity);
        if (saveUserEntity == null) {
            this.logger.info("create : 注册失败-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
            return;
        }

        CSEntity agentCS = new CSEntity(saveUserEntity.getId(), saveUserEntity.getCellphone(), TokenUtils.getToken(saveUserEntity.getCellphone(), requestModel.getPassword()));
        SessionContext.addCSEntity(agentCS);
        this.logger.info("create : 注册成功 " + requestModel.toString());

        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, RegisterModel.propertyFilter));
    }

    @Override
    public void lost() {
        String params = this.getPara("p");
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
        UserEntity existEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (existEntity == null) {
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

        existEntity.setPassword(PasswordUtils.enPassword(requestModel.getPassword()));
        UserEntity updateUser = iUserServices.updatePassword(existEntity);
        if (updateUser == null) {
            this.logger.info("lost : 找回密码失败-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        CSEntity agentCS = new CSEntity(updateUser.getId(), updateUser.getCellphone(), TokenUtils.getToken(updateUser.getCellphone(), requestModel.getPassword()));
        SessionContext.addCSEntity(agentCS);
        this.logger.info("lost : 找回密码成功 " + requestModel.toString());
        //创建
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void login() {
        String params = this.getPara("p");
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
        UserEntity userEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (userEntity == null || !StringUtils.equals(userEntity.getPassword(), PasswordUtils.enPassword(requestModel.getPassword()))) {
            this.logger.info("login : 用户名或密码错误，无法登陆 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, LoginModel.propertyFilter));
            return;
        }

        CSEntity agentCS = new CSEntity(userEntity.getId(), userEntity.getCellphone(), TokenUtils.getToken(userEntity.getCellphone(), requestModel.getPassword()));
        SessionContext.addCSEntity(agentCS);
        this.logger.info("login : 登录成功 " + requestModel.toString());

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(TokenInterceptor.class)
    public void password() {
        String params = this.getPara("p");
        ModifyModel requestModel = JSON.parseObject(params, ModifyModel.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        CSEntity existCSEntity = SessionContext.getCSEntity(csEntity);

        UserEntity existUserEntity = iUserServices.retrieveById(existCSEntity.getId());
        if (existUserEntity == null) {
            this.logger.info("password : 修改密码没有查询到该记录-服务器内部错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, ModifyModel.propertyFilter));
            return;
        }

        if (!StringUtils.equals(existUserEntity.getPassword(), requestModel.getOldPassword())) {
            this.logger.info("password : 修改密码新旧密码不一致 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            renderJson(JSON.toJSONString(iResultSet, ModifyModel.propertyFilter));
            return;
        }

        existUserEntity.setPassword(requestModel.getNewPassword());
        UserEntity updateUserEntity = iUserServices.updatePassword(existUserEntity);
        if (updateUserEntity == null) {
            this.logger.info("password : 修改密码-服务器内部更新错误 " + requestModel.toString());
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, ModifyModel.propertyFilter));
            return;
        }

        CSEntity agentCS = new CSEntity(updateUserEntity.getId(), updateUserEntity.getCellphone(), TokenUtils.getToken(updateUserEntity.getCellphone(), requestModel.getNewPassword()));
        SessionContext.addCSEntity(agentCS);
        this.logger.info("password : 修改密码成功 " + requestModel.toString());

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(TokenInterceptor.class)
    public void logout() {
        String params = this.getPara("p");
        SessionModel requestModel = JSON.parseObject(params, SessionModel.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity csEntity = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        SessionContext.delCSEntity(csEntity);
        this.logger.info("logout : 退出成功 " + requestModel.toString());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(TokenInterceptor.class)
    public void mine() {
        String params = this.getPara("p");
        AgentModel requestModel = JSON.parseObject(params, AgentModel.class);
        IResultSet iResultSet = new ResultSet();
        CSEntity agentCS = new CSEntity(null, requestModel.getCellphone(), requestModel.getT());
        UserEntity userEntity = iUserServices.retrieveById(SessionContext.getCSEntity(agentCS).getId());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(userEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        String resultJson = JSON.toJSONString(iResultSet,new PropertyFilter() {
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
