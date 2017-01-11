package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * Created by Pengwei Ding on 2017-01-09 14:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ModifyModel extends SessionModel {

    public static final PropertyFilter propertyFilter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if ("oldPassword".equals(name) || "newPassword".equals(name)) {
                return false;
            }
            return true;
        }
    };

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
    @JSONField(serialize=false)
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
