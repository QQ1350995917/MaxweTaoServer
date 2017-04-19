package org.maxwe.tao.server.controller.account.manager;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.PasswordUtils;
import org.maxwe.tao.server.common.utils.RSAUtils;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.account.user.IUserServices;
import org.maxwe.tao.server.service.account.user.UserEntity;
import org.maxwe.tao.server.service.account.user.UserServices;
import org.maxwe.tao.server.service.manager.IManagerServices;
import org.maxwe.tao.server.service.manager.ManagerEntity;
import org.maxwe.tao.server.service.manager.ManagerServices;
import org.maxwe.tao.server.service.menu.MenuEntity;

import java.util.*;

/**
 * Created by Pengwei Ding on 2017-02-09 21:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class ManagerController extends Controller implements IManagerController {
    private final Logger logger = Logger.getLogger(ManagerController.class.getName());
    private IManagerServices iManagerServices = new ManagerServices();
    private IAgentServices iAgentServices = new AgentServices();
    private IUserServices iUserServices = new UserServices();
    public static final List<MenuEntity> superMenus = new LinkedList<>();
    public static final List<MenuEntity> managerMenus = new LinkedList<>();
    public static final List<MenuEntity> workMenus = new LinkedList<>();
    public static final HashMap<String,String> workMenusLabel = new LinkedHashMap<>();

    static {
        superMenus.add(new MenuEntity("100", "系统总览", "system", 0, 2, 2, ""));
        superMenus.add(new MenuEntity("101", "运营报表", "analysis", 1, 2, 2, ""));
        superMenus.add(new MenuEntity("102", "码量管理", "money", 2, 2, 2, ""));
        superMenus.add(new MenuEntity("103", "备份管理", "export", 3, 2, 2, ""));
        superMenus.add(new MenuEntity("104", "版本管理", "version", 4, 2, 2, ""));


        managerMenus.add(new MenuEntity("200", "账号管理", "accountManager", 0, 2, 2, "manager/block,manager/unBlock,manager/delete,manager/grant,,manager/reset"));
        managerMenus.add(new MenuEntity("201", "添加账号", "accountCreate", 1, 2, 2, "manager/create"));
        managerMenus.add(new MenuEntity("204", "代理级别", "levels", 4, 2, 2, ""));
        managerMenus.add(new MenuEntity("202", "代理总览", "agents", 2, 2, 2, "manager/agents"));
        managerMenus.add(new MenuEntity("203", "用户总览", "users", 3, 2, 2, "manager/agents"));


        workMenus.add(new MenuEntity("300", "商品管理", "tao", 0, 3, 2, "manager/tao"));
        workMenus.add(new MenuEntity("301", "站内发布", "publish", 1, 3, 2, "manager/publish"));
        workMenus.add(new MenuEntity("302", "三方数据", "tao", 2, 3, 2, "manager/tao"));

        workMenusLabel.put("200", "账号管理");
        workMenusLabel.put("201", "添加账号");
        workMenusLabel.put("204", "代理级别");
        workMenusLabel.put("202", "代理总览");
        workMenusLabel.put("203", "用户总览");
        workMenusLabel.put("300", "商品管理");
        workMenusLabel.put("301", "站内发布");
        workMenusLabel.put("302", "三方数据");
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
    @Before(SessionInterceptor.class)
    public void logout() {
        this.removeSessionAttr(RSAUtils.PRIVATE_KEY);
        this.removeSessionAttr(RSAUtils.PUBLIC_KEY);
        this.removeSessionAttr("manager");
        this.login();
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
            String plainPasswordIn = RSAUtils.decryptByPrivateKey(password, privateKey.toString());
            ManagerEntity managerEntity = iManagerServices.retrieveByLoginName(loginName);
            if (managerEntity == null) {
                this.setAttr("loginErrorMessage", "账户或密码错误");
                this.login();
                return;
            }
            String plainPasswordOut = PasswordUtils.dePassword(managerEntity.getCellphone(), managerEntity.getPassword());
            if (!plainPasswordOut.equals(plainPasswordIn)) {
                this.setAttr("loginErrorMessage", "账户或密码错误");
                this.login();
                return;
            }
            if (managerEntity.getStatus() == 1) {
                this.setAttr("loginErrorMessage", "账户已经被禁用，请联系管理员");
                this.renderError(403, "/webapp/widgets/403.html");
            } else if (managerEntity.getStatus() == 2) {
                this.setSessionAttr("manager", managerEntity);
                this.manage();
            } else {
                if ("administrator110".equals(loginName) && managerEntity.getLevel() < 0) {
                    this.setSessionAttr("manager", managerEntity);
                    this.manage();
                } else {
                    this.login();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.renderError(500, "/webapp/widgets/500.html");
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void manage() {
        this.setAttr("url", "/manager/manage");
        ManagerEntity manager = this.getSessionAttr("manager");
        if (manager == null) {
            this.login();
            return;
        }
        if ("administrator110".equals(manager.getLoginName()) && manager.getLevel() < 0) {
            this.setAttr("superMenus", superMenus);
        }

        String[] access = null;
        if (manager.getAccess() != null) {
            access = JSON.parseObject(manager.getAccess(), String[].class);
        }

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

    @Override
    @Before(SessionInterceptor.class)
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
    @Before(SessionInterceptor.class)
    public void create() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        try {
            String loginName = this.getPara("loginName");
            String nickName = this.getPara("nickName");
            String cellPhone = this.getPara("cellphone");
            String password = this.getPara("password");
            String[] access = this.getParaValues("access");

            if (StringUtils.isEmpty(loginName) ||
                    StringUtils.isEmpty(nickName) ||
                    StringUtils.isEmpty(cellPhone) ||
                    !CellPhoneUtils.isCellphone(cellPhone) ||
                    StringUtils.isEmpty(password)
                    ) {
                renderError(400);
                return;
            }
            String privateKey = this.getSessionAttr("privateKey");
            String plainPassword = RSAUtils.decryptByPrivateKey(password, privateKey.toString());
            if (plainPassword.length() < 12 || plainPassword.length() > 22) {
                renderError(400);
                return;
            }
            String enPassword = PasswordUtils.enPassword(cellPhone, plainPassword);
            ManagerEntity managerEntity = new ManagerEntity();
            managerEntity.setId(UUID.randomUUID().toString());
            managerEntity.setLoginName(loginName);
            managerEntity.setNickName(nickName);
            managerEntity.setCellphone(cellPhone);
            managerEntity.setPassword(enPassword);
            if (access != null) {
                managerEntity.setAccess(JSON.toJSONString(access).toString());
            }
            ManagerEntity createManager = iManagerServices.create(managerEntity);
            if (createManager == null) {
                renderError(500);
            } else {
                renderJson("{\"result\":\"ok\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderError(400);
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void grant() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        String loginName = this.getPara("loginName");
        String[] access = this.getParaValues("access");
        if (access == null){
            access = new String[]{};
        }
        boolean result = iManagerServices.updateGrant(loginName, JSON.toJSONString(access));
        if (result) {
            renderJson("{\"result\":\"ok\"}");
        } else {
            renderError(500);
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void password() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        ManagerEntity manager = this.getSessionAttr("manager");
        String password = manager.getPassword();
        String passwordOld = this.getPara("oldPassword");
        String passwordNew = this.getPara("newPassword");
        if (StringUtils.isEmpty(passwordOld) || StringUtils.isEmpty(passwordNew)) {
            renderError(400);
            return;
        }
        String privateKey = this.getSessionAttr("privateKey");
        String plainPasswordOld = RSAUtils.decryptByPrivateKey(passwordOld, privateKey.toString());
        String plainPasswordNew = RSAUtils.decryptByPrivateKey(passwordNew, privateKey.toString());

        if (plainPasswordOld.length() < 12 || plainPasswordOld.length() > 22 || plainPasswordNew.length() < 12 || plainPasswordNew.length() > 22) {
            renderError(400);
            return;
        }
        String dePasswordOldOut = PasswordUtils.dePassword(manager.getCellphone(), password);
        if (!plainPasswordOld.equals(dePasswordOldOut)) {
            renderError(400);
            return;
        }
        String enPasswordNew = PasswordUtils.enPassword(manager.getCellphone(), plainPasswordNew);
        manager.setPassword(enPasswordNew);
        ManagerEntity managerEntity = iManagerServices.updatePassword(manager);
        if (managerEntity != null) {
            renderJson("{\"result\":\"ok\"}");
        } else {
            manager.setPassword(password);
            renderError(500);
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void reset() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void block() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void unBlock() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void delete() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void agents() {
        int pageIndex = this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == 0 ? 12 : this.getParaToInt("pageSize");
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveAll(pageIndex, pageSize);
        int agentsSum = iAgentServices.retrieveAllSum();
        logger.info("agents all size :" + agentsSum + " agents current page size :" + agentEntities.size());
        this.setAttr("agents", agentEntities);
        this.setAttr("pages", agentsSum / pageSize + (agentsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/managerAgentsList.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void users() {
        int pageIndex = this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == 0 ? 12 : this.getParaToInt("pageSize");
        LinkedList<UserEntity> userEntities = iUserServices.retrieveAll(pageIndex, pageSize);
        int usersSum = iUserServices.retrieveAllSum();
        this.setAttr("users", userEntities);
        this.setAttr("pages", usersSum / pageSize + (usersSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/managerUsersList.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void goods() {
        render("/webapp/widgets/businessListData.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void publish() {

    }


    public void index(){
        renderJson("{\"result\":\"ok\"}");
    }
}
