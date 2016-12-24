package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.common.utils.DateTime;
import org.maxwe.tao.server.service.product.FormatEntity;

/**
 * Created by Pengwei Ding on 2016-09-23 11:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VFormatEntity extends FormatEntity {
    private String cs;
    private String formatId1;
    private int weight1;
    private String formatId2;
    private int weight2;
    private VTypeEntity parent;
    private int currentPageIndex = 0;
    private int sizeInPage = 12;

    public VFormatEntity() {
        super();
    }

    public VFormatEntity(FormatEntity formatEntity) {
        this.setTypeId(formatEntity.getTypeId());
        this.setFormatId(formatEntity.getFormatId());
        this.setLabel(formatEntity.getLabel());
        this.setMeta(formatEntity.getMeta());
        this.setAmount(formatEntity.getAmount());
        this.setAmountMeta(formatEntity.getAmountMeta());
        this.setPrice(formatEntity.getPrice());
//        this.setPriceMeta(formatEntity.getPriceMeta());
        this.setPostage(formatEntity.getPostage());
//        this.setPostageMeta(formatEntity.getPostageMeta());
        this.setPricing(formatEntity.getPricing());
        this.setPricingDiscount(formatEntity.getPricingDiscount());
        this.setPricingStart(formatEntity.getPricingStart());
        this.setPricingEnd(formatEntity.getPricingEnd());
        this.setPricingStatus(formatEntity.getPricingStatus());
        this.setExpressCount(formatEntity.getExpressCount());
        this.setExpressName(formatEntity.getExpressName());
        this.setExpressStart(formatEntity.getExpressStart());
        this.setExpressEnd(formatEntity.getExpressEnd());
        this.setExpressStatus(formatEntity.getExpressStatus());
//        this.setGiftCount(formatEntity.getGiftCount());
//        this.setGiftLabel(formatEntity.getGiftLabel());
//        this.setGiftId(formatEntity.getGiftId());
//        this.setGiftStart(formatEntity.getGiftStart());
//        this.setGiftEnd(formatEntity.getGiftEnd());
//        this.setGiftStatus(formatEntity.getGiftStatus());
        this.setWeight(formatEntity.getWeight());
        this.setQueue(formatEntity.getQueue());
        this.setStatus(formatEntity.getStatus());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getFormatId1() {
        return formatId1;
    }

    public void setFormatId1(String formatId1) {
        this.formatId1 = formatId1;
    }

    public int getWeight1() {
        return weight1;
    }

    public void setWeight1(int weight1) {
        this.weight1 = weight1;
    }

    public String getFormatId2() {
        return formatId2;
    }

    public void setFormatId2(String formatId2) {
        this.formatId2 = formatId2;
    }

    public int getWeight2() {
        return weight2;
    }

    public void setWeight2(int weight2) {
        this.weight2 = weight2;
    }

    public VTypeEntity getParent() {
        return parent;
    }

    public void setParent(VTypeEntity parent) {
        this.parent = parent;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public int getSizeInPage() {
        return sizeInPage;
    }

    public void setSizeInPage(int sizeInPage) {
        this.sizeInPage = sizeInPage;
    }

    public boolean checkFormatIdParams() {
        if (this.getFormatId() == null || this.getFormatId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCreateParams() {
        //所属的类型ID是不能为空
        if (this.getTypeId() == null || this.getTypeId().trim().equals("")) {
            return false;
        }
        //规格的状态必须符合规定
        if (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2) {
            return false;
        }
        //规格，数量，定价，邮费是数字，规格不能小于0，单位不能为空，数量不能为空，数量单位不能为空，定价不能小于0，邮费不能小于0
        if (this.getLabel() <= 0 || this.getMeta() == null || this.getMeta().trim().equals("")
                || this.getAmount() <= 0 || this.getAmountMeta() == null || this.getAmountMeta().trim().equals("")
                || this.getPrice() <= 0 || this.getPostage() < 0) {
            return false;
        }
        //打折状态必须符合规定
        if (this.getPricingStatus() != 1 && this.getPricingStatus() != 2) {
            return false;
        }
        //折扣不能小于0，现价不能小于0
        if (this.getPricingDiscount() <= 0 || this.getPricing() <= 0) {
            return false;
        }
        //折扣开始时间如果有，就必须大于一个固定时间（系统初始上线时间）
        try {
            if (this.getPricingStart() > 0 && this.getPricingStart() - DateTime.getFixedTimestamp() < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        //折扣结束时间如果有，就必须大于折扣开始时间
        if (this.getPricingEnd() > 0 && this.getPricingEnd() - this.getPricingStart() < 0) {
            return false;
        }

        //包邮状态必须符合规定
        if (this.getExpressStatus() != 1 && this.getExpressStatus() != 2) {
            return false;
        }
        //包邮数量不能小于0
        if (this.getExpressCount() <= 0) {
            return false;
        }
        //包邮开始时间如果有，就必须大于一个固定时间（系统初始上线时间）
        try {
            if (this.getExpressStart() > 0 && this.getExpressStart() - DateTime.getFixedTimestamp() < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        //折扣结束时间如果有，就必须大于折扣开始时间
        if (this.getExpressEnd() > 0 && this.getExpressEnd() - this.getExpressStart() < 0) {
            return false;
        }

        return true;
    }

    public boolean checkUpdateParams() {
        if (!checkCreateParams()) {
            return false;
        }
        if (this.getFormatId() == null && this.getFormatId().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean checkMarkParams() {
        if (this.getFormatId() == null && this.getFormatId().trim().equals("")) {
            return false;
        }
        if (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2) {
            return false;
        }
        return true;
    }

    public boolean checkSwapParams() {
        if (this.getFormatId1() == null || this.getFormatId1().trim().equals("")
                || this.getFormatId2() == null || this.getFormatId2().trim().equals("")
                || this.getFormatId1().equals(this.getFormatId2())
                || this.getWeight1() == this.getWeight2()) {
            return false;
        } else {
            return true;
        }
    }
}
