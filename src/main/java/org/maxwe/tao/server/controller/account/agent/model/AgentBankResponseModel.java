package org.maxwe.tao.server.controller.account.agent.model;

import org.maxwe.tao.server.common.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-07 14:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 绑定返点账户的相应模型
 */
public class AgentBankResponseModel extends ResponseModel<AgentBankRequestModel> {
    private long bankTime;

    public AgentBankResponseModel() {
        super();
    }

    public AgentBankResponseModel(AgentBankRequestModel requestModel) {
        super(requestModel);
    }

    public AgentBankResponseModel(AgentBankRequestModel requestModel, long bankTime) {
        super(requestModel);
        this.bankTime = bankTime;
    }

    public long getBankTime() {
        return bankTime;
    }

    public void setBankTime(long bankTime) {
        this.bankTime = bankTime;
    }
}
