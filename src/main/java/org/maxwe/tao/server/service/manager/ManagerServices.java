package org.maxwe.tao.server.service.manager;

import org.maxwe.tao.server.service.menu.MenuEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 11:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员数据服务层
 */
public class ManagerServices implements IManagerServices {

    @Override
    public ManagerEntity mLogin(ManagerEntity managerEntity) {
        List<Record> records = Db.find("SELECT * FROM manager WHERE loginName = ? AND password = ? AND status = 2", managerEntity.getLoginName(), managerEntity.getPassword());
        if (records.size() > 0) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), ManagerEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity mRetrieveById(ManagerEntity managerEntity) {
        List<Record> records = Db.find("SELECT * FROM manager WHERE managerId = ? AND status = 2", managerEntity.getManagerId());
        if (records.size() > 0) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), ManagerEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity mUpdate(ManagerEntity managerEntity) {
        Record updateManagerRecord = new Record()
                .set("managerId", managerEntity.getManagerId())
                .set("username", managerEntity.getUsername())
                .set("password", managerEntity.getPassword());
        boolean updateManager = Db.update("manager", "managerId", updateManagerRecord);
        if (updateManager) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<ManagerEntity> MRetrieves() {
        LinkedList<ManagerEntity> managerEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM manager WHERE status > 0 AND level > 0 ORDER BY queue");
        for (Record record : records) {
            managerEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), ManagerEntity.class));
        }
        return managerEntities;
    }

    @Override
    public boolean MExist(ManagerEntity managerEntity) {
        List<Record> records = Db.find("SELECT * FROM manager WHERE loginName = ? AND status != -1", managerEntity.getLoginName());
        if (records.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ManagerEntity MCreate(ManagerEntity managerEntity, LinkedList<? extends MenuEntity> menuEntities) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.update("UPDATE manager SET queue = queue + 1");
                Record saveManagerRecord = new Record()
                        .set("managerId", managerEntity.getManagerId())
                        .set("loginName", managerEntity.getLoginName())
                        .set("username", managerEntity.getUsername())
                        .set("password", managerEntity.getPassword())
                        .set("level", 1)
                        .set("queue", managerEntity.getQueue())
                        .set("status", managerEntity.getStatus());
                boolean saveManager = Db.save("manager", "managerId", saveManagerRecord);
                if (!saveManager) {
                    return false;
                }

                boolean result = true;
                if (menuEntities != null) {
                    for (MenuEntity menuEntity : menuEntities) {
                        Record saveMenuMappingRecord = new Record().set("managerId", managerEntity.getManagerId()).set("menuId", menuEntity.getMenuId());
                        boolean save = Db.save("manager_menu", "managerId, menuId", saveMenuMappingRecord);
                        result = result && save;
                        if (!result) {
                            break;
                        }
                    }
                }
                return result;
            }
        });

        if (succeed) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity MUpdate(ManagerEntity managerEntity, LinkedList<? extends MenuEntity> menuEntities) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Record updateManagerRecord = new Record()
                        .set("managerId", managerEntity.getManagerId())
                        .set("username", managerEntity.getUsername())
                        .set("password", managerEntity.getPassword());
                boolean updateManager = Db.update("manager", "managerId", updateManagerRecord);
                if (!updateManager) {
                    return false;
                }

                boolean result = true;
                if (menuEntities != null) {
                    Db.deleteById("manager_menu", "managerId", managerEntity.getManagerId());
                    for (MenuEntity menuEntity : menuEntities) {
                        Record saveMenuMappingRecord = new Record().set("managerId", managerEntity.getManagerId()).set("menuId", menuEntity.getMenuId());
                        boolean save = Db.save("manager_menu", "managerId, menuId", saveMenuMappingRecord);
                        result = result && save;
                        if (!result) {
                            break;
                        }
                    }
                }
                return result;
            }
        });

        if (succeed) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity MBlock(ManagerEntity managerEntity) {
        int update = Db.update("UPDATE manager SET status = 1 WHERE managerId = ? ", managerEntity.getManagerId());
        if (update == 1) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity MUnBlock(ManagerEntity managerEntity) {
        int update = Db.update("UPDATE manager SET status = 2 WHERE managerId = ? ", managerEntity.getManagerId());
        if (update == 1) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity MDelete(ManagerEntity managerEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.update("UPDATE manager SET status = -1 WHERE managerId = ? ", managerEntity.getManagerId());
                Db.update("DELETE FROM manager_menu WHERE managerId = ? ", managerEntity.getManagerId());
                return true;
            }
        });

        if (succeed) {
            return managerEntity;
        } else {
            return null;
        }
    }
}
