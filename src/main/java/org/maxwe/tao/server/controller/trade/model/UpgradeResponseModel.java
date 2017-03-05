package org.maxwe.tao.server.controller.trade.model;

import org.maxwe.tao.server.controller.mate.model.MateModel;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-05 10:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 提升代理级别的响应模型
 */
public class UpgradeResponseModel implements Serializable {
    private MateModel branch;//分支代理
    private int codeNum;//交易码量

    public UpgradeResponseModel() {
        super();
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }
}
