package org.maxwe.tao.server.controller.account.agent.model;

import com.alibaba.druid.util.StringUtils;
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
}
