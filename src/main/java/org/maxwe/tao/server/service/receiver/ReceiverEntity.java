package org.maxwe.tao.server.service.receiver;

/**
 * Created by Pengwei Ding on 2016-08-31 14:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverEntity {
    private String receiverId;
    private String name;
    private String phone0;
    private String phone1;
    private String province;
    private String city;
    private String county;
    private String town;
    private String village;
    private String append;
    private int status; // -1标示删除，1标示禁用，2标示正常，3标示默认收货地址。数据库中该字段默认为2，目前暂时没有1的状态。
    private String accountId;
    private String createTime;
    private String updateTime;

    public ReceiverEntity() {
        super();
    }

    public ReceiverEntity(String receiverId, String province, String city, String county, String town, String village, String append, String name, String phone0, String phone1, int status, String accountId, String createTime, String updateTime) {
        this.receiverId = receiverId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.town = town;
        this.village = village;
        this.append = append;
        this.name = name;
        this.phone0 = phone0;
        this.phone1 = phone1;
        this.status = status;
        this.accountId = accountId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ReceiverEntity(String receiverId, String province, String city, String county, String town, String village, String append, String name, String phone0, String phone1, int status, String accountId) {
        this.receiverId = receiverId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.town = town;
        this.village = village;
        this.append = append;
        this.name = name;
        this.phone0 = phone0;
        this.phone1 = phone1;
        this.status = status;
        this.accountId = accountId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone0() {
        return phone0;
    }

    public void setPhone0(String phone0) {
        this.phone0 = phone0;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
