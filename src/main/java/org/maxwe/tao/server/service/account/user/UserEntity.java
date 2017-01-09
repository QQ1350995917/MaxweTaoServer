package org.maxwe.tao.server.service.account.user;

import org.maxwe.tao.server.service.account.AccountEntity;

/**
 * Created by Pengwei Ding on 2017-01-09 14:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserEntity extends AccountEntity {
    private String actCode; // 激活码，激活时候才有数据，数据库可为空
    private long actTime; // 激活时间，激活时候才有数据，数据库可为空

    public UserEntity() {
        super();
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public long getActTime() {
        return actTime;
    }

    public void setActTime(long actTime) {
        this.actTime = actTime;
    }

    @Override
    public String toString() {
        return super.toString() + "UserEntity{" +
                "actCode='" + actCode + '\'' +
                ", actTime='" + actTime + '\'' +
                '}';
    }
}
