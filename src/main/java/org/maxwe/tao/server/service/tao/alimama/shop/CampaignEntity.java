package org.maxwe.tao.server.service.tao.alimama.shop;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-24 16:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 定向计划的属性
 */
public class CampaignEntity implements Serializable {
    private float commissionRate;// 10.30,活动佣金率
    private long CampaignID;// 26819145,活动ID
    private String CampaignName;// "易群推依依",活动名称
    private String CampaignType;// "定向推广计划",活动类型
    private String AvgCommission;// "7.01 %",活动平均佣金率
    private boolean Exist;// false,活动是否已经结束
    private long manualAudit;// 0,自动审核 1, 人工审核
    private long ShopKeeperID;// 115538439,商家的ID
    private String Properties;// "否"

    public CampaignEntity() {
        super();
    }

    public float getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(float commissionRate) {
        this.commissionRate = commissionRate;
    }

    public long getCampaignID() {
        return CampaignID;
    }

    public void setCampaignID(long campaignID) {
        CampaignID = campaignID;
    }

    public String getCampaignName() {
        return CampaignName;
    }

    public void setCampaignName(String campaignName) {
        CampaignName = campaignName;
    }

    public String getCampaignType() {
        return CampaignType;
    }

    public void setCampaignType(String campaignType) {
        CampaignType = campaignType;
    }

    public String getAvgCommission() {
        return AvgCommission;
    }

    public void setAvgCommission(String avgCommission) {
        AvgCommission = avgCommission;
    }

    public boolean isExist() {
        return Exist;
    }

    public void setExist(boolean exist) {
        Exist = exist;
    }

    public long getManualAudit() {
        return manualAudit;
    }

    public void setManualAudit(long manualAudit) {
        this.manualAudit = manualAudit;
    }

    public long getShopKeeperID() {
        return ShopKeeperID;
    }

    public void setShopKeeperID(long shopKeeperID) {
        ShopKeeperID = shopKeeperID;
    }

    public String getProperties() {
        return Properties;
    }

    public void setProperties(String properties) {
        Properties = properties;
    }
}
