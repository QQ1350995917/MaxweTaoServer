package org.maxwe.tao.server.service.menu;

/**
 * Created by Pengwei Ding on 2016-07-28 16:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理界面菜单对象
 */
public class MenuEntity {
    private String id; //ID
    private String label; //显示的名称
    private String flag; //前端点击调用标记
    private int queue; // 顺序
    private int category; //种类
    private int status; //-1删除状态，1，禁用状态，2正常状态，3该菜单除了超级管理员不可授予其他人
    private String actionKey;//使用,分割的动作名称
    private boolean granted;
    private String createTime;
    private String updateTime;

    public MenuEntity() {}

    public MenuEntity(String id, String label, String flag, int queue, int category, int status, String actionKey) {
        this.id = id;
        this.label = label;
        this.flag = flag;
        this.queue = queue;
        this.category = category;
        this.status = status;
        this.actionKey = actionKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    public boolean getGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
