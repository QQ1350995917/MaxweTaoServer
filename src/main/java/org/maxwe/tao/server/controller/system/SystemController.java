package org.maxwe.tao.server.controller.system;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.interceptor.SessionInterceptor;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;
import org.maxwe.tao.server.service.system.SystemServices;
import org.maxwe.tao.server.service.tao.jidi.JiDiServices;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-14 21:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemController extends Controller implements ISystemController {
    private IAgentServices iAgentServices = new AgentServices();

    @Override
    @Before(SessionInterceptor.class)
    public void system() {

        render("/webapp/widgets/systemInfoList.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void money() {
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveByTop();
        this.setAttr("topAgents", agentEntities);
        render("/webapp/widgets/systemMoneyList.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void append() {
        String mark = this.getPara("mark");
        int appendCodes = this.getParaToInt("appendCodes");
        if (StringUtils.isEmpty(mark) || appendCodes <= 0 || appendCodes > 1000) {
            this.setAttr("errorInfo", "提交参数错误");
        } else {
            AgentEntity agentEntity = new AgentEntity();
            agentEntity.setMark(mark);
            boolean appendResult = iAgentServices.appendCodes(agentEntity, appendCodes);
            if (appendResult) {

            } else {
                this.setAttr("errorInfo", "修改失败");
            }
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void backups() {
        render("/webapp/widgets/systemBackups.view.html");
    }

    @Override
    @Before(SessionInterceptor.class)
    public void backup() {
        this.getResponse().setContentType("application/json; charset=utf-8");
        try {
            SystemServices.getInstance().backup("/Users/dingpengwei/tao" + DateTimeUtils.getCurrentTimestamp() + ".sql", "root", "root", "tao");
            renderJson("{\"result\":\"ok\"}");
        } catch (Exception e) {
            e.printStackTrace();
            renderError(500);
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void download() {

    }


    @Override
    @Before(SessionInterceptor.class)
    public void initThird() {
        JiDiServices.getInstance().init();
        renderJson(new String[]{"ok"});
    }

    @Override
    @Before(SessionInterceptor.class)
    public void summaryThird() {
        int dataCounter = JiDiServices.getInstance().getDataCounter();
        this.setAttr("dataCounter",dataCounter);
        render("/webapp/widgets/systemThirdData.view.html");
    }
}
