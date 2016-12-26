package org.maxwe.tao.server.service.user.seller;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-12-25 15:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SellerServices implements ISellerServices {
    @Override
    public boolean existSeller(String cellphone) {
        List<Record> records = Db.find("SELECT * FROM seller WHERE cellphone = ? ", cellphone);
        if (records.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SellerEntity createSeller(SellerEntity sellerEntity) {
        Record accountRecord = new Record()
                .set("sellerId", sellerEntity.getSellerId())
                .set("cellphone", sellerEntity.getCellphone())
                .set("password", sellerEntity.getPassword());
        boolean account = Db.save("seller", accountRecord);
        if (account) {
            return sellerEntity;
        } else {
            return null;
        }
    }

    @Override
    public SellerEntity updateSellerPassword(SellerEntity sellerEntity) {
        int count = Db.update("UPDATE seller SET password = ? WHERE sellerId = ? ", sellerEntity.getPassword(), sellerEntity.getSellerId());
        if (count == 1) {
            return sellerEntity;
        } else {
            return null;
        }
    }

    @Override
    public SellerEntity retrieveSeller(SellerEntity sellerEntity) {
        List<Record> accountRecords = Db.find("SELECT * FROM seller WHERE cellphone = ? AND password = ? ", sellerEntity.getCellphone(),sellerEntity.getPassword());
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        SellerEntity seller = JSON.parseObject(JSON.toJSONString(accountMap), SellerEntity.class);
        return seller;
    }

    @Override
    public SellerEntity retrieveSeller(String sellerId) {
        List<Record> accountRecords = Db.find("SELECT * FROM seller WHERE sellerId = ? ", sellerId);
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        SellerEntity sellerEntity = JSON.parseObject(JSON.toJSONString(accountMap), SellerEntity.class);
        return sellerEntity;
    }
}
