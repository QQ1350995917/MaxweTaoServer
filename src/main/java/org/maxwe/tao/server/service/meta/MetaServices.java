package org.maxwe.tao.server.service.meta;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-11 14:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MetaServices implements IMetaServices {

    @Override
    public LinkedList<UnitEntity> retrieves() {
        LinkedList<UnitEntity> unitEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM meta_unit ORDER BY queue");
        for (Record record : records) {
            unitEntities
                .add(JSON.parseObject(JSON.toJSONString(record.getColumns()), UnitEntity.class));
        }
        return unitEntities;
    }

    @Override
    public LinkedList<SpecialLinkEntity> retrieveSpecialLinks(int pageIndex, int pageSize) {
        List<Record> historyRecords = Db.find("SELECT * FROM links ORDER BY createTime DESC limit ? , ?",
             pageIndex * pageSize, pageSize);
        LinkedList<SpecialLinkEntity> specialLInkEntities = new LinkedList<>();
        for (Record agentRecord : historyRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            SpecialLinkEntity specialLInkEntity = JSON.parseObject(JSON.toJSONString(accountMap), SpecialLinkEntity.class);
            specialLInkEntities.add(specialLInkEntity);
        }
        return specialLInkEntities;
    }

    @Override
    public int specialLinksCount() {
        Record first = Db.findFirst("SELECT COUNT(id) AS counter FROM links");
        if (first == null) {
            return 0;
        } else {
            return new Long(first.getLong("counter")).intValue();
        }
    }

    @Override
    public SpecialLinkEntity createSpecialLink(SpecialLinkEntity specialLInkEntity) {
        Record linkRecord = new Record()
            .set("id", specialLInkEntity.getId())
            .set("url", specialLInkEntity.getUrl());
        boolean isSave = Db.save("links", linkRecord);
        if (isSave){
            return specialLInkEntity;
        }else{
            return null;
        }
    }
}
