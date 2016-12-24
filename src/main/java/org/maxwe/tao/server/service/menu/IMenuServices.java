package org.maxwe.tao.server.service.menu;

import org.maxwe.tao.server.service.manager.ManagerEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 14:33.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMenuServices {

    /**
     * 管理员接口
     * 通过管理员权限查询菜单
     * @param managerEntity 管理员对象
     * @return 管理员所拥有权限的菜单集合
     */
    LinkedList<MenuEntity> retrievesByManager(ManagerEntity managerEntity);

    /**
     * 超级管理员接口
     * 查询所有菜单
     * @param category 类别
     * @return 菜单集合
     */
    LinkedList<MenuEntity> mRetrievesByAdmin(int category);

}
