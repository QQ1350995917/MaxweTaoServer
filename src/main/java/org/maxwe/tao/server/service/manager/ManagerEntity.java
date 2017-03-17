package org.maxwe.tao.server.service.manager;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.controller.account.manager.ManagerController;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2016-07-28 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员对象。为了便利性考虑暂时融合可控制层数据对象和持久层的数据对象
 * TODO: 可以考虑拆分
 */
public class ManagerEntity implements Serializable{
    private String id; // 数据ID //驻留session的内容
    private String loginName; // 登录名
    private String nickName; // 用户名
    private String cellphone; // 电话号码
    private String password; // 用户密码，即登录密码，非明文 TODO: 采用哪种加密方式
    private int level;
    private int status; // 数据状态，可以查询数据库元数据表，这里取值有3个，-1标示删除，1标示禁用，2标示正常
    private String access; // 权限
    @JSONField(serialize=false)
    private String[] accessLabels; // 权限标签
    private long createTime;
    private long updateTime;

    @JSONField(serialize=false)
    private String createTimeLabel;
    @JSONField(serialize=false)
    private String updateTimeLabel;

    public ManagerEntity() {
        super();
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        if (!StringUtils.isEmpty(this.getAccess())){
            String[] accesses = JSON.parseObject(this.getAccess(), String[].class);
            String[] accessLabels = new String[accesses.length];
            for (int i=0;i<accesses.length;i++){
                String label = ManagerController.workMenusLabel.get(accesses[i]);
                accessLabels[i] = label;
            }
            this.setAccessLabels(accessLabels);
        }
    }

    public String[] getAccessLabels() {
        return accessLabels;
    }

    public void setAccessLabels(String[] accessLabels) {
        this.accessLabels = accessLabels;
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
}
