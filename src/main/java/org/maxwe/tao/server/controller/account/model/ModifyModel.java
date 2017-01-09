package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-09 14:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ModifyModel extends SessionModel {

    private String oldPassword;
    private String newPassword;

    public ModifyModel() {
        super();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean isParamsOk() {
        boolean paramsOk = super.isParamsOk();
        if (!StringUtils.isEmpty(this.getOldPassword())
                && !StringUtils.isEmpty(this.getNewPassword())
                && this.getNewPassword().length() >= 6
                && this.getNewPassword().length() <= 12) {
            return true && paramsOk;
        } else {
            return false;
        }
    }
}
