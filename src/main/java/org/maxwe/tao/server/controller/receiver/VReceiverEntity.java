package org.maxwe.tao.server.controller.receiver;

import org.maxwe.tao.server.service.receiver.ReceiverEntity;

/**
 * Created by Pengwei Ding on 2016-09-21 18:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VReceiverEntity extends ReceiverEntity {
    private String cs;

    public VReceiverEntity() {
        super();
    }

    public VReceiverEntity(ReceiverEntity receiverEntity) {
        this.setReceiverId(receiverEntity.getReceiverId());
        this.setName(receiverEntity.getName());
        this.setPhone0(receiverEntity.getPhone0());
        this.setPhone1(receiverEntity.getPhone1());
        this.setProvince(receiverEntity.getProvince());
        this.setCity(receiverEntity.getCity());
        this.setCounty(receiverEntity.getCounty());
        this.setTown(receiverEntity.getTown());
        this.setVillage(receiverEntity.getVillage());
        this.setAppend(receiverEntity.getAppend());
        this.setStatus(receiverEntity.getStatus());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public boolean checkReceiverIdParams() {
        if (this.getReceiverId() == null || this.getReceiverId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCreateParams() {
        if (this.getName() == null || this.getName().trim().equals("")) {
            return false;
        }
        if (this.getPhone0() == null || this.getPhone0().trim().equals("")) {
            return false;
        }
        if (this.getProvince() == null || this.getProvince().trim().equals("")) {
            return false;
        }
        if (this.getCity() == null || this.getCity().trim().equals("")) {
            return false;
        }
        if (this.getCounty() == null || this.getCounty().trim().equals("")) {
            return false;
        }
        if (this.getTown() == null || this.getTown().trim().equals("")) {
            return false;
        }
        if (this.getVillage() == null || this.getVillage().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean checkUpdateParams() {
        if (!this.checkCreateParams()) {
            return false;
        }
        if (this.getReceiverId() == null || this.getReceiverId().trim().equals("")) {
            return false;
        }
        if (this.getStatus() != 2 && this.getStatus() != 3) {
            return false;
        }
        return true;
    }

}
