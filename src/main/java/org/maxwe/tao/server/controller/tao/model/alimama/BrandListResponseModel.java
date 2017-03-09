package org.maxwe.tao.server.controller.tao.model.alimama;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.tao.alimama.brand.GuideEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-08 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广以及其下的推广位列表响应模型
 */
public class BrandListResponseModel extends ResponseModel<BrandListRequestModel> {
    private List<GuideEntity> brands;

    public BrandListResponseModel() {
        super();
    }

    public BrandListResponseModel(BrandListRequestModel requestModel) {
        super(requestModel);
    }

    public List<GuideEntity> getBrands() {
        return brands;
    }

    public void setBrands(List<GuideEntity> brands) {
        this.brands = brands;
    }


}
