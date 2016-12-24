package org.maxwe.tao.server.service.poster;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IPosterServices {

    /**
     * 用户接口
     * 读取海报集合
     * @return fail null success 数据集合
     */
    LinkedList<PosterEntity> retrieves();

    /**
     * 管理员接口
     * 创建海报
     * @param posterEntity 目标海报对象
     * @return fail null success 目标海报对象
     */
    PosterEntity mCreate(PosterEntity posterEntity);

    /**
     * 管理员接口
     * 更新海报
     * @param posterEntity 目标海报对象
     * @return fail null success 目标海报对象
     */
    PosterEntity mUpdate(PosterEntity posterEntity);

    /**
     * 管理员接口
     * 禁用海报
     * @param posterEntity 目标海报对象
     * @return fail null success 目标海报对象
     */
    PosterEntity mBlock(PosterEntity posterEntity);

    /**
     * 管理员接口
     * 启用海报
     * @param posterEntity 目标海报对象
     * @return fail null success 目标海报对象
     */
    PosterEntity mUnBlock(PosterEntity posterEntity);

    /**
     * 管理员接口
     * 删除海报
     * @param posterEntity 目标海报对象
     * @return fail null success 目标海报对象
     */
    PosterEntity mDelete(PosterEntity posterEntity);

    /**
     * 管理员接口
     * 交换海报权重
     * @param posterEntity1 目标对象1
     * @param posterEntity2 目标对象2
     * @return fail null success 目标海报对象集合
     */
    PosterEntity[] mSwap(PosterEntity posterEntity1,PosterEntity posterEntity2);

    /**
     * 管理员接口
     * 读取海报集合
     * @return fail null success 目标海报对象集合
     */
    LinkedList<PosterEntity> mRetrieves();

}
