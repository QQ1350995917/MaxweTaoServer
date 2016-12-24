package org.maxwe.tao.server.service.user;

/**
 * Created by Pengwei Ding on 2016-08-16 09:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverEntity {
    private String receiverId;
    private String provinceId;
    private String provinceLabel;
    private String cityId;
    private String cityLabel;
    private String countyId;
    private String countyLabel;
    private String townId;
    private String townLabel;
    private String villageId;
    private String villageLabel;
    private String appendLabel;
    private String receiverName;
    private String receiverPhone;
    private String backupPhone;
    private String receiverStatus;
    private String accountId;
    private String createTime;
    private String updateTime;

    public ReceiverEntity() {
        super();
    }

    public ReceiverEntity(String receiverId, String provinceId, String provinceLabel, String cityId, String cityLabel, String countyId, String countyLabel, String townId, String townLabel, String villageId, String villageLabel, String appendLabel, String receiverName, String receiverPhone, String backupPhone, String receiverStatus, String accountId, String createTime, String updateTime) {
        this.receiverId = receiverId;
        this.provinceId = provinceId;
        this.provinceLabel = provinceLabel;
        this.cityId = cityId;
        this.cityLabel = cityLabel;
        this.countyId = countyId;
        this.countyLabel = countyLabel;
        this.townId = townId;
        this.townLabel = townLabel;
        this.villageId = villageId;
        this.villageLabel = villageLabel;
        this.appendLabel = appendLabel;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.backupPhone = backupPhone;
        this.receiverStatus = receiverStatus;
        this.accountId = accountId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceLabel() {
        return provinceLabel;
    }

    public void setProvinceLabel(String provinceLabel) {
        this.provinceLabel = provinceLabel;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(String cityLabel) {
        this.cityLabel = cityLabel;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyLabel() {
        return countyLabel;
    }

    public void setCountyLabel(String countyLabel) {
        this.countyLabel = countyLabel;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getTownLabel() {
        return townLabel;
    }

    public void setTownLabel(String townLabel) {
        this.townLabel = townLabel;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getVillageLabel() {
        return villageLabel;
    }

    public void setVillageLabel(String villageLabel) {
        this.villageLabel = villageLabel;
    }

    public String getAppendLabel() {
        return appendLabel;
    }

    public void setAppendLabel(String appendLabel) {
        this.appendLabel = appendLabel;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getBackupPhone() {
        return backupPhone;
    }

    public void setBackupPhone(String backupPhone) {
        this.backupPhone = backupPhone;
    }

    public String getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(String receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
