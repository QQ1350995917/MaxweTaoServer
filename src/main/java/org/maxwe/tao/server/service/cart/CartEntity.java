package org.maxwe.tao.server.service.cart;

/**
 * Created by Pengwei Ding on 2016-09-05 11:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartEntity {
    private String mappingId;
    private String accountId;
    private String formatId;
    private int amount;
    private float pricing;//这个字段是记录了在支付成功时候的对应数量下的价格，也就是说formatId对应下现价乘以amount
    private String orderId;
    private int status;
    private String createTime;
    private String updateTime;

    public CartEntity() {
        super();
    }

    public CartEntity(String mappingId, String accountId, String formatId, int amount, float pricing, String orderId, int status, String createTime, String updateTime) {
        this.mappingId = mappingId;
        this.accountId = accountId;
        this.formatId = formatId;
        this.amount = amount;
        this.pricing = pricing;
        this.orderId = orderId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPricing() {
        return pricing;
    }

    public void setPricing(float pricing) {
        this.pricing = pricing;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
