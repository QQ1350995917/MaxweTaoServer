package org.maxwe.tao.server.service.product;

/**
 * Created by Pengwei Ding on 2016-07-30 21:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FormatEntity {
    private String formatId;
    private int label;// 规格的量 比如 1，100，500，2，3，4
    private String meta;// 规格的单位 比如 kg, mk，L，盒，包，瓶
    private int amount;// 规格下的数量 通常是在，盒，包下的包含个数，比如每盒有4瓶
    private String amountMeta;// 规格下的数量的单位
    private float price;// 定价
    private final String priceMeta = "￥";// 定价单位 通常是￥
    private float postage;// 邮费
    private final String postageMeta = "￥";// 邮费单位 通常是￥
    private float pricing;// 现价
    private float pricingDiscount;// 现价对比定价的折扣
    private long pricingStart;// 折扣活动开始时间
    private long pricingEnd;// 折扣活动结束时间
    private int pricingStatus;// 折扣活动所处于的状态，是否要显示
    private int expressCount;// 包邮需要的数量
    private String expressName;// 邮递公司名称
    private long expressStart;// 包邮活动开始时间
    private long expressEnd;// 包邮活动结束时间
    private int expressStatus;// 包邮活动所处于的状态，是否要显示
//    private int giftCount;// 满赠需要的数量
//    private String giftLabel;// 满赠产品的名称
//    private String giftId;// 满赠产品规格的ID
//    private long giftStart;// 满赠活动开始时间
//    private long giftEnd;// 满赠活动结束时间
//    private int giftStatus;// 满赠活动所处于的状态
    private int queue;// 该规格的顺序
    private int weight;// 该规格的权重，推荐相关，小于0为推荐，越小权重越高
    private int status;// 该规格的状态
    private String typeId;// 规格的类型ID
    private long createTime;
    private long updateTime;

    public FormatEntity() {
        super();
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountMeta() {
        return amountMeta;
    }

    public void setAmountMeta(String amountMeta) {
        this.amountMeta = amountMeta;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPriceMeta() {
        return priceMeta;
    }

//    public void setPriceMeta(String priceMeta) {
//        this.priceMeta = priceMeta;
//    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public String getPostageMeta() {
        return postageMeta;
    }

//    public void setPostageMeta(String postageMeta) {
//        this.postageMeta = postageMeta;
//    }

    public float getPricing() {
        return pricing;
    }

    public void setPricing(float pricing) {
        this.pricing = pricing;
    }

    public float getPricingDiscount() {
        return pricingDiscount;
    }

    public void setPricingDiscount(float pricingDiscount) {
        this.pricingDiscount = pricingDiscount;
    }

    public long getPricingStart() {
        if (this.pricingStart < 0){
            pricingStart = 0;
        }
        return pricingStart;
    }

    public void setPricingStart(long pricingStart) {
        this.pricingStart = pricingStart;
    }

    public long getPricingEnd() {
        if (this.pricingEnd < 0){
            pricingEnd = 0;
        }
        return pricingEnd;
    }

    public void setPricingEnd(long pricingEnd) {
        this.pricingEnd = pricingEnd;
    }

    public int getPricingStatus() {
        return pricingStatus;
    }

    public void setPricingStatus(int pricingStatus) {
        this.pricingStatus = pricingStatus;
    }

    public int getExpressCount() {
        return expressCount;
    }

    public void setExpressCount(int expressCount) {
        this.expressCount = expressCount;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public long getExpressStart() {
        return expressStart;
    }

    public void setExpressStart(long expressStart) {
        this.expressStart = expressStart;
    }

    public long getExpressEnd() {
        return expressEnd;
    }

    public void setExpressEnd(long expressEnd) {
        this.expressEnd = expressEnd;
    }

    public int getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(int expressStatus) {
        this.expressStatus = expressStatus;
    }

//    public int getGiftCount() {
//        return giftCount;
//    }
//
//    public void setGiftCount(int giftCount) {
//        this.giftCount = giftCount;
//    }
//
//    public String getGiftLabel() {
//        return giftLabel;
//    }
//
//    public void setGiftLabel(String giftLabel) {
//        this.giftLabel = giftLabel;
//    }
//
//    public String getGiftId() {
//        return giftId;
//    }
//
//    public void setGiftId(String giftId) {
//        this.giftId = giftId;
//    }
//
//    public long getGiftStart() {
//        return giftStart;
//    }
//
//    public void setGiftStart(long giftStart) {
//        this.giftStart = giftStart;
//    }
//
//    public long getGiftEnd() {
//        return giftEnd;
//    }
//
//    public void setGiftEnd(long giftEnd) {
//        this.giftEnd = giftEnd;
//    }
//
//    public int getGiftStatus() {
//        return giftStatus;
//    }
//
//    public void setGiftStatus(int giftStatus) {
//        this.giftStatus = giftStatus;
//    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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
