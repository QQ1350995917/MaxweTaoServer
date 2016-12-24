package org.maxwe.tao.server.controller.product;

import org.maxwe.tao.server.service.product.SeriesEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 17:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VSeriesEntity extends SeriesEntity {
    private String cs;
    private LinkedList<VTypeEntity> children;

    public VSeriesEntity() {
        super();
    }

    public VSeriesEntity(SeriesEntity seriesEntity) {
        this.setSeriesId(seriesEntity.getSeriesId());
        this.setLabel(seriesEntity.getLabel());
        this.setQueue(seriesEntity.getQueue());
        this.setStatus(seriesEntity.getStatus());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public LinkedList<VTypeEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VTypeEntity> children) {
        this.children = children;
    }

    public boolean checkCreateParams() {
        if (this.getLabel() == null || this.getLabel().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUpdateParams() {
        if (this.getSeriesId() == null || this.getSeriesId().trim().equals("")
                || this.getLabel() == null || this.getLabel().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkMarkParams() {
        if (this.getSeriesId() == null || this.getSeriesId().trim().equals("")
                || (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkSeriesId() {
        if (this.getSeriesId() == null || this.getSeriesId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
