package org.maxwe.tao.server.controller.tao.model.alimama;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.tao.alimama.brand.GuideEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-08 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 新建一个导购推广以及其下的一个推广位响应模型
 */
public class BrandCreateResponseModel extends ResponseModel<BrandCreateRequestModel> {
    private List<GuideEntity> brands;

    public BrandCreateResponseModel() {
        super();
    }

    public BrandCreateResponseModel(BrandCreateRequestModel requestModel) {
        super(requestModel);
    }

    public List<GuideEntity> getBrands() {
        return brands;
    }

    public void setBrands(List<GuideEntity> brands) {
        this.brands = brands;
    }
}
