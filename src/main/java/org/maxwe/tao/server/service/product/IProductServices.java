package org.maxwe.tao.server.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品管数据服务层
 */
public interface IProductServices {

    /**
     * 后台接口
     * 读取产品树集合
     * @return success 数据集合 fail null
     */
    LinkedList<SeriesEntity> mRetrieves();



}
