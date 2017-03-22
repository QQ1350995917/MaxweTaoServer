package org.maxwe.tao.server.controller.account.agent.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.AuthenticateModel;

/**
 * Created by Pengwei Ding on 2017-03-07 14:51.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 绑定返点账户的请求模型
 */
public class AgentBankRequestModel extends AuthenticateModel {
    private String trueName;
    private String zhifubao;

    public AgentBankRequestModel() {
        super();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao;
    }

    @JSONField(serialize=false)
    public boolean isAgentBankRequestParamsOk(){
        if (StringUtils.isEmpty(trueName)){
            return false;
        }

        if (StringUtils.isEmpty(zhifubao)){
            return false;
        }

        if (!super.isAuthenticateParamsOk()){
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        AgentBankRequestModel agentBankRequestModel = new AgentBankRequestModel();
        agentBankRequestModel.setApt(2);
        agentBankRequestModel.setAuthenticatePassword("111111");
        agentBankRequestModel.setCellphone("18516883957");
        agentBankRequestModel.setId(100007);
        agentBankRequestModel.setSign("0C7ABB2B3253C1ABD13D7391B7CFF0B8F02A320670D88933659179102FD1C79F419146861E6540203845E1C20B496552");
        agentBankRequestModel.setT("573b8cf284eb797303416867776d2683");
        agentBankRequestModel.setTrueName("123123");
        agentBankRequestModel.setZhifubao("123123");

        System.out.println(agentBankRequestModel.isAgentBankRequestParamsOk());

    }
}
