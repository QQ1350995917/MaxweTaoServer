package org.maxwe.tao.server.controller.system;

import org.maxwe.tao.server.service.system.SystemEntity;

/**
 * Created by Pengwei Ding on 2016-11-13 17:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VSystemEntity extends SystemEntity {

    public VSystemEntity() {
        super();
    }

    public VSystemEntity(SystemEntity systemEntity) {
        this.setOsName(systemEntity.getOsName());
        this.setOsVersion(systemEntity.getOsVersion());
        this.setOsArch(systemEntity.getOsArch());
        this.setOsIP(systemEntity.getOsIP());
        this.setOsMAC(systemEntity.getOsMAC());
        this.setOsDate(systemEntity.getOsDate());
        this.setJavaVersion(systemEntity.getJavaVersion());
        this.setServerName(systemEntity.getServerName());
        this.setServerVersion(systemEntity.getServerVersion());
        this.setDbName(systemEntity.getDbName());
        this.setDbVersion(systemEntity.getDbVersion());
        this.setCPUS(systemEntity.getCPUS());
        this.setCPURatio(systemEntity.getCPURatio());
        this.setMemoryTotal(systemEntity.getMemoryTotal());
        this.setMemoryUsed(systemEntity.getMemoryUsed());
        this.setMemoryFree(systemEntity.getMemoryFree());
    }
}
