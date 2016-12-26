package org.maxwe.tao.server.controller.cart;

import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartController extends Controller implements ICartController {

//    private ICartServices iCartServices = new CartServices();
//    private ISeriesServices iSeriesServices = new SeriesServices();
//    private ITypeServices iTypeServices = new TypeServices();
//    private IFormatServices iFormatServices = new FormatServices();
//    private IAccountServices iAccountServices = new AccountServices();
//
//    @Override
//    public void index() {
//
//    }
//
//    @Override
//    @Before(SessionInterceptor.class)
//    public void create() {
//        String params = this.getPara("p");
//        VCartEntity requestVCartEntity = JSON.parseObject(params, VCartEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVCartEntity.checkCreateParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "formatId", "amount")));
//            return;
//        }
//
//        HttpSession session = SessionContext.getSession(requestVCartEntity.getCs());
//        VUserEntity vUserEntity = (VUserEntity) session.getAttribute(SessionContext.KEY_USER);
//        requestVCartEntity.setMappingId(UUID.randomUUID().toString());
//        requestVCartEntity.setAccountId(vUserEntity.getChildren().get(0).getAccountId());//TODO 这里存在问题，应该查询所有账户下是否有这个产品的ID,如果有应该设置添加的产品到旧有的账户中去
//        CartEntity cartEntityExist = iCartServices.exist(requestVCartEntity);
//        if (cartEntityExist == null) {
//            CartEntity createResult = iCartServices.create(requestVCartEntity);
//            if (createResult == null) {
//                iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//                iResultSet.setData(requestVCartEntity);
//                iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//                renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "formatId", "amount")));
//                return;
//            }
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "formatId", "amount")));
//            return;
//        }
//
//        int amount = requestVCartEntity.getAmount() + cartEntityExist.getAmount();
//        requestVCartEntity.setMappingId(cartEntityExist.getMappingId());
//        requestVCartEntity.setAmount(amount);
//        CartEntity updateResult = iCartServices.updateAmount(requestVCartEntity);
//        if (updateResult == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "formatId", "amount")));
//            return;
//        }
//
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(new VCartEntity(updateResult));
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "amount")));
//    }
//
//    @Override
//    @Before(SessionInterceptor.class)
//    public void update() {
//        String params = this.getPara("p");
//        VCartEntity requestVCartEntity = JSON.parseObject(params, VCartEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVCartEntity.checkUpdateParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "amount")));
//            return;
//        }
//
//        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(SessionContext.getSession(requestVCartEntity.getCs()).toString());
//        if (accountEntities == null || accountEntities.size() == 0) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "amount")));
//            return;
//        }
//
//        requestVCartEntity.setAccountId(accountEntities.get(0).getAccountId());//TODO 这里存在问题，应该查询所有账户下是否有这个产品的ID,如果有应该设置添加的产品到旧有的账户中去
//        CartEntity updateResult = iCartServices.updateAmount(requestVCartEntity);
//        if (updateResult == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "amount")));
//            return;
//        }
//
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(new VCartEntity(updateResult));
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId", "amount")));
//    }
//
//    @Override
//    @Before(SessionInterceptor.class)
//    public void retrieves() {
//        String params = this.getPara("p");
//        VCartEntity requestVCartEntity = JSON.parseObject(params, VCartEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        LinkedList<CartEntity> cartEntities;
//        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVCartEntity.getCs()).getAttribute(SessionContext.KEY_USER);
//        if (requestVCartEntity.getProductIds() != null) {
//            cartEntities = iCartServices.retrievesByIds(vUserEntity.getChildren(), requestVCartEntity.getProductIds());
//        } else {
//            cartEntities = iCartServices.retrievesByAccounts(vUserEntity.getChildren(), requestVCartEntity.getCurrentPageIndex(), requestVCartEntity.getSizeInPage());
//        }
//        if (cartEntities == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId")));
//            return;
//        }
//        int counter = iCartServices.countByAccounts(vUserEntity.getChildren());
//        LinkedList<VCartEntity> responseVCartEntities = new LinkedList<>();
//        for (CartEntity cartEntity : cartEntities) {// TODO 所有涉及到正反向产品树结构查询的都应该着重考虑产品下架的可能
//            FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
//            TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
//            SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
//            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
//            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
//            VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
//            vTypeEntity.setParent(vSeriesEntity);
//            vFormatEntity.setParent(vTypeEntity);
//            VCartEntity responseVCartEntity = new VCartEntity(cartEntity);
//            responseVCartEntity.setFormatEntity(vFormatEntity);
//            responseVCartEntities.add(responseVCartEntity);
//        }
//        if (responseVCartEntities.size() == 0) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
//        } else {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        }
//        iResultSet.setData(new VPageData(counter % requestVCartEntity.getSizeInPage() == 0 ?
//                counter / requestVCartEntity.getSizeInPage() : counter % requestVCartEntity.getSizeInPage() + 1,
//                requestVCartEntity.getCurrentPageIndex(), requestVCartEntity.getSizeInPage(), responseVCartEntities));
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet));//TODO 调整返回参数
//    }
//
//    @Override
//    @Before(SessionInterceptor.class)
//    public void delete() {
//        String params = this.getPara("p");
//        VCartEntity requestVCartEntity = JSON.parseObject(params, VCartEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVCartEntity.checkDeleteParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId")));
//            return;
//        }
//
//        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVCartEntity.getCs()).getAttribute(SessionContext.KEY_USER);
//        CartEntity delete = iCartServices.deleteById(vUserEntity.getChildren(), requestVCartEntity);
//        if (delete == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVCartEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId")));
//            return;
//        }
//
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(requestVCartEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VCartEntity.class, "mappingId")));
//    }
//
//    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
//    @Override
//    public void mRetrieve() {
//        String params = this.getPara("p");
//        VUserEntity requestVUserEntity = JSON.parseObject(params, VUserEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVUserEntity.checkUserIdParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVUserEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "userId")));
//            return;
//        }
//
//        LinkedList<AccountEntity> accountEntities = iAccountServices.mRetrieveByUserId(requestVUserEntity.getUserId());
//        if (accountEntities == null || accountEntities.size() == 0) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "userId")));
//            return;
//        }
//
//        LinkedList<CartEntity> cartEntities = iCartServices.retrievesByAccounts(accountEntities, requestVUserEntity.getCurrentPageIndex(), requestVUserEntity.getSizeInPage());
//        if (cartEntities == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VUserEntity.class, "userId")));
//            return;
//        }
//
//        LinkedList<VCartEntity> responseVCartEntities = new LinkedList<>();
//        for (CartEntity cartEntity : cartEntities) {
//            FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
//            TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
//            SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
//            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
//            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
//            VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
//            vTypeEntity.setParent(vSeriesEntity);
//            vFormatEntity.setParent(vTypeEntity);
//            VCartEntity responseVCartEntity = new VCartEntity(cartEntity);
//            responseVCartEntity.setFormatEntity(vFormatEntity);
//            responseVCartEntities.add(responseVCartEntity);
//        }
//        int counter = iCartServices.countByAccounts(accountEntities);
//        if (responseVCartEntities.size() == 0) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
//        } else {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        }
//        iResultSet.setData(new VPageData(counter % requestVUserEntity.getSizeInPage() == 0 ?
//                counter / requestVUserEntity.getSizeInPage() : counter / requestVUserEntity.getSizeInPage() + 1,
//                requestVUserEntity.getCurrentPageIndex(), requestVUserEntity.getSizeInPage(), responseVCartEntities));
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet));//TODO 调整返回参数
//    }
}
