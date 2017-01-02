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
        Db.update("DELETE FROM cs WHERE mappingId = ? AND type = ?", csEntity.getMappingId(), csEntity.getType());
        Record accountRecord = new Record()
                .set("csId", csEntity.getCsId())
                .set("token", csEntity.getToken())
                .set("mappingId", csEntity.getMappingId())
                .set("type", csEntity.getType());
        boolean account = Db.save("cs", accountRecord);
        if (account) {
            return csEntity;
        } else {
            return null;
        }
    }

    @Override
    public CSEntity updateToken(CSEntity csEntity) {
        int count = Db.update("UPDATE cs SET token = ? WHERE csId = ? ", csEntity.getToken(), csEntity.getCsId());
        if (count == 1) {
            return csEntity;
        } else {
            return null;
        }
    }

    @Override
    public CSEntity retrieveByToken(String token) {
        List<Record> accountRecords = Db.find("SELECT * FROM cs WHERE token = ? ", token);
        if (accountRecords.get(0) == null) {
            return null;
        }
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        CSEntity csEntity = JSON.parseObject(JSON.toJSONString(accountMap), CSEntity.class);
        return csEntity;
    }

    @Override
    public boolean deleteByToken(String token) {
        int update = Db.update("DELETE FROM cs WHERE token = ? ", token);
        return update >= 0;
    }
}
