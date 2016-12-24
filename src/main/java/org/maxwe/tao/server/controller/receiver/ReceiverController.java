package org.maxwe.tao.server.controller.receiver;

import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.manager.VManagerEntity;
import org.maxwe.tao.server.controller.user.VUserEntity;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.receiver.IReceiverService;
import org.maxwe.tao.server.service.receiver.ReceiverEntity;
import org.maxwe.tao.server.service.receiver.ReceiverServices;
import org.maxwe.tao.server.service.user.AccountEntity;
import org.maxwe.tao.server.service.user.AccountServices;
import org.maxwe.tao.server.service.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverController extends Controller implements IReceiverController {

    private IAccountServices iAccountServices = new AccountServices();
    private IReceiverService iReceiverService = new ReceiverServices();

    @Override
    public void index() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void retrieves() {
        String params = this.getPara("p");
        VUserEntity requestVUserEntity = JSON.parseObject(params, VUserEntity.class);
        IResultSet iResultSet = new ResultSet();
        VUserEntity sessionUserEntity = SessionContext.getSessionUser(requestVUserEntity.getCs());
        LinkedList<ReceiverEntity> receiverEntities = iReceiverService.retrieves(sessionUserEntity.getChildren());
        if (receiverEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VReceiverEntity> responseVReceiverEntities = new LinkedList<>();
        for (ReceiverEntity receiverEntity : receiverEntities) {
            responseVReceiverEntities.add(new VReceiverEntity(receiverEntity));
        }
        if (responseVReceiverEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVReceiverEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet,
                new SimplePropertyPreFilter(
                        VReceiverEntity.class, "receiverId", "name", "phone0", "phone1", "province", "city", "county", "town",
                        "village", "append", "status", "accountId")));
    }

    @Override
    @Before(SessionInterceptor.class)
    public void create() {
        String params = this.getPara("p");
        VReceiverEntity requestVReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVReceiverEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet,
                    new SimplePropertyPreFilter(VReceiverEntity.class,
                            "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append")));
            return;
        }

        VUserEntity sessionUser = SessionContext.getSessionUser(requestVReceiverEntity.getCs());
        String receiverId = UUID.randomUUID().toString();
        requestVReceiverEntity.setStatus(2);
        requestVReceiverEntity.setReceiverId(receiverId);
        ReceiverEntity receiverEntity = iReceiverService.create(sessionUser.getChildren().get(0), requestVReceiverEntity);
        if (receiverEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet,
                    new SimplePropertyPreFilter(VReceiverEntity.class,
                            "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VReceiverEntity(receiverEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId",
                "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append", "status")));
        return;
    }

    @Override
    @Before(SessionInterceptor.class)
    public void update() {
        String params = this.getPara("p");
        VReceiverEntity requestVReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVReceiverEntity.checkUpdateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId",
                    "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append", "status")));
            return;
        }

        VUserEntity sessionUser = SessionContext.getSessionUser(requestVReceiverEntity.getCs());
        ReceiverEntity receiverEntity = iReceiverService.updateById(sessionUser.getChildren(), requestVReceiverEntity);
        if (receiverEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId",
                    "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append", "status")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VReceiverEntity(receiverEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId",
                "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append", "status")));
        return;
    }

    @Override
    @Before(SessionInterceptor.class)
    public void delete() {
        String params = this.getPara("p");
        VReceiverEntity requestVReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVReceiverEntity.checkReceiverIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId")));
            return;
        }

        VUserEntity sessionUser = SessionContext.getSessionUser(requestVReceiverEntity.getCs());
        ReceiverEntity receiverEntity = iReceiverService.deleteById(sessionUser.getChildren(), requestVReceiverEntity.getReceiverId());
        if (receiverEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVReceiverEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId")));
    }

    @Override
    @Before(SessionInterceptor.class)
    public void king() {
        String params = this.getPara("p");
        VReceiverEntity requestVReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVReceiverEntity.checkReceiverIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId")));
            return;
        }

        VUserEntity sessionUser = SessionContext.getSessionUser(requestVReceiverEntity.getCs());
        ReceiverEntity receiverEntity = iReceiverService.kingReceiverInUser(sessionUser.getChildren(), requestVReceiverEntity);
        if (receiverEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVReceiverEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVReceiverEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VReceiverEntity.class, "receiverId")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieveByUser() {
        String params = this.getPara("p");
        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVManagerEntity.checkUserParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<AccountEntity> accountEntities = iAccountServices.mRetrieveByUserId(requestVManagerEntity.getUser().getUserId());
        if (accountEntities == null || accountEntities.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<ReceiverEntity> receiverEntities = iReceiverService.mRetrieves(accountEntities);
        if (receiverEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<VReceiverEntity> responseVReceiverEntities = new LinkedList<>();
        for (ReceiverEntity receiverEntity : receiverEntities) {
            responseVReceiverEntities.add(new VReceiverEntity(receiverEntity));
        }
        if (responseVReceiverEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVReceiverEntities);
        renderJson(JSON.toJSONString(iResultSet,
                new SimplePropertyPreFilter(
                        VReceiverEntity.class, "receiverId", "name", "phone0", "phone1", "province", "city", "county", "town",
                        "village", "append", "status", "accountId")));
    }
}
