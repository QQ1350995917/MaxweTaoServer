package org.maxwe.tao.server.controller.account.agent.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-01-10 11:21.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FindModel extends SessionModel {
    private String targetMark;

    public FindModel() {
        super();
    }

    public String getTargetMark() {
        return targetMark;
    }

    public void setTargetMark(String targetMark) {
        this.targetMark = targetMark;
    }

    @Override
    public String toString() {
        return super.toString() + "FindModel{" +
                "targetMark='" + targetMark + '\'' +
                '}';
    }

    @Override
    public boolean isParamsOk() {
        if (StringUtils.isEmpty(this.getTargetMark())){
            return false;
        }
        return super.isParamsOk();
    }
}
