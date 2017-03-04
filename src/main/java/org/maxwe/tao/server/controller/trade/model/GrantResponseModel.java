package org.maxwe.tao.server.controller.trade.model;

/**
 * Created by Pengwei Ding on 2017-03-04 11:43.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成单个授权码的相应模型
 * 给普通用户使用，此时仅仅是生成码的相应，而不是用户真正使用
 */
public class GrantResponseModel extends GrantRequestModel {
    private String actCode;
    private int codeNum;
    private float price;
    private float codeDeal;

    public GrantResponseModel() {
        super();
    }

    public GrantResponseModel(String actCode,int codeNum ,float price) {
        super();
        this.actCode = actCode;
        this.codeNum = codeNum;
        this.price = price;
        this.codeDeal = this.price * this.codeNum;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCodeDeal() {
        return codeDeal;
    }

    public void setCodeDeal(float codeDeal) {
        this.codeDeal = codeDeal;
    }
}
