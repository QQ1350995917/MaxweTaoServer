package org.maxwe.tao.server.controller.tao.model.alimama;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponsePageEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-19 10:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * 以阿里妈妈模型作为标准
 * 构建淘妈咪系统内的商品响应统一模型
 */
public class GoodsResponseModel extends ResponseModel<GoodsRequestModel>{
    private long pageIndex;
    private long pageSize;
    private long counter;
    private List<AliResponsePageEntity> goodsEntities;

    public GoodsResponseModel() {
        super();
    }

    public GoodsResponseModel(GoodsRequestModel requestModel) {
        super(requestModel);
    }

    public GoodsResponseModel(long pageIndex, long pageSize, long counter, List<AliResponsePageEntity> goodsEntities) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.counter = counter;
        this.goodsEntities = goodsEntities;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public List<AliResponsePageEntity> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(List<AliResponsePageEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }
}
