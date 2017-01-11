package org.maxwe.tao.server.controller.account.agent.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.controller.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-09 18:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BankModel extends SessionModel {
    private String trueName;
    private String zhifubao;
    private String password;
    private long timestamp;// 响应字段

    public BankModel() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BankModel{" +
                "trueName='" + trueName + '\'' +
                ", zhifubao='" + zhifubao + '\'' +
                ", password='" + password + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public boolean isParamsOk() {
        if (StringUtils.isEmpty(this.getTrueName())
                || StringUtils.isEmpty(this.getZhifubao())
                || StringUtils.isEmpty(this.getPassword())
                || this.getPassword().length() < 6
                || this.getPassword().length() > 12) {
            return false;
        }
        return super.isParamsOk();
    }
}
