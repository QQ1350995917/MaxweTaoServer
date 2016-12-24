package org.maxwe.tao.server.service.cart;

import org.maxwe.tao.server.service.order.OrderEntity;
import org.maxwe.tao.server.service.user.AccountEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartServices implements ICartServices {

    @Override
    public CartEntity exist(CartEntity cartEntity) {
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE status = 1 AND accountId = ? AND formatId = ? ORDER BY updateTime DESC", cartEntity.getAccountId(), cartEntity.getFormatId());
        if (records == null || records.size() != 0) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public CartEntity create(CartEntity cartEntity) {
        Record record = new Record()
                .set("mappingId", cartEntity.getMappingId())
                .set("formatId", cartEntity.getFormatId())
                .set("amount", cartEntity.getAmount())
                .set("orderId", cartEntity.getOrderId())
                .set("accountId", cartEntity.getAccountId());
        boolean save = Db.save("user_cart", record);
        if (save) {
            return cartEntity;
        } else {
            return null;
        }
    }

    @Override
    public CartEntity updateAmount(CartEntity cartEntity) {
        int update = Db.update("UPDATE user_cart SET amount = ? WHERE mappingId = ?", cartEntity.getAmount(), cartEntity.getMappingId());
        if (update == 1) {
            return cartEntity;
        } else {
            return null;
        }
    }

    @Override
    public CartEntity deleteById(LinkedList<? extends AccountEntity> accountEntities, CartEntity cartEntity) {
        String[] params = new String[accountEntities.size() + 1];
        int index = 0;
        params[index] = cartEntity.getMappingId();
        index++;
        String in = "";
        for (AccountEntity accountEntity : accountEntities) {
            in = in + " ? ,";
            params[index] = accountEntity.getAccountId();
            index++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE mappingId = ? AND accountId IN (" + in + ")", cartEntity.getMappingId());
        if (records.size() > 0) {
            Record record = records.get(0);
            boolean delete = Db.delete("user_cart", "mappingId", record);
            if (delete) {
                return JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<CartEntity> retrievesByAccountId(String accountId) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE status = 1 AND accountId = ? ORDER BY updateTime DESC ", accountId);
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public int countByAccounts(LinkedList<? extends AccountEntity> accountEntities) {
        Object[] params = new Object[accountEntities.size()];
        int index = 0;
        String in = "";
        for (AccountEntity accountEntity : accountEntities) {
            in = in + " ? ,";
            params[index] = accountEntity.getAccountId();
            index++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        List<Record> records = Db.find("SELECT COUNT(mappingId) AS counter FROM user_cart WHERE status = 1 AND accountId IN (" + in + ")",params);
        if (records.size() == 1) {
            return Integer.parseInt(records.get(0).getColumns().get("counter").toString());
        } else {
            return 0;
        }
    }

    @Override
    public LinkedList<CartEntity> retrievesByAccounts(LinkedList<? extends AccountEntity> accountEntities, int pageIndex, int counter) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();

        Object[] params = new Object[accountEntities.size() + 2];
        int index = 0;
        String in = "";
        for (AccountEntity accountEntity : accountEntities) {
            in = in + " ? ,";
            params[index] = accountEntity.getAccountId();
            index++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        params[index] = pageIndex * counter;
        index++;
        params[index] = counter;
        index++;
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE status = 1 AND accountId IN (" + in + ") ORDER BY updateTime DESC limit ? , ? ", params);
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public CartEntity retrieveById(String accountId, String mappingId) {
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE status = 1 AND mappingId = ? ORDER BY updateTime DESC", mappingId);
        return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
    }

    @Override
    public LinkedList<CartEntity> retrievesByIds(LinkedList<? extends AccountEntity> accountEntities, String[] mappingIds) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();

        String[] params = new String[accountEntities.size() + mappingIds.length];
        int index = 0;
        String inAccountIds = "";
        for (AccountEntity accountEntity : accountEntities) {
            inAccountIds = inAccountIds + " ? ,";
            params[index] = accountEntity.getAccountId();
            index++;
        }
        if (inAccountIds.length() > 0) {
            inAccountIds = inAccountIds.substring(0, inAccountIds.length() - 1);
        }
        String inMappingIds = "";
        for (String mappingId : mappingIds) {
            inMappingIds = inMappingIds + " ? ,";
            params[index] = mappingId;
            index++;
        }
        if (inMappingIds.length() > 0) {
            inMappingIds = inMappingIds.substring(0, inMappingIds.length() - 1);
        }

        List<Record> records = Db.find("SELECT * FROM user_cart WHERE status = 1 AND accountId IN (" + inAccountIds + ") AND mappingId IN (" + inMappingIds + ") ORDER BY updateTime DESC", params);
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public LinkedList<CartEntity> retrievesByOrderId(LinkedList<? extends AccountEntity> accountEntities, String orderId) {
        String[] params = new String[accountEntities.size() + 1];
        int index = 0;
        params[index] = orderId;
        index++;
        String in = "";
        for (AccountEntity accountEntity : accountEntities) {
            in = in + " ? ,";
            params[index] = accountEntity.getAccountId();
            index++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }

        List<Record> records = Db.find("SELECT * FROM user_cart WHERE orderId = ? AND accountId IN (" + in + ") ORDER BY updateTime DESC", params);
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public boolean attachToOrder(OrderEntity orderEntity, String[] mappingIds) {
        String[] params = new String[mappingIds.length + 1];
        int index = 0;
        params[index] = orderEntity.getOrderId();
        index++;
        String in = "";
        for (String mappingId : mappingIds) {
            in = in + " ? ,";
            params[index] = mappingId;
            index++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }

        int update = Db.update("UPDATE user_cart SET orderId = ? ,status = 2 WHERE mappingId IN (" + in + ") AND status = 1 ORDER BY updateTime DESC", params);
        if (update == mappingIds.length) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public LinkedList<CartEntity> mRetrievesByOrderId(LinkedList<? extends AccountEntity> accountEntities, String orderId) {
        return this.retrievesByOrderId(accountEntities, orderId);
    }

    @Override
    public LinkedList<CartEntity> mRetrievesByOrderId(String orderId) {
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE orderId = ? ORDER BY updateTime DESC", orderId);
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }
}
