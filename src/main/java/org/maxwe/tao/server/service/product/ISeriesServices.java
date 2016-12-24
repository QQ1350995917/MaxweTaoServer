package org.maxwe.tao.server.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 11:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISeriesServices {

    /**
     * 用户接口
     * 读取所有的系列
     * @return fail null success 系列对象集合
     */
    LinkedList<SeriesEntity> retrieves();

    /**
     * 用户接口
     * 通过系列ID精确查找
     * @param seriesId 系列ID
     * @return success 数据对象 fail null
     */
    SeriesEntity retrieveById(String seriesId);

    /**
     * 管理员接口
     * 管理员创建的系列名是否已经存在
     * @param seriesName 系列名称
     * @return true 存在 false 不存在
     */
    boolean mExist(String seriesName);

    /**
     * 管理员接口
     * 创建一个系列
     * @param seriesEntity 待创建的数据对象
     * @return success 数据对象 fail null
     */
    SeriesEntity mCreate(SeriesEntity seriesEntity);

    /**
     * 管理员接口
     * 更新一个系列名称
     * @param seriesEntity 待更新的数据对象
     * @return success 数据对象 fail null
     */
    SeriesEntity mUpdate(SeriesEntity seriesEntity);

    /**
     * 管理员接口
     * 禁用一个系列
     * @param seriesEntity 待更新状态的数据对象，除了ID和status，可不包含其他字段
     * @return success 数据对象 fail null
     */
    SeriesEntity mBlock(SeriesEntity seriesEntity);

    /**
     * 管理员接口
     * 启用一个系列
     * @param seriesEntity 系列对象
     * @return success 数据对象 fail null
     */
    SeriesEntity mUnBlock(SeriesEntity seriesEntity);

    /**
     * 管理员接口
     * 删除一个系列
     * @param seriesEntity 系列对象
     * @return
     */
    SeriesEntity mDelete(SeriesEntity seriesEntity);

    /**
     * 管理员接口
     * 读取所有的系列
     * @return success 数据集合 fail null
     */
    LinkedList<SeriesEntity> mRetrieves();


}
