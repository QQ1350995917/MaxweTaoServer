package org.maxwe.tao.server.service.tao.jidi;

import org.maxwe.tao.server.service.tao.mami.GoodsEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-20 22:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 对接淘客基地api
 * 由于5000次数限制
 * 故采用策略
 * 每天定时从淘客基地获取数据进行缓存
 * 使用缓存数据对外提供服务
 */
public interface IJiDiGoodsServices {

    void init();

    LinkedList<GoodsEntity> getGoods(int pageIndex,int pageSize);
    LinkedList<GoodsEntity> getGoodsTop100(int pageIndex,int pageSize);
    LinkedList<GoodsEntity> getGoodsDaPai(int pageIndex,int pageSize);
    LinkedList<GoodsEntity> getGoodsMeiRiBiPai(int pageIndex,int pageSize);
}
