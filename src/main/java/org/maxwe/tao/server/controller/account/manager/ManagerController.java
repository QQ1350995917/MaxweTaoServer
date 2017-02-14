package org.maxwe.tao.server.controller.account.manager;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import org.maxwe.tao.server.common.utils.RSAUtils;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.manager.IManagerServices;
import org.maxwe.tao.server.service.manager.ManagerEntity;
import org.maxwe.tao.server.service.manager.ManagerServices;
import org.maxwe.tao.server.service.menu.MenuEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2017-02-09 21:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ManagerController extends Controller implements IManagerController {
    private IManagerServices iManagerServices = new ManagerServices();
    private IAgentServices iAgentServices = new AgentServices();
    public static final List<MenuEntity> superMenus = new LinkedList<>();
    public static final List<MenuEntity> managerMenus = new LinkedList<>();
    public static final List<MenuEntity> workMenus = new LinkedList<>();

    static {
        superMenus.add(new MenuEntity("100", "系统总览", "agents", 0, 2, 2, ""));
        superMenus.add(new MenuEntity("101", "数据报表", "agents", 1, 2, 2, ""));
        superMenus.add(new MenuEntity("102", "码量管理", "agents", 2, 2, 2, ""));
        superMenus.add(new MenuEntity("103", "导出数据", "agents", 3, 2, 2, ""));

        managerMenus.add(new MenuEntity("200", "账号管理", "accountManager", 0, 2, 2, "manager/block,manager/unBlock,manager/delete,manager/grant,,manager/reset"));
        managerMenus.add(new MenuEntity("201", "添加账号", "accountCreate", 1, 2, 2, "manager/create"));
        managerMenus.add(new MenuEntity("202", "代理总览", "agents", 2, 2, 2, "manager/agents"));

        workMenus.add(new MenuEntity("300", "商品管理", "goods", 0, 3, 2, "manager/goods"));
        workMenus.add(new MenuEntity("301", "站内发布", "publish", 1, 3, 2, "manager/publish"));
    }

    @Override
    public void login() {
        try {
            Map<String, String> keys = RSAUtils.initKey();
            this.setSessionAttr(RSAUtils.PRIVATE_KEY, RSAUtils.getPrivateKey(keys));
            this.setSessionAttr(RSAUtils.PUBLIC_KEY, RSAUtils.getPublicKey(keys));
            this.setAttr(RSAUtils.PUBLIC_KEY, RSAUtils.getPublicKey(keys));
            this.setAttr("url", "/manager/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        render("/WEB-INF/login.html");
    }

    @Override
    public void logout() {
        this.removeSessionAttr(RSAUtils.PRIVATE_KEY);
        this.removeSessionAttr(RSAUtils.PUBLIC_KEY);
        this.removeSessionAttr("manager");
    }

    @Override
    public void access() {
        String privateKey = this.getSessionAttr("privateKey");
        String loginName = this.getPara("loginName");
        String password = this.getPara("password");
        if (StringUtils.isEmpty(privateKey) || StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            this.login();
            return;
        }
        try {
            String plainPassword = RSAUtils.decryptByPrivateKey(password, privateKey.toString());
            ManagerEntity managerEntity = iManagerServices.retrieveByLogin(loginName, plainPassword);
            if (managerEntity == null) {
                this.setAttr("loginErrorMessage", "账户或密码错误");
                this.login();
                return;
            } else {
                if (managerEntity.getStatus() == 1) {
                    this.setAttr("loginErrorMessage", "账户已经被禁用，请联系管理员");
                    this.renderError(403, "/webapp/widgets/403.html");
                } else if (managerEntity.getStatus() == 2) {
                    this.setSessionAttr("manager", managerEntity);
                    this.manage();
                } else {
                    if ("administrator".equals(loginName) && managerEntity.getLevel() < 0) {
                        this.setSessionAttr("manager", managerEntity);
                        this.setAttr("superMenus", superMenus);
                        this.manage();
                    } else {
                        this.login();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.renderError(500, "/webapp/widgets/500.html");
        }
    }

    @Override
    public void manage() {
        this.setAttr("url", "/manager/manage");
        ManagerEntity manager = this.getSessionAttr("manager");
        if (manager == null) {
            this.login();
        } else {
            String[] access = JSON.parseObject(manager.getAccess() == null ? "" : manager.getAccess(), String[].class);
            List<MenuEntity> managerMenusNew = new LinkedList<>();
            List<MenuEntity> workMenusNew = new LinkedList<>();
            if (access != null) {
                for (String ac : access) {
                    for (MenuEntity menu : managerMenus) {
                        if (menu.getId().equals(ac)) {
                            managerMenusNew.add(menu);
                        }
                    }
                    for (MenuEntity menu : workMenus) {
                        if (menu.getId().equals(ac)) {
                            workMenusNew.add(menu);
                        }
                    }
                }
            }
            this.setAttr("managerMenus", managerMenusNew);
            this.setAttr("workMenus", workMenusNew);
            this.setAttr("manager", manager);
            renderFreeMarker("/WEB-INF/terminal.html");
        }
    }

    @Override
    public void accounts() {
        int pageIndex = this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == 0 ? 12 : this.getParaToInt("pageSize");
        LinkedList<ManagerEntity> retrieves = iManagerServices.retrieves(pageIndex, pageSize);
        int accountsSum = iManagerServices.retrievesSum();
        this.setAttr("accounts", retrieves);
        this.setAttr("pages", accountsSum / pageSize + (accountsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/managerList.view.html");
    }

    @Override
    public void create() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        try {
            String loginName = this.getPara("loginName");
            String nickName = this.getPara("nickName");
            String cellPhone = this.getPara("cellphone");
            String password = this.getPara("password");
            String[] access = this.getParaValues("access");

            ManagerEntity managerEntity = new ManagerEntity();
            managerEntity.setId(UUID.randomUUID().toString());
            managerEntity.setLoginName(loginName);
            managerEntity.setNickName(nickName);
            managerEntity.setCellphone(cellPhone);
            managerEntity.setPassword(password);
            if (access != null) {
                managerEntity.setAccess(JSON.toJSONString(access).toString());
            }
            ManagerEntity createManager = iManagerServices.create(managerEntity);
            if (createManager == null) {
                renderJson("{\"result\":\"error\"}");
            } else {
                renderJson("{\"result\":\"ok\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson("{\"result\":\"error\"}");
        }
    }

    @Override
    public void grant() {

    }

    @Override
    public void password() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        ManagerEntity manager = this.getSessionAttr("manager");
        String password = manager.getPassword();
        String passwordOld = this.getPara("oldPassword");
        String passwordNew = this.getPara("newPassword");
        manager.setPassword(passwordNew);
        ManagerEntity managerEntity = iManagerServices.updatePassword(manager);
        if (managerEntity != null) {
            this.setAttr("manager",managerEntity);
            renderJson("{\"result\":\"ok\"}");
        } else {
            manager.setPassword(password);
            this.setAttr("manager",manager);
            renderJson("{\"result\":\"error\"}");
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void block() {

    }

    @Override
    public void unBlock() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void agents() {
        int pageIndex = this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == 0 ? 12 : this.getParaToInt("pageSize");
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveAll(pageIndex, pageSize);
        int agentsSum = iAgentServices.retrieveAllSum();
        this.setAttr("agents", agentEntities);
        this.setAttr("pages", agentsSum / pageSize + (agentsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/agentsList.view.html");
    }

    @Override
    public void goods() {

    }

    @Override
    public void publish() {

    }
}
