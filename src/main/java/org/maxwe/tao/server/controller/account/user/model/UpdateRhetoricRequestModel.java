package org.maxwe.tao.server.controller.account.user.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-25 14:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户更新分享推广说辞的请求模型
 */
public class UpdateRhetoricRequestModel extends TokenModel {
    private String rhetoric;

    public UpdateRhetoricRequestModel() {
        super();
    }

    public String getRhetoric() {
        return rhetoric;
    }

    public void setRhetoric(String rhetoric) {
        this.rhetoric = rhetoric;
    }

    public boolean isRhetoricParamsOk() {
        if (StringUtils.isEmpty(this.getRhetoric())) {
            return false;
        }
        if (this.getRhetoric().length() > 100) {
            return false;
        }
        return super.isTokenParamsOk();
    }
}
