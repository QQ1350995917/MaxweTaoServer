package org.maxwe.tao.server.service.manager;

/**
 * Created by Pengwei Ding on 2016-07-28 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员对象。为了便利性考虑暂时融合可控制层数据对象和持久层的数据对象
 * TODO: 可以考虑拆分
 */
public class ManagerEntity {
    private String managerId; // 数据ID //驻留session的内容
    private String loginName; // 登录名
    private String username; // 用户名
    private String password; // 用户密码，即登录密码，非明文 TODO: 采用哪种加密方式
    private int level;
    private int queue;
    private int status; // 数据状态，可以查询数据库元数据表，这里取值有3个，-1标示删除，1标示禁用，2标示正常
    private long createTime;
    private long updateTime;

    public ManagerEntity() {
        super();
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
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


}
