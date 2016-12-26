package org.maxwe.tao.server.service.user;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-12-25 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CSServices implements ICSServices {

    @Override
    public boolean existByToken(String token) {
        List<Record> records = Db.find("SELECT * FROM cs WHERE token = ? ", token);
        if (records.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CSEntity create(CSEntity csEntity) {
        Record accountRecord = new Record()
                .set("csId", csEntity.getCsId())
                .set("token", csEntity.getToken())
                .set("mappingId", csEntity.getMappingId())
                .set("tableName", csEntity.getTableName());
        boolean account = Db.save("cs", accountRecord);
        if (account) {
            return csEntity;
        } else {
            return null;
        }
    }

    @Override
    public CSEntity retrieveByToken(String token) {
        List<Record> accountRecords = Db.find("SELECT * FROM cs WHERE token = ? ", token);
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        CSEntity csEntity = JSON.parseObject(JSON.toJSONString(accountMap), CSEntity.class);
        return csEntity;
    }

    @Override
    public boolean deleteByToken(String token) {
        int update = Db.update("DELETE FROM WHERE TOKEN = ", token);
        return update >= 0;
    }
}
