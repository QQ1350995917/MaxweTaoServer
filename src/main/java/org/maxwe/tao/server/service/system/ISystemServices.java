package org.maxwe.tao.server.service.system;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-16 18:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISystemServices {

    /**
     * 获取当前系统状态
     * @return
     */
    SystemEntity getSystemStatus();

    /**
     * 数据库备份
     * @param filePath
     * @param name
     * @param password
     * @param db
     * @throws Exception
     */
    void backup(String filePath, String name, String password, String db) throws Exception;

    /**
     * 数据库恢复
     * @param filePath
     * @param name
     * @param password
     * @param db
     * @throws Exception
     */
    void recover(String filePath, String name, String password, String db) throws Exception;

    /**
     * 创建新的备份到数据库进行记录
     * @param backupEntity
     * @return
     */
    BackupEntity createBackup(BackupEntity backupEntity);

    /**
     *
     * @param id
     * @return
     */
    BackupEntity retrieveById(String id);

    /**
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    LinkedList<BackupEntity> retrieveAll(int pageIndex,int pageSize);

}
