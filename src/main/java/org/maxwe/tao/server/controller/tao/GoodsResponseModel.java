package org.maxwe.tao.server.controller.tao;

import org.maxwe.tao.server.service.tao.mami.GoodsEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-19 10:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class GoodsResponseModel {
    private long pageIndex;
    private long pageSize;
    private long counter;
    private LinkedList<GoodsEntity> goodsEntities;

    public GoodsResponseModel() {
        super();
    }

    public GoodsResponseModel(long pageIndex, long pageSize, long counter, LinkedList<GoodsEntity> goodsEntities) {
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

    public LinkedList<GoodsEntity> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(LinkedList<GoodsEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }
}
