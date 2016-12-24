package org.maxwe.tao.server.controller.link;

import org.maxwe.tao.server.service.link.LinkEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-10-11 15:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VLinkEntity extends LinkEntity {
    private String cs;
    private String linkId1;
    private int weight1;
    private String linkId2;
    private int weight2;

    private LinkedList<VLinkEntity> children;

    public VLinkEntity() {
        super();
    }

    public VLinkEntity(LinkEntity linkEntity) {
        this.setLinkId(linkEntity.getLinkId());
        this.setLabel(linkEntity.getLabel());
        this.setHref(linkEntity.getHref());
        this.setPid(linkEntity.getPid());
        this.setWeight(linkEntity.getWeight());
        this.setStatus(linkEntity.getStatus());
        this.setCreateTime(linkEntity.getCreateTime());
        this.setUpdateTime(linkEntity.getUpdateTime());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getLinkId1() {
        return linkId1;
    }

    public void setLinkId1(String linkId1) {
        this.linkId1 = linkId1;
    }

    public int getWeight1() {
        return weight1;
    }

    public void setWeight1(int weight1) {
        this.weight1 = weight1;
    }

    public String getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(String linkId2) {
        this.linkId2 = linkId2;
    }

    public int getWeight2() {
        return weight2;
    }

    public void setWeight2(int weight2) {
        this.weight2 = weight2;
    }

    public LinkedList<VLinkEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VLinkEntity> children) {
        this.children = children;
    }


    public boolean checkCreateParams() {
        if (this.getPid() == null) {
            if (this.getLabel() == null || this.getLabel().trim().equals("")) {
                return false;
            } else {
                return true;
            }
        } else {
            if (this.getLabel() == null
                    || this.getHref() == null
                    || this.getLabel().trim().equals("")
                    || this.getHref().trim().equals("")) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkUpdateParams() {
        if (this.getPid() == null || this.getLinkId() == null) {
            return false;
        }
        if (this.getPid().equals(this.getLinkId())) {
            if (this.getLabel() == null || this.getLabel().trim().equals("")) {
                return false;
            } else {
                return true;
            }
        } else {
            if (this.getLinkId() == null
                    || this.getPid() == null
                    || this.getLabel() == null
                    || this.getHref() == null
                    || this.getLinkId().trim().equals("")
                    || this.getPid().trim().equals("")
                    || this.getLabel().trim().equals("")
                    || this.getHref().trim().equals("")) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkMarkParams() {
        if (this.getLinkId() != null
                && !this.getLinkId().trim().equals("")
                && (this.getStatus() == -1 || this.getStatus() == 1 || this.getStatus() == 2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkSwapParams() {
        if (this.getLinkId1() == null
                || this.getLinkId2() == null
                || this.getLinkId1().trim().equals("")
                || this.getLinkId2().trim().equals("")
                || this.getWeight1() < 0
                || this.getWeight2() < 0
                || this.getWeight1() == this.getWeight2()) {
            return false;
        } else {
            return true;
        }
    }
}
