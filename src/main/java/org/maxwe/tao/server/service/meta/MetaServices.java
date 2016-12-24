package org.maxwe.tao.server.service.meta;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

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
            unitEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), UnitEntity.class));
        }
        return unitEntities;
    }
}
