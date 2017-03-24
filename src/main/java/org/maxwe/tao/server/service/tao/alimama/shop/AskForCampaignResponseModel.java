package org.maxwe.tao.server.service.tao.alimama.shop;

import org.maxwe.tao.server.service.tao.alimama.common.AliResponseInfoEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-24 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 申请推广活动的响应模型
 */
public class AskForCampaignResponseModel {
    private List<CampaignEntity> data;
    private AliResponseInfoEntity info;
    private boolean ok;
    private String invalidKey;

    public AskForCampaignResponseModel() {
        super();
    }

    public List<CampaignEntity> getData() {
        return data;
    }

    public void setData(List<CampaignEntity> data) {
        this.data = data;
    }

    public AliResponseInfoEntity getInfo() {
        return info;
    }

    public void setInfo(AliResponseInfoEntity info) {
        this.info = info;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getInvalidKey() {
        return invalidKey;
    }

    public void setInvalidKey(String invalidKey) {
        this.invalidKey = invalidKey;
    }
}
