package org.maxwe.tao.server.controller.account.user;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.controller.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-09 18:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ActiveModel extends SessionModel {
    private String actCode;

    public ActiveModel() {
        super();
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    @Override
    public boolean isParamsOk() {
        if (!StringUtils.isEmpty(this.getActCode())) {
            return true && super.isParamsOk();
        }
        return false;
    }
}
