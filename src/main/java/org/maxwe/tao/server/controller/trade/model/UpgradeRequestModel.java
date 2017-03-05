package org.maxwe.tao.server.controller.trade.model;

import org.maxwe.tao.server.controller.account.model.AuthenticateModel;
import org.maxwe.tao.server.controller.mate.model.MateModel;
import org.maxwe.tao.server.service.level.LevelEntity;

/**
 * Created by Pengwei Ding on 2017-03-05 10:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 提升代理级别的请求模型
 */
public class UpgradeRequestModel extends AuthenticateModel {
    private MateModel branch;//分支代理
    private LevelEntity upgradeToLevel;//要提升到的级别
    private int codeNum;//补码数量

    public UpgradeRequestModel() {
        super();
    }

    public MateModel getBranch() {
        return branch;
    }

    public void setBranch(MateModel branch) {
        this.branch = branch;
    }

    public LevelEntity getUpgradeToLevel() {
        return upgradeToLevel;
    }

    public void setUpgradeToLevel(LevelEntity upgradeToLevel) {
        this.upgradeToLevel = upgradeToLevel;
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    public boolean isUpgradeRequestParamsOk() {
        if (this.getBranch().getAgent() == null) {
            return false;
        }
        if (this.getBranch().getAgent().getId() < 0) {
            return false;
        }
        if (this.getBranch().getLevel() == null) {
            return false;
        }
        if (this.getBranch().getLevel().getLevel() < 0) {
            return false;
        }
        if (this.getUpgradeToLevel() == null) {
            return false;
        }
        return super.isAuthenticateParamsOk() && this.getCodeNum() > 0 && this.getCodeNum() < 10000;
    }
}
