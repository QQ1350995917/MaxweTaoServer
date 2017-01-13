package org.maxwe.tao.server.service.history;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-01-10 10:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class HistoryServices implements IHistoryServices {


    @Override
    public HistoryEntity updateToId(HistoryEntity historyEntity) {
        int count = Db.update("UPDATE history SET toId = ? WHERE id = ? ",
                historyEntity.getToId(), historyEntity.getId());
        if (count == 1) {
            return historyEntity;
        } else {
            return null;
        }
    }

    @Override
    public HistoryEntity retrieveByActCode(String actCode) {
        List<Record> records = Db.find("SELECT * FROM history WHERE actCode = ? ", actCode);
        if (records != null && records.size() >= 1 && records.get(0) != null) {
            Map<String, Object> accountMap = records.get(0).getColumns();
            return JSON.parseObject(JSON.toJSONString(accountMap), HistoryEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<HistoryEntity> retrieveByFromId(String fromId, int pageIndex, int pageSize) {
        List<Record> historyRecords = Db.find("SELECT * FROM history WHERE fromId = ? ORDER BY createTime DESC limit ? , ?",
                fromId, pageIndex * pageSize, pageSize);
        LinkedList<HistoryEntity> historyEntities = new LinkedList<>();
        for (Record agentRecord : historyRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            HistoryEntity historyEntity = JSON.parseObject(JSON.toJSONString(accountMap), HistoryEntity.class);
            historyEntities.add(historyEntity);
        }
        return historyEntities;
    }

    @Override
    public LinkedList<HistoryEntity> retrieveByTime(String fromId, long startTime, long endTime, int pageIndex, int pageSize) {
        List<Record> historyRecords = Db.find("SELECT * FROM history WHERE fromId = ? AND createTime >= ? AND createTime <= ? ORDER BY createTime DESC limit ? , ?",
                fromId, startTime, endTime, pageIndex * pageSize, pageSize);
        LinkedList<HistoryEntity> historyEntities = new LinkedList<>();
        for (Record agentRecord : historyRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            HistoryEntity historyEntity = JSON.parseObject(JSON.toJSONString(accountMap), HistoryEntity.class);
            historyEntities.add(historyEntity);
        }
        return historyEntities;
    }

    @Override
    public int countByFromId(String fromId) {
        Record first = Db.findFirst("SELECT COUNT(id) AS counter FROM history WHERE fromId = ?", fromId);
        if (first == null) {
            return 0;
        } else {
            return first.getInt("counter");
        }
    }
}
