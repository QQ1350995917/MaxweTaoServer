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
    private String reason;// 声请加入推广计划的理由
    private String rhetoric;// 分享推广的说辞
    private int refId;//推广人的ID
    private int refNum;//推广别人的历史总量
    private int refBalance;//推广积分余额

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRhetoric() {
        return rhetoric;
    }

    public void setRhetoric(String rhetoric) {
        this.rhetoric = rhetoric;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public int getRefNum() {
        return refNum;
    }

    public void setRefNum(int refNum) {
        this.refNum = refNum;
    }

    public int getRefBalance() {
        return refBalance;
    }

    public void setRefBalance(int refBalance) {
        this.refBalance = refBalance;
    }

    @Override
    public String toString() {
        return super.toString() + "UserEntity{" +
                "actCode='" + actCode + '\'' +
                ", actTime='" + actTime + '\'' +
                '}';
    }
}
