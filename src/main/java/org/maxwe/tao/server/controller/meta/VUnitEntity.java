package org.maxwe.tao.server.controller.meta;

import org.maxwe.tao.server.service.meta.UnitEntity;

/**
 * Created by Pengwei Ding on 2016-10-20 10:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VUnitEntity extends UnitEntity {
    public VUnitEntity() {
        super();
    }

    public VUnitEntity(UnitEntity unitEntity) {
        this.setUnitId(unitEntity.getUnitId());
        this.setLabel(unitEntity.getLabel());
        this.setName(unitEntity.getName());
        this.setQueue(unitEntity.getQueue());
    }
}
