package org.maxwe.tao.server.service.user;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-16 10:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AccountServices implements IAccountServices {

    @Override
    public boolean existAccount(String identity) {
        List<Record> records = Db.find("SELECT * FROM user_account WHERE identity = ? ", identity);
        if (records.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AccountEntity create(AccountEntity accountEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Record userRecord = new Record()
                        .set("userId", accountEntity.getUserId())
                        .set("status", 2);
                Record userAccountRecord = new Record()
                        .set("accountId", accountEntity.getAccountId())
                        .set("identity", accountEntity.getIdentity())
                        .set("password", accountEntity.getIdentity())
                        .set("userId", accountEntity.getUserId());
                boolean user = Db.save("user", userRecord);
                boolean account = Db.save("user_account", userAccountRecord);
                return user && account;
            }
        });

        if (succeed) {
            return accountEntity;
        } else {
            return null;
        }
    }

    @Override
    public AccountEntity retrieve(AccountEntity accountEntity) {
        List<Record> records = Db.find("SELECT * FROM user_account WHERE identity = ? AND password = ? ", accountEntity.getIdentity(), accountEntity.getPassword());
        if (records.size() > 0) {
            Map<String, Object> accountMap = records.get(0).getColumns();
            return JSON.parseObject(JSON.toJSONString(accountMap), AccountEntity.class);
        } else {
            return null;
        }
    }


    @Override
    public AccountEntity updatePassword(AccountEntity accountEntity) {
        return null;
    }

    @Override
    public AccountEntity update(AccountEntity accountEntity) {
        return null;
    }

    @Override
    public AccountEntity portrait(AccountEntity accountEntity) {
        return null;
    }

    @Override
    public AccountEntity updateIdentity(AccountEntity accountEntity) {
        return null;
    }

    @Override
    public UserEntity bind(AccountEntity targetAccountEntity, AccountEntity currentAccountEntity) {
        return null;
    }

    @Override
    public UserEntity unBind(AccountEntity targetAccountEntity, AccountEntity currentAccountEntity) {
        return null;
    }


    @Override
    public LinkedList<AccountEntity> retrieveByUserId(String userId) {
        LinkedList<AccountEntity> result = new LinkedList<>();
        List<Record> accountRecords = Db.find("SELECT * FROM user_account WHERE userId = ? ORDER BY source ASC ", userId);
        for (Record accountRecord : accountRecords) {
            Map<String, Object> accountMap = accountRecord.getColumns();
            AccountEntity accountEntity = JSON.parseObject(JSON.toJSONString(accountMap), AccountEntity.class);
            result.add(accountEntity);
        }
        return result;
    }

    @Override
    public int mCount() {
        List<Record> records = Db.find("SELECT COUNT(userId) AS counter FROM user WHERE status != -1 ");
        if (records != null && records.size() == 1) {
            return Integer.parseInt(records.get(0).getColumns().get("counter").toString());
        } else {
            return 0;
        }
    }

    @Override
    public LinkedList<UserEntity> mRetrieveUsers(int page, int counter) {
        LinkedList<UserEntity> userEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user WHERE status != -1 order by createTime DESC limit ? , ?", page * counter, counter);
        for (Record record : records) {
            userEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), UserEntity.class));
        }
        return userEntities;
    }

    @Override
    public LinkedList<UserEntity> mQueryUsers(String key, int page, int counter) {
        return null;
    }

    @Override
    public UserEntity mBlock(UserEntity userEntity) {
        int count = Db.update("UPDATE user SET status = 1 WHERE userId = ? ", userEntity.getUserId());
        if (count == 1) {
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public UserEntity mUnBlock(UserEntity userEntity) {
        int count = Db.update("UPDATE user SET status = 2 WHERE userId = ? ", userEntity.getUserId());
        if (count == 1) {
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<AccountEntity> mRetrieveByUserId(String userId) {
        LinkedList<AccountEntity> accountEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_account WHERE userId = ? ", userId);
        for (Record record : records) {
            AccountEntity accountEntity = JSON.parseObject(JSON.toJSONString(record.getColumns()), AccountEntity.class);
            accountEntities.add(accountEntity);
        }
        return accountEntities;
    }

    @Override
    public UserEntity retrieveUserByAccountId(String account) {
        List<Record> accountRecords = Db.find("SELECT * FROM user_account WHERE accountId = ? ", account);
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        List<Record> userRecords = Db.find("SELECT * FROM user WHERE userId = ? ", accountMap.get("userId").toString());
        Map<String, Object> userMap = userRecords.get(0).getColumns();
        UserEntity userEntity = JSON.parseObject(JSON.toJSONString(userMap), UserEntity.class);
        return userEntity;
    }


}
