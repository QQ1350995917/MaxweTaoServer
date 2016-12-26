package org.maxwe.tao.server.controller.manager;

import org.maxwe.tao.server.common.cache.SessionContext;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.controller.menu.VMenuEntity;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.MenuInterceptor;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.manager.IManagerServices;
import org.maxwe.tao.server.service.manager.ManagerEntity;
import org.maxwe.tao.server.service.manager.ManagerServices;
import org.maxwe.tao.server.service.menu.IMenuServices;
import org.maxwe.tao.server.service.menu.MenuEntity;
import org.maxwe.tao.server.service.menu.MenuServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-07-30 10:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员业务逻辑流程控制器
 */
public class ManagerController extends Controller implements IManagerController {
//    private IManagerServices iManagerServices = new ManagerServices();
//    private IMenuServices iMenuServices = new MenuServices();
//
//    @Override
//    public void index() {
//
//    }
//
//    @Override
//    public void mLogin() {
//        String params = this.getPara("p");//获取参数
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);//参数转换
//        IResultSet iResultSet = new ResultSet();
//        if (requestVManagerEntity == null || !requestVManagerEntity.checkLoginParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "loginName", "password")));
//            return;
//        }
//
//        ManagerEntity managerEntity = iManagerServices.mLogin(requestVManagerEntity);
//        if (managerEntity == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_BAD.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_FAIL);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "loginName", "password")));
//            return;
//        }
//
//        LinkedList<String> actionKeys = new LinkedList<>();
//        LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(managerEntity);
//        for (MenuEntity menuEntity : menuEntities) {
//            VMenuEntity vMenuEntity = new VMenuEntity(menuEntity);
//            actionKeys.addAll(Arrays.asList(vMenuEntity.getActionKey().split(",")));
//        }
//        HttpSession session = this.getSession(true);
//        String sessionId = session.getId();
//        VManagerEntity sessionVManagerEntity = new VManagerEntity();
//        sessionVManagerEntity.setCs(sessionId);
//        sessionVManagerEntity.setManagerId(managerEntity.getManagerId());
//        sessionVManagerEntity.setActionKeys(actionKeys);
//        session.setAttribute(SessionContext.KEY_USER, sessionVManagerEntity);
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(new VManagerEntity(sessionId));
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_LOGIN_SUCCESS);
//        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "cs")));
//    }
//
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class})
//    public void mSession() {
//        String params = this.getPara("p");
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(requestVManagerEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "cs")));
//    }
//
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class})
//    public void mExit() {
//        String params = this.getPara("p");
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        SessionContext.delSession(requestVManagerEntity.getCs());
//        IResultSet iResultSet = new ResultSet();
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet));
//    }
//
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class})
//    public void mRetrieve() {//由于拦截器已经保证了session的存在，故此处无需额外的session验证
//        String params = this.getPara("p");//获取参数
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);//参数转换
//        IResultSet iResultSet = new ResultSet();
//        VManagerEntity sessionManager = SessionContext.getSessionManager(requestVManagerEntity.getCs());
//        ManagerEntity managerEntity = iManagerServices.mRetrieveById(sessionManager);
//        LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(managerEntity);//TODO 异步线程禁用或删除用户的问题，这个细节尚未处理
//        LinkedList<VMenuEntity> vMenuEntities = new LinkedList<>();
//        for (MenuEntity menuEntity : menuEntities) {
//            vMenuEntities.add(new VMenuEntity(menuEntity));
//        }
//        VManagerEntity responseVManagerEntity = new VManagerEntity(managerEntity);
//        responseVManagerEntity.setCs(requestVManagerEntity.getCs());
//        responseVManagerEntity.setMenus(vMenuEntities);
//
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(responseVManagerEntity);
//        renderJson(JSON.toJSONString(iResultSet,
//                new SerializeFilter[]{
//                        new SimplePropertyPreFilter(VManagerEntity.class, "cs", "loginName", "username", "password", "menus"),
//                        new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//                }
//        ));
//    }
//
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class})
//    public void mUpdate() {//由于拦截器已经保证了session的存在，故此处无需额外的session验证
//        String params = this.getPara("p");
//        IResultSet iResultSet = new ResultSet();
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        VManagerEntity sessionManager = SessionContext.getSessionManager(requestVManagerEntity.getCs());
//        requestVManagerEntity.setManagerId(sessionManager.getManagerId());
//        if (!requestVManagerEntity.checkUpdateParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "cs", "username", "password")));
//            return;
//        }
//        ManagerEntity managerEntity = iManagerServices.mUpdate(requestVManagerEntity);// TODO 数据返回为空，可能是由于管理员数据被禁用，这个细节没有处理
//        if (managerEntity == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "cs", "username", "password")));
//        } else {
//            VManagerEntity responseVManagerEntity = new VManagerEntity(managerEntity);
//            responseVManagerEntity.setCs(requestVManagerEntity.getCs());
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//            iResultSet.setData(responseVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "cs", "username", "password")));
//        }
//    }
//
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class,})
//    public void MRetrieves() {
//        LinkedList<VManagerEntity> responseVManagerEntities = new LinkedList<>();
//        LinkedList<ManagerEntity> managerEntities = iManagerServices.MRetrieves();
//        for (ManagerEntity managerEntity : managerEntities) {
//            VManagerEntity vManagerEntity = new VManagerEntity(managerEntity);
//            LinkedList<VMenuEntity> vMenuEntities = new LinkedList<>();
//            LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(managerEntity);
//            for (MenuEntity menuEntity : menuEntities) {
//                vMenuEntities.add(new VMenuEntity(menuEntity));
//            }
//            vManagerEntity.setMenus(vMenuEntities);
//            responseVManagerEntities.add(vManagerEntity);
//        }
//        IResultSet iResultSet = new ResultSet();
//        if (responseVManagerEntities.size() > 0) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        } else {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
//        }
//        iResultSet.setData(responseVManagerEntities);
//        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "loginName", "username", "password", "status", "menus"),
//                new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label", "category")
//        }));
//    }
//
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class,})
//    public void MCreate() {
//        String params = this.getPara("p");
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVManagerEntity.checkMCreateParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                    new SimplePropertyPreFilter(VManagerEntity.class, "loginName", "username", "password", "menus"),
//                    new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//            }));
//            return;
//        }
//
//        if (iManagerServices.MExist(requestVManagerEntity)) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                    new SimplePropertyPreFilter(VManagerEntity.class, "loginName", "username", "password", "menus"),
//                    new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//            }));
//            return;
//        }
//
//        requestVManagerEntity.setManagerId(UUID.randomUUID().toString());
//        requestVManagerEntity.setLevel(1);
//        requestVManagerEntity.setStatus(1);
//        ManagerEntity managerEntity = iManagerServices.MCreate(requestVManagerEntity, requestVManagerEntity.getMenus());
//        if (managerEntity == null) {
//            requestVManagerEntity.setManagerId(null);
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                    new SimplePropertyPreFilter(VManagerEntity.class, "loginName", "username", "password", "menus"),
//                    new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//            }));
//            return;
//        }
//        VManagerEntity responseVManagerEntity = new VManagerEntity(managerEntity);
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(responseVManagerEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "loginName", "username", "password", "menus","status"),
//                new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//        }));
//    }
//
//    /**
//     * TODO 尚未按照流程设计图修改内存状态，修改了内存状态才能使修改立即生效
//     */
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class,})
//    public void MUpdate() {
//        String params = this.getPara("p");
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVManagerEntity.checkMUpdateParams()) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                    new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "loginName", "username", "password", "menus"),
//                    new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//            }));
//            return;
//        }
//
//        ManagerEntity managerEntity = iManagerServices.MUpdate(requestVManagerEntity, requestVManagerEntity.getMenus());
//        if (managerEntity == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                    new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "loginName", "username", "password", "menus"),
//                    new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//            }));
//            return;
//        }
//
//        VManagerEntity responseVManagerEntity = new VManagerEntity(managerEntity);
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(responseVManagerEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
//                new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "loginName", "username", "password", "menus"),
//                new SimplePropertyPreFilter(VMenuEntity.class, "menuId", "label")
//        }));
//    }
//
//    /**
//     * TODO 尚未按照流程设计图修改内存状态，修改了内存状态才能使修改立即生效
//     */
//    @Override
//    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class,})
//    public void MMark() {
//        String params = this.getPara("p");
//        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
//        IResultSet iResultSet = new ResultSet();
//        if (!requestVManagerEntity.checkMMarkParams()){
//            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "status")}));
//            return;
//        }
//        ManagerEntity managerEntity = null;
//        if (requestVManagerEntity.getStatus() == 1) {
//            managerEntity = iManagerServices.MBlock(requestVManagerEntity);
//        } else if (requestVManagerEntity.getStatus() == 2) {
//            managerEntity = iManagerServices.MUnBlock(requestVManagerEntity);
//        } else if (requestVManagerEntity.getStatus() == -1) {
//            managerEntity = iManagerServices.MDelete(requestVManagerEntity);
//        }
//
//        if (managerEntity == null) {
//            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
//            iResultSet.setData(requestVManagerEntity);
//            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
//            renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "status")}));
//            return;
//        }
//
//        VManagerEntity responseVManagerEntity = new VManagerEntity(managerEntity);
//        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
//        iResultSet.setData(responseVManagerEntity);
//        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
//        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{new SimplePropertyPreFilter(VManagerEntity.class, "managerId", "status")}));
//    }
}
