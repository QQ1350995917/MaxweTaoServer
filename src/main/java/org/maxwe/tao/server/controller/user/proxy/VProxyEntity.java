package org.maxwe.tao.server.controller.user.proxy;

import org.maxwe.tao.server.service.user.proxy.ProxyEntity;

/**
 * Created by Pengwei Ding on 2016-09-29 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VProxyEntity extends ProxyEntity {
    private String t;
    private String ordPassword;
    private String newPassword;

    public VProxyEntity() {
        super();
    }

    public VProxyEntity(String t) {
        super();
        this.t = t;
    }

    public VProxyEntity(ProxyEntity proxyEntity) {
        this.setProxyId(proxyEntity.getProxyId());
        this.setProxyPId(proxyEntity.getProxyPId());
        this.setName(proxyEntity.getName());
        this.setCellphone(proxyEntity.getCellphone());
        this.setPassword(proxyEntity.getPassword());
        this.setPortrait(proxyEntity.getPortrait());
        this.setLevel(proxyEntity.getLevel());
        this.setStatus(proxyEntity.getStatus());
        this.setCreateTime(proxyEntity.getCreateTime());
        this.setUpdateTime(proxyEntity.getUpdateTime());
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getOrdPassword() {
        return ordPassword;
    }

    public void setOrdPassword(String ordPassword) {
        this.ordPassword = ordPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean checkCreateParams() {
        if (this.getCellphone() == null || this.getCellphone().trim().equals("")
                || this.getPassword() == null || this.getPassword().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkLoginParams() {
        if (this.getCellphone() == null || this.getCellphone().trim().equals("")
                || this.getPassword() == null || this.getPassword().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
