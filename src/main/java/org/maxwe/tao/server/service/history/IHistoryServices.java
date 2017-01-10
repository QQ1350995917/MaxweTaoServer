package org.maxwe.tao.server.service.history;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 15:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IHistoryServices {

    HistoryEntity updateToId(HistoryEntity historyEntity);

    HistoryEntity retrieveByActCode(String actCode);

    LinkedList<HistoryEntity> retrieveByFromId(String fromId,int pageIndex,int pageSize);

    LinkedList<HistoryEntity> retrieveByTime(String fromId,long startTime,long endTime,int pageIndex,int pageSize);


}
