package org.maxwe.tao.server.service.history;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-01-09 15:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IHistoryServices {

//    HistoryEntity updateToId(HistoryEntity historyEntity);

    HistoryEntity retrieveByActCode(String actCode);

    LinkedList<HistoryEntity> retrieveByFromId(int fromId,int pageIndex,int pageSize);

    LinkedList<HistoryEntity> retrieveByTime(String fromId,long startTime,long endTime,int pageIndex,int pageSize);

    int countByFromId(String fromId);

    int countActCodeInFromIdInTime(long startLine,long endLine,List<Integer> fromIds);
}
