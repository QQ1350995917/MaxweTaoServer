package org.maxwe.tao.server.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFormatServices {

    /**
     * 用户接口
     * 读取类型下的规格
     *
     * @param typeEntity 最少包含类型ID的类型数据对象
     * @return success 规格数据集 fail null
     */
    LinkedList<FormatEntity> retrievesInType(TypeEntity typeEntity);

    /**
     * 用户接口
     * 通过规格的ID精确读取规格信息
     *
     * @param formatId 规格ID
     * @return success 规格数据 fail null
     */
    FormatEntity retrieveById(String formatId);

    /**
     * 用户接口（读取推荐）
     * 根据权重读取规格信息
     *
     * @param min 下边界权重（包含）
     * @param max 上边界权重（不包含）
     * @return success 规格数据集 fail null
     */
    LinkedList<FormatEntity> retrievesByWeight(int min, int max);

    /**
     * 管理员接口
     * 获取总的数据数量
     *
     * @return
     */
    int mCount();

    /**
     * 管理员接口
     * 判断该规格是否已经存在
     *
     * @param formatEntity 规格对象
     * @return true exist false not exist
     */
    boolean mExist(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 在类型下创建一个新的规格
     *
     * @param formatEntity 规格数据
     * @return success 类型数据 fail null
     */
    FormatEntity mCreate(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 全量更新规格
     *
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mUpdate(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 禁用规格
     *
     * @param formatEntity 规格对象
     * @return success 规格数据 fail null
     */
    FormatEntity mBlock(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 启用规格
     *
     * @param formatEntity 规格对象
     * @return success 规格数据 fail null
     */
    FormatEntity mUnBlock(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 删除规格
     *
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mDelete(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 使一个产品规格数据设置为被推荐
     * 推荐规则：
     * 推荐位共12个，权重为-12 ~ -1，数字越小，权重越大
     * 设置推荐位时候查询目前推荐位数量，小于12则各个推荐数字-1，当前目标设置为-1，大于12则-1变为0，当前目标设置为-1
     *
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mKingWeight(FormatEntity formatEntity);

    /**
     * 管理员接口
     * 在产品推荐中交换位置
     *
     * @param formatEntity1 规格数据
     * @param formatEntity2 规格数据
     * @return success 交换后的规格数据 fail null
     */
    FormatEntity[] mSwapWeight(FormatEntity formatEntity1, FormatEntity formatEntity2);

    /**
     * 管理员接口（用于推荐设置）
     * 根据权重顺序读取
     *
     * @param index
     * @param counter
     * @return success 规格数据 fail null
     */
    LinkedList<FormatEntity> mRetrieveByWeight(int index, int counter);

    /**
     * 管理员接口
     * 读取类型下的规格
     *
     * @param typeEntity 最少包含类型ID的类型数据对象
     * @return success 规格数据集 fail null
     */
    LinkedList<FormatEntity> mRetrievesInType(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 通过规格的ID精确读取规格信息
     *
     * @param formatId 规格ID
     * @return success 规格数据 fail null
     */
    FormatEntity mRetrieveById(String formatId);


}
