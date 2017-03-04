package org.maxwe.tao.server.controller.trade.model;

import org.maxwe.tao.server.controller.account.model.AuthenticateModel;
import org.maxwe.tao.server.controller.mate.model.MateModel;

/**
 * Created by Pengwei Ding on 2017-03-04 11:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理之间发生授权码交易的请求模型
 */
public class TradeRequestModel extends AuthenticateModel {
    private MateModel branchAgent;
    private int codeNum;

    public TradeRequestModel() {
        super();
    }

    public MateModel getBranchAgent() {
        return branchAgent;
    }

    public void setBranchAgent(MateModel branchAgent) {
        this.branchAgent = branchAgent;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    public boolean isTradeRequestParamsOk() {
        if (this.getBranchAgent().getAgent() == null) {
            return false;
        }
        if (this.getBranchAgent().getAgent().getId() < 0) {
            return false;
        }
        if (this.getBranchAgent().getLevel() == null) {
            return false;
        }
        if (this.getBranchAgent().getLevel().getLevel() < 0) {
            return false;
        }
        return super.isAuthenticateParamsOk() && this.getCodeNum() > 0 && this.getCodeNum() < 10000;
    }
}
