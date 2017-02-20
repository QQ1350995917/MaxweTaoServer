package org.maxwe.tao.server.service.system;

/**
 * Created by Pengwei Ding on 2017-02-16 18:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISystemServices {

    void backup(String filePath, String name, String password, String db) throws Exception;

    void recover(String filePath, String name, String password, String db) throws Exception;

}
