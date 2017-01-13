package org.maxwe.tao.server.controller.trade;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-10 10:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TradeModel extends SessionModel {
    private String targetMark;
    private int type;
    private String actCode;
    private String levelId;
    private int codeNum;

    public TradeModel() {
        super();
    }

    public String getTargetMark() {
        return targetMark;
    }

    public void setTargetMark(String targetMark) {
        this.targetMark = targetMark;
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

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
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
                "targetMark='" + targetMark + '\'' +
                ", type=" + type +
                ", actCode='" + actCode + '\'' +
                ", levelId='" + levelId + '\'' +
                ", codeNum=" + codeNum +
                '}';
    }

    @Override
    @JSONField(serialize=false)
    public boolean isParamsOk() {
        if (this.getType() != 1 && this.getType() != 2) {
            return false;
        }

        if (this.getType() == 1 && this.getCodeNum() != 1) {
            return false;
        }

        if (this.getType() == 2 && StringUtils.isEmpty(this.getLevelId())) {
            return false;
        }

        if (this.getType() == 2 && StringUtils.isEmpty(this.getTargetMark())){
            return false;
        }

        if (this.getType() == 2 && this.getCodeNum() < 1){
            return false;
        }

        if (this.getType() == 2 && this.getCodeNum() > 99999){
            return false;
        }

        return super.isParamsOk();
    }
}
