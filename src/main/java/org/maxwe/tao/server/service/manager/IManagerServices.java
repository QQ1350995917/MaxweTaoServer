package org.maxwe.tao.server.service.manager;

import org.maxwe.tao.server.service.menu.MenuEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 10:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员数据服务层
 * 由流程控制层调用该层接口，返回结果集
 */
public interface IManagerServices {


    /**
     * 管理员接口
     * 管理员登录
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity mLogin(ManagerEntity managerEntity);

    /**
     * 管理员接口
     * 管理员查询信息
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity mRetrieveById(ManagerEntity managerEntity);

    /**
     * 管理员接口
     * 管理员更新用户名和密码
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity mUpdate(ManagerEntity managerEntity);

    /**
     * 系统管理员接口
     *
     * @return success 管理员对象集合 fail null
     */
    LinkedList<ManagerEntity> MRetrieves();

    /**
     * 系统管理员接口
     * 系统管理员添加管理员检测用户名是否已经存在
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    boolean MExist(ManagerEntity managerEntity);

    /**
     * 系统管理员接口
     * 创建一个新的管理员
     *
     * @param managerEntity 管理员对象
     * @param menuEntities  管理的菜单对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity MCreate(ManagerEntity managerEntity, LinkedList<? extends MenuEntity> menuEntities);

    /**
     * 系统管理员接口
     * 更新管理员接口
     *
     * @param managerEntity 管理员对象
     * @param menuEntities  管理的菜单对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity MUpdate(ManagerEntity managerEntity, LinkedList<? extends MenuEntity> menuEntities);

    /**
     * 系统管理员接口
     * 禁用管理员
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity MBlock(ManagerEntity managerEntity);

    /**
     * 系统管理员接口
     * 启用管理员
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity MUnBlock(ManagerEntity managerEntity);

    /**
     * 系统管理员接口
     * 删除管理员
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity MDelete(ManagerEntity managerEntity);
}
