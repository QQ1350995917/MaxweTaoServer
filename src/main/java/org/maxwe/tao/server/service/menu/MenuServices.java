package org.maxwe.tao.server.service.menu;

import org.maxwe.tao.server.service.manager.ManagerEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 14:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MenuServices implements IMenuServices {

    @Override
    public LinkedList<MenuEntity> retrievesByManager(ManagerEntity managerEntity) {
//        LinkedList<MenuEntity> menuEntities = new LinkedList<>();
//        List<Record> records = Db.find("SELECT * FROM menu WHERE menuId IN (SELECT menuId FROM manager_menu WHERE managerId = ? ) AND status = 2 ORDER BY category, queue", managerEntity.getManagerId());
//        for (Record record : records) {
//            menuEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), MenuEntity.class));
//        }
//        return menuEntities;
        return null;
    }

    @Override
    public LinkedList<MenuEntity> mRetrievesByAdmin(int category) {
        List<Record> records = null;
        if (category == 0) {
            records = Db.find("SELECT * FROM menu WHERE category > 0 ORDER BY category, queue");
        } else if (category > 0) {
            records = Db.find("SELECT * FROM menu WHERE category = ? ORDER BY category, queue", category);
        }
        if (records != null) {
            LinkedList<MenuEntity> menuEntities = new LinkedList<>();
            for (Record record : records) {
                menuEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), MenuEntity.class));
            }
            return menuEntities;
        }
        return null;
    }
}
