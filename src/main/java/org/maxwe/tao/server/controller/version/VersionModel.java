package org.maxwe.tao.server.controller.version;

import org.maxwe.tao.server.service.version.VersionEntity;

/**
 * Created by Pengwei Ding on 2017-02-21 16:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class VersionModel extends VersionEntity {
    private int oldVersionCode;

    public VersionModel() {
        super();
    }

    public int getOldVersionCode() {
        return oldVersionCode;
    }

    public void setOldVersionCode(int oldVersionCode) {
        this.oldVersionCode = oldVersionCode;
    }

    public boolean isCreateParamsOk(){
        if (oldVersionCode >= this.getVersionCode()){
            return false;
        }
        return true;
    }


}
