package org.maxwe.tao.server.service.tao.mami;

/**
 * Created by Pengwei Ding on 2017-02-23 16:51.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsTransEntity {
    private URLTransEntity convert;
    private GoodsEntity goodsEntity;

    public GoodsTransEntity() {
        super();
    }

    public GoodsTransEntity(URLTransEntity convert,GoodsEntity goodsEntity) {
        this.convert = convert;
        this.goodsEntity = goodsEntity;
    }

    public URLTransEntity getConvert() {
        return convert;
    }

    public void setConvert(URLTransEntity convert) {
        this.convert = convert;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }
}
