package org.maxwe.tao.server.controller.trade.model;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-04 11:43.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成单个授权码的相应模型
 * 给普通用户使用，此时仅仅是生成码的相应，而不是用户真正使用
 */
public class GrantResponseModel extends ResponseModel {
    private String actCode;
    private int codeNum;
    private float price;//成交价格
    private float codeDeal;//成交金额

    public GrantResponseModel() {
        super();
    }

    public GrantResponseModel(TokenModel requestModel) {
        super(requestModel);
    }

    public GrantResponseModel(TokenModel requestModel, String actCode, int codeNum) {
        super(requestModel);
        this.actCode = actCode;
        this.codeNum = codeNum;
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
