package org.maxwe.tao.server.service.manager;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 10:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员数据服务层
 * 由流程控制层调用该层接口，返回结果集
 */
public interface IManagerServices {

    /**
     * 系统管理员接口
     * 系统管理员添加管理员检测用户名是否已经存在
     *
     * @param loginName 管理员登录名称
     * @return success 管理员对象 fail null
     */
    boolean exist(String loginName);

    /**
     * 系统管理员接口
     * 创建一个新的管理员
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity create(ManagerEntity managerEntity);

    /**
     * 管理员接口
     * 管理员更新用户名和密码
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity update(ManagerEntity managerEntity);
    ManagerEntity updatePassword(ManagerEntity managerEntity);

    ManagerEntity delete(ManagerEntity managerEntity);
    ManagerEntity block(ManagerEntity managerEntity);
    ManagerEntity UnBlock(ManagerEntity managerEntity);


    /**
     * 管理员接口
     * 管理员登录
     *
     * @return success 管理员对象 fail null
     */
    ManagerEntity retrieveByLogin(String loginName,String password);

    /**
     * 管理员接口
     * 管理员查询信息
     *
     * @param managerEntity 管理员对象
     * @return success 管理员对象 fail null
     */
    ManagerEntity retrieveById(ManagerEntity managerEntity);

    /**
     * 系统管理员接口
     *
     * @return success 管理员对象集合 fail null
     */
    LinkedList<ManagerEntity> retrieves(int pageIndex,int pageSize);

    /**
     * 系统管理员接口
     *
     * @return success 管理员对象集合 fail null
     */
    int retrievesSum();

}
