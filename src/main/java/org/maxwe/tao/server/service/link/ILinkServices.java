package org.maxwe.tao.server.service.link;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ILinkServices {

    /**
     * 用户接口
     * 读取顶级链接
     *
     * @return fail null success 数据集合
     */
    LinkedList<LinkEntity> retrieves();

    /**
     * 用户接口
     * 根据父链接读取
     *
     * @param pid 父链接ID
     * @return fail null success 数据集合
     */
    LinkedList<LinkEntity> retrievesByPid(String pid);

    /**
     * 管理员接口
     * 检测链接是否重复
     * 如果检测顶级链接则label不同 否则检测label和href
     *
     * @param linkEntity 检测目标对象
     * @return true exist false not exist
     */
    boolean mExist(LinkEntity linkEntity);

    /**
     * 管理员接口
     * 创建链接分类
     *
     * @param linkEntity 链接对象
     * @return fail null success 数据对象
     */
    LinkEntity mCreate(LinkEntity linkEntity);

    /**
     * 管理员接口
     * 创建链接
     *
     * @param linkEntity 链接对象
     * @return fail null success 数据对象
     */
    LinkEntity mUpdate(LinkEntity linkEntity);

    /**
     * 管理员接口
     * 禁用链接
     *
     * @param linkEntity 链接对象
     * @return fail null success 数据对象
     */
    LinkEntity mBlock(LinkEntity linkEntity);

    /**
     * 管理员接口
     * 启用链接
     *
     * @param linkEntity 链接对象
     * @return fail null success 数据对象
     */
    LinkEntity mUnBlock(LinkEntity linkEntity);

    /**
     * 管理员接口
     * 删除链接
     *
     * @param linkEntity 链接对象
     * @return fail null success 数据对象
     */
    LinkEntity mDelete(LinkEntity linkEntity);

    /**
     * 管理员接口
     * 交换链接分类的顺序
     *
     * @param linkEntity1
     * @param linkEntity2
     * @return
     */
    LinkEntity[] mSwap(LinkEntity linkEntity1, LinkEntity linkEntity2);

    /**
     * 管理员接口
     * 读取顶级链接
     *
     * @return fail null success 数据集合
     */
    LinkedList<LinkEntity> mRetrieves();

    /**
     * 管理员接口
     * 根据父链接读取
     *
     * @param pid 父链接ID
     * @return fail null success 数据集合
     */
    LinkedList<LinkEntity> mRetrievesByPid(String pid);



}
