package org.maxwe.tao.server.service.account.user;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.service.history.HistoryEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-01-09 14:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserServices implements IUserServices {

    @Override
    public UserEntity create(UserEntity userEntity) {
        Record agentRecord = new Record()
                .set("refId", userEntity.getRefId())
                .set("cellphone", userEntity.getCellphone())
                .set("password", userEntity.getPassword());
        boolean isSave = Db.save("user", agentRecord);
        if (isSave) {
            if (userEntity.getRefId() > 0) {
                Db.update("UPDATE user SET refNum = refNum + 1,refBalance = refBalance + ? WHERE id = ?",
                        ApplicationConfigure.REFERENCE_BALANCE, userEntity.getRefId());
            }
            return retrieveByCellphone(userEntity.getCellphone());
        } else {
            return null;
        }
    }

    @Override
    public UserEntity updatePassword(UserEntity userEntity) {
        int update = Db.update("UPDATE user SET password = ? WHERE id = ? ",
                userEntity.getPassword(), userEntity.getId());
        if (update == 1) {
            return userEntity;
        }
        return null;
    }

    @Override
    public boolean updateActiveStatus(UserEntity userEntity, HistoryEntity historyEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int updateUser = Db.update("UPDATE user SET actCode = ? ,actTime = ?  WHERE id = ? ",
                        userEntity.getActCode(), new Timestamp(System.currentTimeMillis()), userEntity.getId());
                int updateHistory = Db.update("UPDATE history SET toId = ? WHERE id = ?",
                        historyEntity.getToId(), historyEntity.getId());
                return updateUser == 1 && updateHistory == 1;
            }
        });
        return succeed;
    }


    @Override
    public boolean updateReason(UserEntity userEntity) {
        int update = Db.update("UPDATE user SET reason = ? WHERE id = ? ",
                userEntity.getReason(), userEntity.getId());
        if (update == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRhetoric(UserEntity userEntity) {
        int update = Db.update("UPDATE user SET rhetoric = ? WHERE id = ? ",
                userEntity.getRhetoric(), userEntity.getId());
        if (update == 1) {
            return true;
        }
        return false;
    }

    @Override
    public UserEntity retrieveById(int id) {
        List<Record> userRecords = Db.find("SELECT * FROM user WHERE id = ? ", id);
        if (userRecords != null && userRecords.size() > 0 && userRecords.get(0) != null) {
            Map<String, Object> accountMap = userRecords.get(0).getColumns();
            UserEntity userEntity = JSON.parseObject(JSON.toJSONString(accountMap), UserEntity.class);
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public UserEntity retrieveByCellphone(String cellphone) {
        List<Record> userRecords = Db.find("SELECT * FROM user WHERE cellphone = ? ", cellphone);
        if (userRecords != null && userRecords.size() > 0 && userRecords.get(0) != null) {
            Map<String, Object> accountMap = userRecords.get(0).getColumns();
            UserEntity userEntity = JSON.parseObject(JSON.toJSONString(accountMap), UserEntity.class);
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<UserEntity> retrieveByMaster(String masterId) {
        LinkedList<UserEntity> userEntities = new LinkedList<>();
        List<Record> userRecords = Db.find("SELECT * FROM user WHERE master = ? ", masterId);
        if (userRecords != null && userEntities.size() > 0 && userRecords.size() > 0) {
            for (Record userRecord : userRecords) {
                Map<String, Object> userMap = userRecord.getColumns();
                UserEntity userEntity = JSON.parseObject(JSON.toJSONString(userMap), UserEntity.class);
                userEntities.add(userEntity);
            }
        }
        return userEntities;
    }

    @Override
    public LinkedList<UserEntity> retrieveAll(int pageIndex, int pageSize) {
        List<Record> agentRecords = Db.find("SELECT * FROM user ORDER BY createTime DESC limit ? , ?", pageIndex * pageSize, pageSize);
        LinkedList<UserEntity> agentEntities = new LinkedList<>();
        for (Record agentRecord : agentRecords) {
            Map<String, Object> accountMap = agentRecord.getColumns();
            UserEntity userEntity = JSON.parseObject(JSON.toJSONString(accountMap), UserEntity.class);
            agentEntities.add(userEntity);
        }
        return agentEntities;
    }

    @Override
    public int retrieveAllSum() {
        return Db.find("SELECT * FROM user ").size();
    }


    @Override
    public int retrieveReferenceNumByMonth(int id, long start, long end) {
        Record record =
                Db.findFirst("SELECT COUNT(id) AS counter FROM user WHERE refId = ? " +
                                "AND createTime BETWEEN ? AND ?",
                        id,
                        DateTimeUtils.parseLongToFullTime(start),
                        DateTimeUtils.parseLongToFullTime(end));
        if (record == null) {
            return 0;
        } else {
            return new Long(record.getLong("counter")).intValue();
        }
    }
}
