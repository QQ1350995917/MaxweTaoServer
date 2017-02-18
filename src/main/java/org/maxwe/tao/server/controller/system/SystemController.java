package org.maxwe.tao.server.controller.system;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.core.Controller;
import org.maxwe.tao.server.service.account.agent.AgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentServices;
import org.maxwe.tao.server.service.account.agent.IAgentServices;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-14 21:48.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SystemController extends Controller implements ISystemController {
    private IAgentServices iAgentServices = new AgentServices();

    @Override
    public void system() {

        render("/webapp/widgets/systemInfoList.view.html");
    }

    @Override
    public void money() {
        LinkedList<AgentEntity> agentEntities = iAgentServices.retrieveByTop();
        this.setAttr("topAgents", agentEntities);
        render("/webapp/widgets/systemMoneyList.view.html");
    }

    @Override
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
                this.setAttr("errorInfo","修改失败");
            }
        }
    }

    @Override
    public void backups() {
        render("/webapp/widgets/systemBackups.view.html");
    }

    @Override
    public void backup() {

    }

    @Override
    public void download() {

    }
}
