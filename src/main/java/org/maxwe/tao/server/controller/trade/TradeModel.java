package org.maxwe.tao.server.controller.trade;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-10 10:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TradeModel extends TokenModel {
    private int targetId;
    private int type;
    private String actCode;
    private int codeNum;

    public TradeModel() {
        super();
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "TradeModel{" +
                ", type=" + type +
                ", actCode='" + actCode + '\'' +
                ", codeNum=" + codeNum +
                '}';
    }

    @JSONField(serialize=false)
    public boolean isParamsOk() {
        if (this.getType() != 1 && this.getType() != 2) {
            return false;
        }

        if (this.getType() == 1 && this.getCodeNum() != 1) {
            return false;
        }

        if (this.getType() == 2 && this.getTargetId() == 0){
            return false;
        }

        if (this.getType() == 2 && this.getCodeNum() < 1){
            return false;
        }

        if (this.getType() == 2 && this.getCodeNum() > 99999){
            return false;
        }

        return super.isTokenParamsOk();
    }
}
