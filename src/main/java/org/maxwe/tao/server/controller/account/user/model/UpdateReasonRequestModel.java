package org.maxwe.tao.server.controller.account.user.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-25 14:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户更新申请加入推广计划的理由的请求模型
 */
public class UpdateReasonRequestModel extends TokenModel {
    private String reason;

    public UpdateReasonRequestModel() {
        super();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isReasonParamsOk() {
        if (StringUtils.isEmpty(this.getReason())){
            return false;
        }
        if (this.getReason().length() < 4 || this.getReason().length() > 200){
            return false;
        }
        return super.isTokenParamsOk();
    }
}
