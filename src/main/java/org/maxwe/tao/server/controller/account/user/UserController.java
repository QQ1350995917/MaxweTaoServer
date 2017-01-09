package org.maxwe.tao.server.controller.account.user;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.model.SessionModel;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.Code;
import org.maxwe.tao.server.controller.account.model.ExistModel;
import org.maxwe.tao.server.controller.account.model.LoginModel;
import org.maxwe.tao.server.controller.account.model.ModifyModel;
import org.maxwe.tao.server.controller.account.model.RegisterModel;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.user.IUserServices;
import org.maxwe.tao.server.service.account.user.UserEntity;
import org.maxwe.tao.server.service.account.user.UserServices;

/**
 * Created by Pengwei Ding on 2017-01-09 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserController extends Controller implements IUserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    private IUserServices iUserServices = new UserServices();

    @Override
    public void active() {
        String params = this.getPara("p");
        ActiveModel requestModel = JSON.parseObject(params, ActiveModel.class);
        IResultSet iResultSet = new ResultSet();

    }

    @Override
    public void exist() {
        String params = this.getPara("p");
        ExistModel requestModel = JSON.parseObject(params, ExistModel.class);
        IResultSet iResultSet = new ResultSet();
        if (requestModel == null || !requestModel.isParamsOk()) {
            this.logger.info("exist : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        //重复检测
        UserEntity userEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (userEntity != null) {
            this.logger.info("exist : 检测到重复账户 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        this.logger.info("exist : 账户重复性检测通过 " + params);
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
            this.logger.info("create : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("create : 请求参数中验证码错误 " + params + "\r\n" + SMSManager.getSMSCode(requestModel.getCellphone()));
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        //重复检测 同一种类型下的同一个电话号码算是重复
        UserEntity existUserEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (existUserEntity != null) {
            this.logger.info("create : 重复注册 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        UserEntity userEntity = new UserEntity();
        // TODO 设置必要的参数
        UserEntity saveUserEntity = iUserServices.create(userEntity);
        if (saveUserEntity == null) {
            this.logger.info("create : 注册失败-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        CSEntity agentCS = new CSEntity(saveUserEntity.getId(), saveUserEntity.getMark(),saveUserEntity.getCellphone(), Code.getToken(saveUserEntity.getCellphone(), requestModel.getPassword()));
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
        RegisterModel requestModel = JSON.parseObject(params, RegisterModel.class);
        IResultSet iResultSet = new ResultSet();
        //参数检测
        if (requestModel == null || !requestModel.isParamsOK()) {
            this.logger.info("lost : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        // 注册检测
        UserEntity existUser = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (existUser == null) {
            // 电话号码没有注册
            this.logger.info("lost : 电话号码没有注册，无法使用找回密码 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD_2.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("lost : 验证码错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        existUser.setPassword(requestModel.getPassword());
        UserEntity userAgent = iUserServices.updatePassword(existUser);
        if (userAgent == null) {
            this.logger.info("lost : 找回密码失败-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }
        // TODO 这里的 MARK 为空
        CSEntity agentCS = new CSEntity(userAgent.getId(), userAgent.getMark(),userAgent.getCellphone(), Code.getToken(userAgent.getCellphone(), requestModel.getPassword()));
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
        LoginModel requestMode = JSON.parseObject(params, LoginModel.class);
        IResultSet iResultSet = new ResultSet();
        if (requestMode == null || !requestMode.isParamsOK()) {
            this.logger.info("login : 登录参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestMode);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // TODO 这里要有登录接口
        //查找
        UserEntity userEntity = iUserServices.retrieveByCellphone(requestMode.getCellphone());
        if (userEntity == null) {
            this.logger.info("login : 用户没有注册，无法登陆 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestMode);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // TODO 这里的 mark 为空
        CSEntity agentCS = new CSEntity(userEntity.getId(), userEntity.getMark(),userEntity.getCellphone(), Code.getToken(userEntity.getCellphone(), requestMode.getPassword()));
        SessionContext.addCSEntity(agentCS);
        this.logger.info("login : 登录成功 " + params);

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void password() {
        String params = this.getPara("p");
        ModifyModel requestModel = JSON.parseObject(params, ModifyModel.class);
        IResultSet iResultSet = new ResultSet();
        // TODO 这里 mark 为空
        CSEntity csEntity = new CSEntity(null, requestModel.getMark(), requestModel.getCellphone(), requestModel.getT());
        CSEntity existCSEntity = SessionContext.getCSEntity(csEntity);

        UserEntity existUserEntity = iUserServices.retrieveById(existCSEntity.getId());
        if (existUserEntity == null) {
            this.logger.info("password : 修改密码没有查询到该记录-服务器内部错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            // TODO 检查返回参数
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        if (!StringUtils.equals(existUserEntity.getPassword(), requestModel.getOldPassword())) {
            this.logger.info("password : 修改密码旧密码不一致 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_BAD);
            // TODO 检查返回参数
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        existUserEntity.setPassword(requestModel.getNewPassword());
        UserEntity updateUserEntity = iUserServices.updatePassword(existUserEntity);
        if (updateUserEntity == null) {
            this.logger.info("password : 修改密码-服务器内部更新错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            // TODO 检查返回参数
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        // TODO 密码加密存储后 这里生成的token是会变化的
        CSEntity agentCS = new CSEntity(updateUserEntity.getId(), updateUserEntity.getMark(), updateUserEntity.getCellphone(), Code.getToken(updateUserEntity.getCellphone(), requestModel.getNewPassword()));
        SessionContext.addCSEntity(agentCS);
        this.logger.info("password : 修改密码成功 " + params);

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(agentCS.getToken());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void logout() {
        String params = this.getPara("p");
        SessionModel requestMode = JSON.parseObject(params, SessionModel.class);
        IResultSet iResultSet = new ResultSet();
        // TODO 这里的 mark 为空
        CSEntity csEntity = new CSEntity(null, requestMode.getMark(),requestMode.getCellphone(), requestMode.getT());
        SessionContext.delCSEntity(csEntity);
        this.logger.info("logout : 退出成功 " + params);

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mine() {
        String params = this.getPara("p");
        // TODO 这里使用的model好像不对
        SessionModel requestModel = JSON.parseObject(params, SessionModel.class);
        IResultSet iResultSet = new ResultSet();
        // TODO 这里使用 mark 是为空
        CSEntity agentCS = new CSEntity(null, requestModel.getMark(), requestModel.getCellphone(), requestModel.getT());
        UserEntity userEntity = iUserServices.retrieveById(SessionContext.getCSEntity(agentCS).getId());
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel); // TODO 这里返回值也是不对的
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        // TODO 检查返回参数
        String resultJson = JSON.toJSONString(iResultSet);
        renderJson(resultJson);
    }
}
