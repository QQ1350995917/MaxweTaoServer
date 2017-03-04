package org.maxwe.tao.server.service.account;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.DateTimeUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 14:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 账户基本信息
 * 细分账户都应该继承自该类
 */
public class AccountEntity implements Serializable {
    private int id; // 业务ID，不能暴露给客户端
    private String cellphone; //手机号码 唯一
    private String password; // 登录密码
    private String name; // 用户备注名字 数据库可为空
    private int status; // 状态，0禁用，1正常，数据库默认为1
    private long createTime; // 创建时间
    private long updateTime; // 更新时间


    @JSONField(serialize=false)
    private String createTimeLabel;
    @JSONField(serialize=false)
    private String updateTimeLabel;



    public AccountEntity() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getCreateTime());
    }


    public String getUpdateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getUpdateTime());
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id='" + id + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
