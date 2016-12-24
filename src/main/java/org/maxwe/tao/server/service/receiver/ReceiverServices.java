package org.maxwe.tao.server.service.receiver;

import org.maxwe.tao.server.service.user.AccountEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverServices implements IReceiverService {

    @Override
    public LinkedList<ReceiverEntity> retrieves(AccountEntity accountEntity) {
        return this.retrieves(accountEntity);
    }

    @Override
    public LinkedList<ReceiverEntity> retrieves(LinkedList<? extends AccountEntity> accountEntities) {
        String[] params = new String[accountEntities.size()];
        String in = "";
        int index = 0;
        for (AccountEntity accountEntity : accountEntities) {
            in = in + " ? ,";
            params[index] = accountEntity.getAccountId();
            index++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        LinkedList<ReceiverEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_receiver WHERE status > 1 AND accountId IN (" + in + ") ORDER BY createTime ASC", params);
        for (Record record : records) {
            Map<String, Object> receiverMap = record.getColumns();
            ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverMap), ReceiverEntity.class);
            result.add(receiverEntity);
        }
        return result;
    }

    @Override
    public ReceiverEntity retrieveById(String receiverId) {
        List<Record> receiverRecords = Db.find("SELECT * FROM user_receiver WHERE receiverId = ? ", receiverId);
        ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverRecords.get(0).getColumns()), ReceiverEntity.class);
        return receiverEntity;
    }

    @Override
    public ReceiverEntity create(AccountEntity accountEntity,ReceiverEntity receiverEntity) {
        Record record = new Record()
                .set("receiverId", receiverEntity.getReceiverId())
                .set("name", receiverEntity.getName())
                .set("phone0", receiverEntity.getPhone0())
                .set("phone1", receiverEntity.getPhone1())
                .set("province", receiverEntity.getProvince())
                .set("city", receiverEntity.getCity())
                .set("county", receiverEntity.getCounty())
                .set("town", receiverEntity.getTown())
                .set("village", receiverEntity.getVillage())
                .set("append", receiverEntity.getAppend())
                .set("status", receiverEntity.getStatus());
        if (accountEntity != null){
            record.set("accountId", accountEntity.getAccountId());
        }
        boolean save = Db.save("user_receiver", record);
        if (save) {
            return receiverEntity;
        } else {
            return null;
        }
    }

    @Override
    public ReceiverEntity updateById(LinkedList<? extends AccountEntity> accountEntities,ReceiverEntity receiverEntity) {
        Record record = new Record()
                .set("receiverId", receiverEntity.getReceiverId())
                .set("name", receiverEntity.getName())
                .set("phone0", receiverEntity.getPhone0())
                .set("phone1", receiverEntity.getPhone1())
                .set("province", receiverEntity.getProvince())
                .set("city", receiverEntity.getCity())
                .set("county", receiverEntity.getCounty())
                .set("town", receiverEntity.getTown())
                .set("village", receiverEntity.getVillage())
                .set("append", receiverEntity.getAppend());
        boolean update = Db.update("user_receiver", "receiverId", record);
        if (update) {
            return receiverEntity;
        } else {
            return null;
        }
    }

    @Override
    public ReceiverEntity deleteById(LinkedList<? extends AccountEntity> accountEntities,String receiverId) {
        ReceiverEntity receiverEntity = this.retrieveById(receiverId);
        int update = Db.update("UPDATE user_receiver SET status = -1 WHERE receiverId = ? ", receiverId);
        return update == 1 ? receiverEntity : null;
    }


    @Override
    public ReceiverEntity kingReceiverInUser(LinkedList<? extends AccountEntity> accountEntities, ReceiverEntity receiverEntity) {
        String[] params = new String[accountEntities.size()];
        String in = "";
        int index = 0;
        for (AccountEntity accountEntity : accountEntities) {
            in = in + " ? ,";
            params[index] = accountEntity.getAccountId();
            index ++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        final String IN = in;
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update1 = Db.update("UPDATE user_receiver SET status = 2 WHERE accountId IN (" + IN + ") AND status != -1", params);
                int update2 = Db.update("UPDATE user_receiver SET status = 3 WHERE receiverId = ? ", receiverEntity.getReceiverId());
                return update1 > 0 && update2 == 1;
            }
        });

        if (succeed) {
            return receiverEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<ReceiverEntity> mRetrieves(AccountEntity accountEntity) {
        LinkedList<AccountEntity> accountEntities = new LinkedList<>();
        accountEntities.add(accountEntity);
        return this.mRetrieves(accountEntities);
    }

    @Override
    public LinkedList<ReceiverEntity> mRetrieves(LinkedList<AccountEntity> accountEntities) {
        String in = "";
        String[] accountIds = new String[accountEntities.size()];
        for (int index=0;index<accountEntities.size();index++) {
            in = in + " ? ,";
            accountIds[index] = accountEntities.get(index).getAccountId();
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        LinkedList<ReceiverEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_receiver WHERE accountId IN (" + in + ") AND status > 1", accountIds);
        for (Record record : records) {
            Map<String, Object> receiverMap = record.getColumns();
            ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverMap), ReceiverEntity.class);
            result.add(receiverEntity);
        }
        return result;
    }
}
