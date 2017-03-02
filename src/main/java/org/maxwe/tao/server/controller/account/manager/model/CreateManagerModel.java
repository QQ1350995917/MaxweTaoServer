package org.maxwe.tao.server.controller.account.manager.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Pengwei Ding on 2017-02-13 17:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class CreateManagerModel extends Model<CreateManagerModel> {
    public static final CreateManagerModel me = new CreateManagerModel();

    private String id; // 数据ID //驻留session的内容
    private String loginName; // 登录名
    private String nickName; // 用户名
    private String cellphone; // 电话号码
    private String password; // 用户密码，即登录密码，非明文
    private int status; // 数据状态，可以查询数据库元数据表，这里取值有3个，-1标示删除，1标示禁用，2标示正常
    private String access; // 权限
    private long createTime;
    private long updateTime;

    private String oldPassword; // 用户密码，即登录密码，非明文
    private String newPassword; // 用户密码，即登录密码，非明文

    public CreateManagerModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
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
}
