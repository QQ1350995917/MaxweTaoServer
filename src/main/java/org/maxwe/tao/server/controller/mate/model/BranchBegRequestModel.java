package org.maxwe.tao.server.controller.mate.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.AuthenticateModel;

/**
 * Created by Pengwei Ding on 2017-03-03 15:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理向上级发起授权请求模型
 */
public class BranchBegRequestModel extends AuthenticateModel {
    private int trunkId;//上级的ID
    private String weChat;//本人的微信号码

    public BranchBegRequestModel() {
        super();
    }

    public int getTrunkId() {
        return trunkId;
    }

    public void setTrunkId(int trunkId) {
        this.trunkId = trunkId;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    @JSONField(serialize = false)
    public boolean isBranchBegParamsOk() {
        return super.isAuthenticateParamsOk() && this.getTrunkId() > 0 && !StringUtils.isEmpty(this.getWeChat());
    }
}
