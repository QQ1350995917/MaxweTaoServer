package org.maxwe.tao.server.controller.user.seller;

import org.maxwe.tao.server.service.user.seller.SellerEntity;

/**
 * Created by Pengwei Ding on 2016-09-29 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VSellerEntity extends SellerEntity {
    private String t;
    private String ordPassword;
    private String newPassword;

    public VSellerEntity() {
        super();
    }

    public VSellerEntity(String t) {
        super();
        this.t = t;
    }

    public VSellerEntity(SellerEntity sellerEntity){
        this.setSellerId(sellerEntity.getSellerId());
        this.setName(sellerEntity.getName());
        this.setCellphone(sellerEntity.getCellphone());
        this.setPassword(sellerEntity.getPassword());
        this.setStatus(sellerEntity.getStatus());
        this.setCreateTime(sellerEntity.getCreateTime());
        this.setUpdateTime(sellerEntity.getUpdateTime());
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

    public boolean checkCreateParams(){
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
