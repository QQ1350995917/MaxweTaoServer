package org.maxwe.tao.server.controller.account.user;

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
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.common.utils.TokenUtils;
import org.maxwe.tao.server.controller.account.model.*;
import org.maxwe.tao.server.controller.account.user.model.*;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.account.CSEntity;
import org.maxwe.tao.server.service.account.user.IUserServices;
import org.maxwe.tao.server.service.account.user.UserEntity;
import org.maxwe.tao.server.service.account.user.UserServices;
import org.maxwe.tao.server.service.history.HistoryEntity;
import org.maxwe.tao.server.service.history.HistoryServices;
import org.maxwe.tao.server.service.history.IHistoryServices;

/**
 * Created by Pengwei Ding on 2017-01-09 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class UserController extends Controller implements IUserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    private IUserServices iUserServices = new UserServices();
    private IHistoryServices iHistoryServices = new HistoryServices();

    @Override
    @Before({AppInterceptor.class})
    public void exist() {
        String params = this.getAttr("p");
        AccountExistRequestModel requestModel = JSON.parseObject(params, AccountExistRequestModel.class);
        if (requestModel == null || !requestModel.isAccountExistRequestParamsOk()) {
            this.logger.info("exist : 请求参数错误 " + requestModel.toString());
            AccountExistResponseModel accountExistResponseModel = new AccountExistResponseModel(requestModel);
            accountExistResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            accountExistResponseModel.setMessage("请您输入正确的手机号码");
            renderJson(JSON.toJSONString(accountExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        //重复检测
        UserEntity userEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (userEntity != null) {
            this.logger.info("exist : 检测到重复账户 " + requestModel.toString());
            AccountExistResponseModel agentExistResponseModel = new AccountExistResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            agentExistResponseModel.setMessage("您输入的手机号码已被注册");
            agentExistResponseModel.setExistence(true);
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            this.logger.info("exist : 账户重复性检测通过 " + requestModel.toString());
            AccountExistResponseModel agentExistResponseModel = new AccountExistResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_SUCCESS);
            agentExistResponseModel.setMessage("您输入的手机号码可用");
            agentExistResponseModel.setExistence(false);
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class})
    public void signup() {
        String params = this.getAttr("p");
        AccountSignUpRequestModel requestModel = JSON.parseObject(params, AccountSignUpRequestModel.class);
        signUp(requestModel);
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
            renderJson(JSON.toJSONString(accountSignInResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        //查找
        UserEntity userEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (userEntity == null || !StringUtils.equals(userEntity.getPassword(), PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()))) {
            this.logger.info("login : 用户名或密码错误，无法登陆 " + requestModel.toString());
            AccountSignInResponseModel accountSignInResponseModel = new AccountSignInResponseModel(requestModel);
            accountSignInResponseModel.setCode(ResponseModel.RC_FORBIDDEN);
            accountSignInResponseModel.setMessage("账户或密码错误");
            renderJson(JSON.toJSONString(accountSignInResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity csEntity = new CSEntity(userEntity.getCellphone(), TokenUtils.getToken(userEntity.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(csEntity);
            this.logger.info("login : 登录成功 " + requestModel.toString());

            TokenModel sessionModel = new TokenModel(csEntity.getToken(), userEntity.getId(), userEntity.getCellphone());
            AccountSignInResponseModel accountSignInResponseModel = new AccountSignInResponseModel(requestModel, sessionModel);
            accountSignInResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountSignInResponseModel.setMessage("登录成功");
            renderJson(JSON.toJSONString(accountSignInResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
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
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        // 注册检测
        UserEntity existEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (existEntity == null) {
            // 电话号码没有注册
            this.logger.info("lost : 电话号码没有注册，无法使用找回密码 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_CONFLICT);
            accountLostResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("lost : 验证码错误 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            accountLostResponseModel.setMessage("您输入的验证码错误");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        existEntity.setPassword(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()));
        UserEntity updateUser = iUserServices.updatePassword(existEntity);
        if (updateUser == null) {
            this.logger.info("lost : 找回密码失败-服务器内部错误 " + requestModel.toString());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel);
            accountLostResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            accountLostResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity csEntity = new CSEntity(updateUser.getCellphone(), TokenUtils.getToken(updateUser.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(csEntity);
            this.logger.info("lost : 找回密码成功 " + requestModel.toString());
            //创建
            TokenModel sessionModel = new TokenModel(csEntity.getToken(), updateUser.getId(), updateUser.getCellphone());
            AccountLostResponseModel accountLostResponseModel = new AccountLostResponseModel(requestModel, sessionModel);
            accountLostResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountLostResponseModel.setMessage("重置密码成功");
            renderJson(JSON.toJSONString(accountLostResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void password() {
        String params = this.getAttr("p");
        AccountModifyRequestModel requestModel = JSON.parseObject(params, AccountModifyRequestModel.class);
        UserEntity existUserEntity = iUserServices.retrieveById(requestModel.getId());
        if (existUserEntity == null) {
            this.logger.info("password : 修改密码没有查询到该记录-服务器内部错误 " + requestModel.toString());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            accountModifyResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (!StringUtils.equals(existUserEntity.getPassword(), PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()))) {
            this.logger.info("password : 修改密码新旧密码不一致 " + requestModel.toString());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_CONFLICT);
            accountModifyResponseModel.setMessage("您输入的旧密码有误");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        existUserEntity.setPassword(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()));
        UserEntity updateUserEntity = iUserServices.updatePassword(existUserEntity);
        if (updateUserEntity == null) {
            this.logger.info("password : 修改密码-服务器内部更新错误 " + requestModel.toString());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            accountModifyResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity newCSEntity = new CSEntity(updateUserEntity.getCellphone(), TokenUtils.getToken(updateUserEntity.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(newCSEntity);
            this.logger.info("password : 修改密码成功 " + requestModel.toString());
            TokenModel sessionModel = new TokenModel(newCSEntity.getToken(), updateUserEntity.getId(), updateUserEntity.getCellphone());
            AccountModifyResponseModel accountModifyResponseModel = new AccountModifyResponseModel(requestModel, sessionModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountModifyResponseModel.setMessage("重置密码成功");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void signout() {
        String params = this.getAttr("p");
        AccountSignOutRequestModel requestModel = JSON.parseObject(params, AccountSignOutRequestModel.class);
        CSEntity csEntity = new CSEntity(requestModel.getCellphone(), requestModel.getT(), requestModel.getApt());
        TokenContext.delCSEntity(csEntity);
        this.logger.info("logout : 退出成功 " + requestModel.toString());
        AccountSignOutResponseModel accountSignOutResponseModel = new AccountSignOutResponseModel();
        accountSignOutResponseModel.setCode(ResponseModel.RC_SUCCESS);
        accountSignOutResponseModel.setMessage("退出成功");
        renderJson(JSON.toJSONString(accountSignOutResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void mine() {
        String params = this.getAttr("p");
        UserMineRequestModel requestModel = JSON.parseObject(params, UserMineRequestModel.class);
        UserEntity userEntity = iUserServices.retrieveById(requestModel.getId());
        if (userEntity == null) {
            this.logger.info("mine : 服务器内部错误 " + requestModel.toString());
            UserMineResponseModel accountModifyResponseModel = new UserMineResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            accountModifyResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            this.logger.info("mine : 执行成功 " + requestModel.toString());
            UserMineResponseModel accountModifyResponseModel = new UserMineResponseModel(requestModel);
            accountModifyResponseModel.setCode(ResponseModel.RC_SUCCESS);
            accountModifyResponseModel.setUser(userEntity);
            accountModifyResponseModel.setMessage("获取成功");
            renderJson(JSON.toJSONString(accountModifyResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void active() {
        String params = this.getAttr("p");
        UserActiveRequestModel requestModel = JSON.parseObject(params, UserActiveRequestModel.class);
        if (requestModel == null || !requestModel.isUserActiveRequestParamsOk()) {
            this.logger.info("active : 请求参数错误 " + requestModel.toString());
            UserActiveResponseModel userActiveResponseModel = new UserActiveResponseModel(requestModel);
            userActiveResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            userActiveResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(userActiveResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        HistoryEntity historyEntity = iHistoryServices.retrieveByActCode(requestModel.getActCode());
        if (historyEntity == null || historyEntity.getFromId() == 0 || historyEntity.getType() != 1) {
            this.logger.info("active : 找不到该授权码 " + requestModel.toString());
            UserActiveResponseModel userActiveResponseModel = new UserActiveResponseModel(requestModel);
            userActiveResponseModel.setCode(ResponseModel.RC_NOT_FOUND);
            userActiveResponseModel.setMessage("找不到您的授权码");
            renderJson(JSON.toJSONString(userActiveResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        if (historyEntity.getToId() != 0) {
            this.logger.info("active :  该授权码已被激活" + requestModel.toString());
            UserActiveResponseModel userActiveResponseModel = new UserActiveResponseModel(requestModel);
            userActiveResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            userActiveResponseModel.setMessage("您的授权码已被激活");
            renderJson(JSON.toJSONString(userActiveResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        UserEntity userEntity = iUserServices.retrieveById(requestModel.getId());
        if (!StringUtils.equals(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getAuthenticatePassword()), userEntity.getPassword())) {
            this.logger.info("grant : 认证密码错误 " + requestModel.toString());
            UserActiveResponseModel userActiveResponseModel = new UserActiveResponseModel(requestModel);
            userActiveResponseModel.setCode(ResponseModel.RC_CONFLICT);
            userActiveResponseModel.setMessage("您的密码错误");
            renderJson(JSON.toJSONString(userActiveResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        userEntity.setActCode(requestModel.getActCode());
        historyEntity.setToId(userEntity.getId());
        boolean updateActiveStatus = iUserServices.updateActiveStatus(userEntity, historyEntity);
        if (!updateActiveStatus) {
            this.logger.info("active : 激活失败-服务器内部错误 " + requestModel.toString());
            UserActiveResponseModel userActiveResponseModel = new UserActiveResponseModel(requestModel);
            userActiveResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            userActiveResponseModel.setMessage("激活失败,请重试");
            renderJson(JSON.toJSONString(userActiveResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            this.logger.info("active : 激活成功 " + requestModel.toString());
            UserActiveResponseModel userActiveResponseModel = new UserActiveResponseModel(requestModel);
            userActiveResponseModel.setCode(ResponseModel.RC_SUCCESS);
            userActiveResponseModel.setMessage("激活成功");
            userActiveResponseModel.setActTime(System.currentTimeMillis());
            renderJson(JSON.toJSONString(userActiveResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void updateReason() {
        String params = this.getAttr("p");
        UpdateReasonRequestModel requestModel = JSON.parseObject(params, UpdateReasonRequestModel.class);
        if (requestModel == null || !requestModel.isReasonParamsOk()) {
            this.logger.info("updateReason : 请求参数错误 " + requestModel.toString());
            UpdateReasonResponseModel responseModel = new UpdateReasonResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        UserEntity existUserEntity = iUserServices.retrieveById(requestModel.getId());
        if (existUserEntity == null) {
            this.logger.info("updateReason : 修改加入申请理由没有查询到该记录-服务器内部错误 " + requestModel.toString());
            UpdateReasonResponseModel responseModel = new UpdateReasonResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("修改错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        existUserEntity.setReason(requestModel.getReason());
        boolean updateReason = iUserServices.updateReason(existUserEntity);
        if (updateReason) {
            this.logger.info("updateReason : 修改加入申请理由成功 " + requestModel.toString());
            UpdateReasonResponseModel responseModel = new UpdateReasonResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SUCCESS);
            responseModel.setMessage("修改成功");
            responseModel.setReason(requestModel.getReason());
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        } else {
            this.logger.info("updateReason : 修改加入申请理由失败 " + requestModel.toString());
            UpdateReasonResponseModel responseModel = new UpdateReasonResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("修改失败");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
    }

    @Override
    @Before({AppInterceptor.class, TokenInterceptor.class})
    public void updateRhetoric() {
        String params = this.getAttr("p");
        UpdateRhetoricRequestModel requestModel = JSON.parseObject(params, UpdateRhetoricRequestModel.class);
        if (requestModel == null || !requestModel.isRhetoricParamsOk()) {
            this.logger.info("updateRhetoric : 请求参数错误 " + requestModel.toString());
            UpdateRhetoricResponseModel responseModel = new UpdateRhetoricResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            responseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        UserEntity existUserEntity = iUserServices.retrieveById(requestModel.getId());
        if (existUserEntity == null) {
            this.logger.info("updateRhetoric : 修改分享推广说辞没有查询到该记录-服务器内部错误 " + requestModel.toString());
            UpdateRhetoricResponseModel responseModel = new UpdateRhetoricResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_NOT_FOUND);
            responseModel.setMessage("修改错误，请重试");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        existUserEntity.setRhetoric(requestModel.getRhetoric());
        boolean updateReason = iUserServices.updateRhetoric(existUserEntity);
        if (updateReason) {
            this.logger.info("updateRhetoric : 修改分享推广说辞成功 " + requestModel.toString());
            UpdateRhetoricResponseModel responseModel = new UpdateRhetoricResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SUCCESS);
            responseModel.setMessage("修改成功");
            responseModel.setRhetoric(requestModel.getRhetoric());
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        } else {
            this.logger.info("updateRhetoric : 修改分享推广说辞失败 " + requestModel.toString());
            UpdateRhetoricResponseModel responseModel = new UpdateRhetoricResponseModel(requestModel);
            responseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            responseModel.setMessage("修改失败");
            renderJson(JSON.toJSONString(responseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
    }

    @Override
    public void referenceInfo() {
        int id = this.getParaToInt("id") == null ? -1 : this.getParaToInt("id");
        this.setAttr("id", 0);
        this.setAttr("refNum", 0);
        this.setAttr("refNumByMonth", 0);
        this.setAttr("refBalance", 0);

        if (id > 0) {
            UserEntity userEntity = iUserServices.retrieveById(id);
            int[] currentYearMonthDate = DateTimeUtils.getCurrentYearMonthDate();
            try {
                long[] monthTimestampLine = DateTimeUtils.getMonthTimestampLine(currentYearMonthDate[0], currentYearMonthDate[1]);
                int refNumByMonth = iUserServices.retrieveReferenceNumByMonth(id, monthTimestampLine[0], monthTimestampLine[1]);
                this.setAttr("refNumByMonth", refNumByMonth);
            } catch (Exception e) {
                this.logger.info("referenceInfo :时间转化错误 " + e.getMessage());
            }

            this.setAttr("id", userEntity.getId());
            this.setAttr("refNum", userEntity.getRefNum());

            this.setAttr("refBalance", userEntity.getRefBalance());
        }
        this.render("/webapp/widgets/phone_reference_info.html");
    }

    @Override
    public void reference() {
        String refID = this.getPara("refId");
        if (!StringUtils.isEmpty(refID)) {
            this.setAttr("refId", refID);
        }
        this.render("/webapp/widgets/phone_reference_signup.html");
    }

    @Override
    public void referenceSignUp() {
        int refID = this.getParaToInt("refId") == null ? -1 : this.getParaToInt("refId");
        String cellphone = this.getPara("cellphone");
        String smsCode = this.getPara("smsCode");
        String password = this.getPara("password");
        AccountSignUpRequestModel requestModel = new AccountSignUpRequestModel();
        requestModel.setRefId(refID);
        requestModel.setCellphone(cellphone);
        requestModel.setSmsCode(smsCode);
        requestModel.setPassword(password);
        requestModel.setApt(1);
        signUp(requestModel);
    }

    /**
     * 注册逻辑
     *
     * @param requestModel
     */
    private void signUp(AccountSignUpRequestModel requestModel) {
        //参数检测
        if (requestModel == null || !requestModel.isAccountSignUpParamsOk()) {
            this.logger.info("register : 请求参数错误 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            agentExistResponseModel.setMessage("请您输入正确的参数");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        // 验证码检测
        if (!StringUtils.equals(requestModel.getSmsCode(), SMSManager.getSMSCode(requestModel.getCellphone()))) {
            this.logger.info("register : 请求参数中验证码错误 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_CONFLICT);
            agentExistResponseModel.setMessage("您输入的验证码错误");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }
        //重复检测 同一种类型下的同一个电话号码算是重复
        UserEntity existUserEntity = iUserServices.retrieveByCellphone(requestModel.getCellphone());
        if (existUserEntity != null) {
            this.logger.info("register : 重复注册 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_NOT_ACCEPTABLE);
            agentExistResponseModel.setMessage("您输入手机号码已被注册，请直接登录");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setRefId(requestModel.getRefId());
        userEntity.setCellphone(requestModel.getCellphone());
        userEntity.setPassword(PasswordUtils.enPassword(requestModel.getCellphone(), requestModel.getPassword()));
        UserEntity saveUserEntity = iUserServices.create(userEntity);
        if (saveUserEntity == null) {
            this.logger.info("create : 注册失败-服务器内部错误 " + requestModel.toString());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel);
            agentExistResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            agentExistResponseModel.setMessage("系统错误，请重试");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        } else {
            CSEntity csEntity = new CSEntity(saveUserEntity.getCellphone(), TokenUtils.getToken(saveUserEntity.getCellphone(), requestModel.getPassword()), requestModel.getApt());
            TokenContext.addCSEntity(csEntity);
            this.logger.info("create : 注册成功 " + requestModel.toString());
            //创建
            TokenModel sessionModel = new TokenModel(csEntity.getToken(), saveUserEntity.getId(), saveUserEntity.getCellphone());
            AccountSignUpResponseModel agentExistResponseModel = new AccountSignUpResponseModel(requestModel, sessionModel);
            agentExistResponseModel.setCode(ResponseModel.RC_SUCCESS);
            agentExistResponseModel.setMessage("注册成功");
            renderJson(JSON.toJSONString(agentExistResponseModel, new SerializeFilter[]{TokenModel.propertyFilter, TokenModel.valueFilter}, SerializerFeature.WriteMapNullValue));
        }
    }
}
