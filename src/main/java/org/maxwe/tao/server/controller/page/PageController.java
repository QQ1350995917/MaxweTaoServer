package org.maxwe.tao.server.controller.page;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.maxwe.tao.server.controller.account.manager.ManagerController;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;
import org.maxwe.tao.server.service.manager.ManagerEntity;
import org.maxwe.tao.server.service.manager.ManagerServices;
import org.maxwe.tao.server.service.menu.MenuEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-08-19 16:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 前端页面接口，所有有关页面的跳转均由该类实现的接口分发
 */
public class PageController extends Controller implements IPageController {

    @Override
    public void index() {
        this.render("/webapp/widgets/index.html");
    }

    public static void main() {

    }

    @Override
    public void addm() {
        this.setAttr("managerMenus", ManagerController.managerMenus);
        this.setAttr("workMenus", ManagerController.workMenus);
        this.setAttr("publicKey", this.getSessionAttr("publicKey"));
        this.render("/webapp/widgets/managerCreate.view.html");
    }

    @Override
    public void password() {
        ManagerEntity manager = this.getSessionAttr("manager");
        this.setAttr("manager", manager);
        this.setAttr("publicKey", this.getSessionAttr("publicKey"));
        this.render("/webapp/widgets/managerPassword.view.html");
    }

    @Override
    public void money() {

    }


    @Override
    public void grant() {
        String loginName = this.getPara("loginName");
        if (StringUtils.isEmpty(loginName)) {
            renderError(400);
            return;
        }
        ManagerEntity managerEntity = new ManagerServices().retrieveByLoginName(loginName);
        if (managerEntity == null) {
            renderError(500);
            return;
        }

        String access = managerEntity.getAccess();
        List<String> strings = new LinkedList<>();
        if (access != null) {
            strings = JSON.parseObject(access, List.class);
        }

        for (MenuEntity menuEntity : ManagerController.managerMenus) {
            if (strings.contains(menuEntity.getId())) {
                menuEntity.setGranted(true);
            } else {
                menuEntity.setGranted(false);
            }
        }

        for (MenuEntity menuEntity : ManagerController.workMenus) {
            if (strings.contains(menuEntity.getId())) {
                menuEntity.setGranted(true);
            } else {
                menuEntity.setGranted(false);
            }
        }

        this.setAttr("managerMenus", ManagerController.managerMenus);
        this.setAttr("workMenus", ManagerController.workMenus);
        this.setAttr("loginName", loginName);
        this.render("/webapp/widgets/managerGrant.view.html");
    }

    @Override
    public void level() {

    }

    @Override
    public void users() {
        this.render("/webapp/widgets/systemUserList.html");
    }

    @Override
    public void ml() {
        this.render("/webapp/widgets/login.html");
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class})
    public void frame() {
//        // TODO 把JS文件进行拆分 根据管理员的权限进行动态分配JS文件
        this.render("/webapp/widgets/frame.html");
    }

    public void api() {
        this.render("/webapp/widgets/tao.html");
    }

    @Override
    public void addGoodsView() {
        this.render("/webapp/widgets/businessAddData.view.html");
    }
}
