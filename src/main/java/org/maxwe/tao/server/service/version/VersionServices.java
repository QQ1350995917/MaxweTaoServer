package org.maxwe.tao.server.service.version;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-01-06 18:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VersionServices implements IVersionServices {

    @Override
    public List<VersionEntity> reversion() {
        List<Record> records = Db.find("SELECT * FROM version");
        LinkedList<VersionEntity> versionEntities = new LinkedList<>();
        for (Record agentRecord : records) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            VersionEntity versionEntity = JSON.parseObject(JSON.toJSONString(accountMap), VersionEntity.class);
            versionEntities.add(versionEntity);
        }
        return versionEntities;
    }
}
