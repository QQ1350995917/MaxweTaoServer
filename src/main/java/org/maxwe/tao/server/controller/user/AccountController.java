package org.maxwe.tao.server.controller.user;

import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.response.VPageData;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.user.AccountEntity;
import org.maxwe.tao.server.service.user.AccountServices;
import org.maxwe.tao.server.service.user.IAccountServices;
import org.maxwe.tao.server.service.user.UserEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AccountController extends Controller implements IAccountController {
    IAccountServices iAccountServices = new AccountServices();

    @Override
    public void index() {

    }

    @Override
    public void exist() {

    }

    @Override
    public void smsCode() {

    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VAccountEntity requestVAccountEntity = JSON.parseObject(params, VAccountEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVAccountEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAccountEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password", "source")));
            return;
        }

        boolean existAccount = iAccountServices.existAccount(requestVAccountEntity.getIdentity());
        if (existAccount) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVAccountEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password", "source")));
            return;
        }

        requestVAccountEntity.setAccountId(UUID.randomUUID().toString());
        requestVAccountEntity.setUserId(UUID.randomUUID().toString());
        AccountEntity createAccountEntity = iAccountServices.create(requestVAccountEntity);
        if (createAccountEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAccountEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password", "source")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VAccountEntity(createAccountEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password")));
    }

    @Override
    public void login() {
        String params = this.getPara("p");
        VAccountEntity requestVAccountEntity = JSON.parseObject(params, VAccountEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVAccountEntity.checkLoginParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVAccountEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password")));
            return;
        }

        AccountEntity loginAccountEntity = iAccountServices.retrieve(requestVAccountEntity);
        if (loginAccountEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
            iResultSet.setData(requestVAccountEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password")));
            return;
        }

        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(loginAccountEntity.getUserId());
        if (accountEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVAccountEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAccountEntity.class, "identity", "password")));
            return;
        }

        LinkedList<VAccountEntity> vAccountEntities = new LinkedList<>();
        for (AccountEntity accountEntity : accountEntities) {
            vAccountEntities.add(new VAccountEntity(accountEntity));
        }
        HttpSession session = this.getSession(true);
        String sessionId = session.getId();
        VUserEntity sessionVUserEntity = new VUserEntity(sessionId, loginAccountEntity.getUserId(), vAccountEntities);
        session.setAttribute(SessionContext.KEY_USER, sessionVUserEntity);
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VUserEntity(sessionId));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "cs")));
    }

    @Override
    @Before(SessionInterceptor.class)
    public void logout() {
        String params = this.getPara("p");
        VUserEntity requestVUserEntity = JSON.parseObject(params, VUserEntity.class);
        SessionContext.delSession(requestVUserEntity.getCs());
        IResultSet iResultSet = new ResultSet();
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(SessionInterceptor.class)
    public void update() {

    }


    @Override
    @Before(SessionInterceptor.class)
    public void bind() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void phone() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void unBind() {

    }

    @Override
    public void password() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void portrait() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void retrieve() {
        String params = this.getPara("p");
        VUserEntity requestVUserEntity = JSON.parseObject(params, VUserEntity.class);
        IResultSet iResultSet = new ResultSet();
        HttpSession session = SessionContext.getSession(requestVUserEntity.getCs());
        VUserEntity sessionUserEntity = (VUserEntity) session.getAttribute(SessionContext.KEY_USER);
        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(sessionUserEntity.getUserId());
        if (accountEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VAccountEntity> responseVAccountEntity = new LinkedList<>();
        for (AccountEntity accountEntity : accountEntities) {
            responseVAccountEntity.add(new VAccountEntity(accountEntity));
        }
        if (responseVAccountEntity.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVAccountEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VAccountEntity.class, "identity", "nickName", "gender", "address", "portrait", "birthday", "source")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VUserEntity requestVUserEntity = JSON.parseObject(params, VUserEntity.class);
        IResultSet iResultSet = new ResultSet();
        LinkedList<UserEntity> userEntities = iAccountServices.mRetrieveUsers(requestVUserEntity.getCurrentPageIndex(), requestVUserEntity.getSizeInPage());
        if (userEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
        }

        LinkedList<VUserEntity> responseUserEntities = new LinkedList<>();
        for (UserEntity userEntity : userEntities) {
            VUserEntity vUserEntity = new VUserEntity(userEntity);
            LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(userEntity.getUserId());
            LinkedList<VAccountEntity> vAccountEntities = new LinkedList<>();
            for (AccountEntity accountEntity : accountEntities) {
                VAccountEntity result = new VAccountEntity(accountEntity);
                vAccountEntities.add(result);
            }
            vUserEntity.setChildren(vAccountEntities);
            responseUserEntities.add(vUserEntity);
        }
        int userCounter = iAccountServices.mCount();
        if (responseUserEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(
                new VPageData(userCounter % requestVUserEntity.getSizeInPage() == 0 ?
                        userCounter / requestVUserEntity.getSizeInPage() : userCounter / requestVUserEntity.getSizeInPage() + 1,
                        requestVUserEntity.getCurrentPageIndex(), requestVUserEntity.getSizeInPage(), responseUserEntities));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VPageData.class, "totalPageNumber", "currentPageIndex", "sizeInPage", "dataInPage"),
                new SimplePropertyPreFilter(VUserEntity.class, "userId", "status", "children"),
                new SimplePropertyPreFilter(VAccountEntity.class, "accountId", "identity", "nickName", "gender", "address", "portrait", "birthday", "source", "userId")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mQueryUsers() {

    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VUserEntity requestVUserEntity = JSON.parseObject(params, VUserEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVUserEntity.checkMarkParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVUserEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "userId", "status")));
            return;
        }

        UserEntity userEntity = null;
        if (requestVUserEntity.getStatus() == 1) {
            userEntity = iAccountServices.mBlock(requestVUserEntity);
        } else if (requestVUserEntity.getStatus() == 2) {
            userEntity = iAccountServices.mUnBlock(requestVUserEntity);
        }
        if (userEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVUserEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "userId", "status")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VUserEntity(userEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "userId", "status")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieveAccounts() {

    }
}
