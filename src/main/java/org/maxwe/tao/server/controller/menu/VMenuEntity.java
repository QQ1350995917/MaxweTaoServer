package org.maxwe.tao.server.controller.menu;

import org.maxwe.tao.server.service.menu.MenuEntity;

/**
 * Created by Pengwei Ding on 2016-10-19 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VMenuEntity extends MenuEntity {
    public VMenuEntity() {
        super();
    }

    public VMenuEntity(MenuEntity menuEntity) {
        this.setMenuId(menuEntity.getMenuId());
        this.setLabel(menuEntity.getLabel());
        this.setFlag(menuEntity.getFlag());
        this.setQueue(menuEntity.getQueue());
        this.setCategory(menuEntity.getCategory());
        this.setStatus(menuEntity.getStatus());
        this.setActionKey(menuEntity.getActionKey());
    }
}
