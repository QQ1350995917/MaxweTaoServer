package org.maxwe.tao.server.service.file;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-11-02 10:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFileServices {

    LinkedList<FFile> retrieveByTrunkId(String trunkId);

    FFile mCreate(FFile fFile);

    FFile mUpdate(FFile fFile);

    FFile mDelete(FFile fFile);

    FFile mBindTrunk(FFile fFile);

    LinkedList<FFile> mRetrieveByTrunkId(String trunkId);

}
