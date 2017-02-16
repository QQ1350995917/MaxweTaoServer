package org.maxwe.tao.server.service.version;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-01-06 18:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IVersionServices {

    /**
     * 读取四个最新版本的app
     * @return
     */
    List<VersionEntity> retrieveTopVersion();


    List<VersionEntity> retrieveAll(int pageIndex,int pageSize);
    int retrieveCounter();


    List<VersionEntity> reversion();

}
