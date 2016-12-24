package org.maxwe.tao.server.controller.order;

import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.response.VPageData;
import org.maxwe.tao.server.common.utils.OrderCode;
import org.maxwe.tao.server.controller.cart.VCartEntity;
import org.maxwe.tao.server.controller.manager.VManagerEntity;
import org.maxwe.tao.server.controller.product.VFormatEntity;
import org.maxwe.tao.server.controller.product.VSeriesEntity;
import org.maxwe.tao.server.controller.product.VTypeEntity;
import org.maxwe.tao.server.controller.receiver.VReceiverEntity;
import org.maxwe.tao.server.controller.user.VUserEntity;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.cart.CartEntity;
import org.maxwe.tao.server.service.cart.CartServices;
import org.maxwe.tao.server.service.cart.ICartServices;
import org.maxwe.tao.server.service.order.IOrderServices;
import org.maxwe.tao.server.service.order.OrderEntity;
import org.maxwe.tao.server.service.order.OrderServices;
import org.maxwe.tao.server.service.product.*;
import org.maxwe.tao.server.service.receiver.IReceiverService;
import org.maxwe.tao.server.service.receiver.ReceiverEntity;
import org.maxwe.tao.server.service.receiver.ReceiverServices;
import org.maxwe.tao.server.service.user.AccountEntity;
import org.maxwe.tao.server.service.user.AccountServices;
import org.maxwe.tao.server.service.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderController extends Controller implements IOrderController {
    private IAccountServices iAccountServices = new AccountServices();
    private IOrderServices iOrderServices = new OrderServices();
    private IReceiverService iReceiverService = new ReceiverServices();
    private ICartServices iCartServices = new CartServices();
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();

    @Override
    public void index() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void create() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();

        requestVOrderEntity.setStatus(1);
        String orderId = UUID.randomUUID().toString();
        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        requestVOrderEntity.setAccountId(vUserEntity.getChildren().get(0).getAccountId());
        requestVOrderEntity.setOrderId(orderId);
        requestVOrderEntity.setCode(OrderCode.gen());
        OrderEntity createOrderEntity = iOrderServices.create(requestVOrderEntity);
        if (createOrderEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }
        boolean attachToOrder = iCartServices.attachToOrder(createOrderEntity, requestVOrderEntity.getProductIds());
        if (!attachToOrder) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VOrderEntity(createOrderEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
    }

    @Override
    public void createAnonymous() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        requestVOrderEntity.setStatus(1);
        /**
         * 保存匿名订单的收货人信息
         */
        VReceiverEntity receiverEntity = requestVOrderEntity.getReceiver();
        receiverEntity.setReceiverId(UUID.randomUUID().toString());
        ReceiverEntity createReceiver = iReceiverService.create(null, receiverEntity);
        if (createReceiver == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }

        String orderId = UUID.randomUUID().toString();
        requestVOrderEntity.setOrderId(orderId);
        requestVOrderEntity.setReceiverId(createReceiver.getReceiverId());
        /**
         * 保存匿名订单的订单信息
         */
        requestVOrderEntity.setCode(OrderCode.gen());
        OrderEntity createOrderEntity = iOrderServices.create(requestVOrderEntity);
        if (createOrderEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }
        CartEntity cartEntity = new CartEntity();
        String mappingId = UUID.randomUUID().toString();
        cartEntity.setMappingId(mappingId);
        cartEntity.setFormatId(requestVOrderEntity.getProductIds()[0]);
        cartEntity.setAmount(1);
        cartEntity.setPricing(0);
        cartEntity.setOrderId(createOrderEntity.getOrderId());
        cartEntity.setStatus(2);
        CartEntity resultCartEntity = iCartServices.create(cartEntity);
        if (resultCartEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VOrderEntity(createOrderEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
    }

    @Before(SessionInterceptor.class)
    @Override
    public void expressed() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVOrderEntity.checkOrderIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        OrderEntity result = iOrderServices.expressed(vUserEntity.getChildren(), requestVOrderEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Before(SessionInterceptor.class)
    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        LinkedList<OrderEntity> orderEntities = iOrderServices.retrievesByAccounts(vUserEntity.getChildren(), 0, 0, 0);
        if (orderEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VOrderEntity> responseVOrderEntities = new LinkedList<>();
        for (int index = 0; index < orderEntities.size(); index++) {
            OrderEntity orderEntity = orderEntities.get(index);
            ReceiverEntity receiverEntity = iReceiverService.retrieveById(orderEntity.getReceiverId());
            VOrderEntity vOrderEntity = new VOrderEntity(orderEntity);
            vOrderEntity.setReceiver(new VReceiverEntity(receiverEntity));
            LinkedList<VCartEntity> vCartEntities = new LinkedList<>();
            LinkedList<CartEntity> cartEntities = iCartServices.retrievesByOrderId(vUserEntity.getChildren(), orderEntity.getOrderId());
            for (CartEntity cartEntity : cartEntities) {
                VCartEntity vCartEntity = new VCartEntity(cartEntity);
                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                vTypeEntity.setParent(vSeriesEntity);
                vFormatEntity.setParent(vTypeEntity);
                vCartEntity.setFormatEntity(vFormatEntity);
                vCartEntities.add(vCartEntity);
            }
            vOrderEntity.setCartEntities(vCartEntities);
            responseVOrderEntities.add(vOrderEntity);
        }

        if (responseVOrderEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVOrderEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VOrderEntity.class, "orderId", "accountId", "code", "senderName", "senderPhone", "receiverId", "cost", "postage", "status", "expressLabel", "expressNumber", "createTime", "receiver", "cartEntities"),
                new SimplePropertyPreFilter(VReceiverEntity.class, "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append"),
                new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "accountId", "amount", "pricing", "formatEntity"),
                new SimplePropertyPreFilter(VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }


    @Override
    public void query() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVOrderEntity.checkOrderIdParams()) {
//            QueryPageEntity queryPageEntity = new QueryPageEntity();
            LinkedList<OrderEntity> query = iOrderServices.query(requestVOrderEntity.getOrderId(), 0, 0);
            OrderEntity orderEntity = query.get(0);
            ReceiverEntity receiverEntity = iReceiverService.retrieveById(orderEntity.getReceiverId());
            VOrderEntity vOrderEntity = new VOrderEntity(orderEntity);
            vOrderEntity.setReceiver(new VReceiverEntity(receiverEntity));
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(vOrderEntity);//TODO 裁剪参数
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrievesByUser() {
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

        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(requestVManagerEntity.getUser().getUserId());
        if (accountEntities == null || accountEntities.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrievesByUser(accountEntities, 0, requestVManagerEntity.getCurrentPageIndex(), requestVManagerEntity.getSizeInPage());
        if (orderEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<VOrderEntity> responseVOrderEntities = new LinkedList<>();
        for (int index = 0; index < orderEntities.size(); index++) {
            OrderEntity orderEntity = orderEntities.get(index);
            VOrderEntity vOrderEntity = new VOrderEntity(orderEntity);
            ReceiverEntity receiverEntity = iReceiverService.retrieveById(orderEntity.getReceiverId());
            vOrderEntity.setReceiver(new VReceiverEntity(receiverEntity));
            LinkedList<VCartEntity> vCartEntities = new LinkedList<>();
            LinkedList<CartEntity> cartEntities = iCartServices.mRetrievesByOrderId(accountEntities, orderEntity.getOrderId());
            for (CartEntity cartEntity : cartEntities) {
                VCartEntity vCartEntity = new VCartEntity(cartEntity);
                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                vTypeEntity.setParent(vSeriesEntity);
                vFormatEntity.setParent(vTypeEntity);
                vCartEntity.setFormatEntity(vFormatEntity);
                vCartEntities.add(vCartEntity);
            }
            vOrderEntity.setCartEntities(vCartEntities);
            responseVOrderEntities.add(vOrderEntity);
        }
        int orderCounter = iOrderServices.mCountByUser(accountEntities, 0);
        if (responseVOrderEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(new VPageData((orderCounter % requestVManagerEntity.getSizeInPage() == 0 ?
                orderCounter / requestVManagerEntity.getSizeInPage() : orderCounter / requestVManagerEntity.getSizeInPage() + 1),
                requestVManagerEntity.getCurrentPageIndex(), requestVManagerEntity.getSizeInPage(), responseVOrderEntities));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{//TODO 裁剪参数
                new SimplePropertyPreFilter(VPageData.class, "totalPageNumber", "currentPageIndex", "sizeInPage", "dataInPage"),
                new SimplePropertyPreFilter(VOrderEntity.class, "orderId", "accountId", "code", "senderName", "senderPhone", "receiverId", "cost", "postage", "status", "expressLabel", "expressNumber", "createTime", "receiver", "cartEntities"),
                new SimplePropertyPreFilter(VReceiverEntity.class, "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append"),
                new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "accountId", "amount", "pricing", "formatEntity"),
                new SimplePropertyPreFilter(VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVOrderEntity.checkStatusParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "status")));
            return;
        }

        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrieves(requestVOrderEntity.getStatus(), requestVOrderEntity.getCurrentPageIndex(), requestVOrderEntity.getSizeInPage());
        if (orderEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "status")));
            return;
        }

        LinkedList<VOrderEntity> responseVOrderEntities = new LinkedList<>();
        for (OrderEntity orderEntity : orderEntities) {
            ReceiverEntity receiverEntity = iReceiverService.retrieveById(orderEntity.getReceiverId());
            VOrderEntity result = new VOrderEntity(orderEntity);
            result.setReceiver(new VReceiverEntity(receiverEntity));
            LinkedList<CartEntity> cartEntities = iCartServices.mRetrievesByOrderId(orderEntity.getOrderId());
            LinkedList<VCartEntity> vCartEntities = new LinkedList<>();
            for (CartEntity cartEntity : cartEntities) {
                VCartEntity vCartEntity = new VCartEntity(cartEntity);
                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                vTypeEntity.setParent(vSeriesEntity);
                vFormatEntity.setParent(vTypeEntity);
                vCartEntity.setFormatEntity(vFormatEntity);
                vCartEntities.add(vCartEntity);
            }
            result.setCartEntities(vCartEntities);
            responseVOrderEntities.add(result);
        }
        int orderCounter = iOrderServices.mCount(requestVOrderEntity.getStatus());
        if (responseVOrderEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(new VPageData(orderCounter % requestVOrderEntity.getSizeInPage() == 0 ?
                orderCounter / requestVOrderEntity.getSizeInPage() : orderCounter / requestVOrderEntity.getSizeInPage() + 1,
                requestVOrderEntity.getCurrentPageIndex(), requestVOrderEntity.getSizeInPage(), responseVOrderEntities));
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VPageData.class, "totalPageNumber", "currentPageIndex", "sizeInPage", "dataInPage"),
                new SimplePropertyPreFilter(VOrderEntity.class, "orderId", "accountId", "code", "senderName", "senderPhone", "receiverId", "cost", "postage", "status", "expressLabel", "expressNumber", "createTime", "receiver", "cartEntities"),
                new SimplePropertyPreFilter(VReceiverEntity.class, "name", "phone0", "phone1", "province", "city", "county", "town", "village", "append"),
                new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "accountId", "amount", "pricing", "formatEntity"),
                new SimplePropertyPreFilter(VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mExpressing() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVOrderEntity.checkExpressParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "expressLabel", "expressNumber")));
            return;
        }
        OrderEntity result = iOrderServices.mExpressing(requestVOrderEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "expressLabel", "expressNumber")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVOrderEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "expressLabel", "expressNumber")));
    }

    @Override
    @Before({SessionInterceptor.class, MenuInterceptor.class})
    public void mQuery() {

    }
}
