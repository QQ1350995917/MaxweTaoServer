package org.maxwe.tao.server.controller.business;

import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.controller.user.VAgentEntity;
import org.maxwe.tao.server.service.account.agent.AgentEntity;

/**
 * Created by Pengwei Ding on 2017-01-05 17:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TradeModel extends VAgentEntity {
    private int tradeCode;//交易授权码的数量
    private AgentEntity authorizedAgent;

    public TradeModel() {
        super();
    }

    public int getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(int tradeCode) {
        this.tradeCode = tradeCode;
    }

    public AgentEntity getAuthorizedAgent() {
        return authorizedAgent;
    }

    public void setAuthorizedAgent(AgentEntity authorizedAgent) {
        this.authorizedAgent = authorizedAgent;
    }

    public boolean checkFindAgentParams(){
        if (this.authorizedAgent == null || !CellPhoneUtils.isCellphone(this.authorizedAgent.getCellphone())){
            return false;
        }
        return true;
    }

    public boolean checkGrantParams(){
        if (this.tradeCode < 1 || this.authorizedAgent == null || !CellPhoneUtils.isCellphone(this.authorizedAgent.getCellphone())){
            return false;
        }
        return true;
    }

    public boolean checkTradeParams(){
        if (!this.checkGrantParams()){
            return false;
        }
        return true;
    }

}
