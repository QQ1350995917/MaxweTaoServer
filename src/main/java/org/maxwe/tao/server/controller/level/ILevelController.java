package org.maxwe.tao.server.controller.level;

/**
 * Created by Pengwei Ding on 2017-01-14 10:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public interface ILevelController {

    /**
     * 创建一个等级
     */
    void create();

    /**
     * 罗列所有历史等级
     */
    void levels();

    /**
     * 罗列所有顶级等级
     */
    void tops();

    /**
     * 根据码量定级别
     */
    void score();

}
