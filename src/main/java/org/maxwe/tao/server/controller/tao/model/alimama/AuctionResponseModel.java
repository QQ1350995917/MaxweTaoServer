package org.maxwe.tao.server.controller.tao.model.alimama;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.tao.alimama.convert.AliConvertEntity;

/**
 * Created by Pengwei Ding on 2017-03-08 20:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成优惠券的响应模型
 */
public class AuctionResponseModel extends ResponseModel<AuctionRequestModel> {
    private AliConvertEntity auction;

    public AliConvertEntity getAuction() {
        return auction;
    }

    public void setAuction(AliConvertEntity auction) {
        this.auction = auction;
    }

    public AuctionResponseModel() {
        super();
    }

    public AuctionResponseModel(AuctionRequestModel requestModel) {
        super(requestModel);
    }
}
