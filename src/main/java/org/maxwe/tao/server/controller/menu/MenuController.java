package org.maxwe.tao.server.controller.menu;

import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-30 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */

public class MenuController extends Controller implements IMenuController {
//    private IMenuServices iMenuServices = new MenuServices();
//    @Override
//    public void index() {
//
//    }
//
//    @Override
//    @Before({SessionInterceptor.class})
//    public void mRetrieves() {
//        String params = this.getPara("p");
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        VManagerEntity sessionManager = SessionContext.getSessionManager(requestVManagerEntity.getCs());
//        LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(sessionManager);
//        if (menuEntities == null){
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet));
//            return;
//        }
//
//        LinkedList<VMenuEntity> responseVMenuEntities = new LinkedList<>();
//        for (MenuEntity menuEntity:menuEntities){
//            responseVMenuEntities.add(new VMenuEntity(menuEntity));
//        }
//        if (responseVMenuEntities.size() == 0) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
//        } else {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        }
//        iResultSet.setData(responseVMenuEntities);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet,new SimplePropertyPreFilter(VMenuEntity.class,"menuId","label","flag","category")));
//    }
}
