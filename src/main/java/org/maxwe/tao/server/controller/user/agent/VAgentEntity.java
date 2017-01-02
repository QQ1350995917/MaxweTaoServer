package org.maxwe.tao.server.controller.user.agent;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.service.user.agent.AgentEntity;

/**
 * Created by Pengwei Ding on 2016-09-29 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VAgentEntity extends AgentEntity {
    private String t;
    private String ordPassword;
    private String newPassword;
    private String cellPhoneCode;

    public VAgentEntity() {
        super();
    }

    public VAgentEntity(String t) {
        super();
        this.t = t;
    }

    public VAgentEntity(AgentEntity agentEntity) {
        this.setAgentId(agentEntity.getAgentId());
        this.setAgentPId(agentEntity.getAgentPId());
        this.setCellphone(agentEntity.getCellphone());
        this.setPassword(agentEntity.getPassword());
        this.setName(agentEntity.getName());
        this.setNamed(agentEntity.getNamed());
        this.setCode(agentEntity.getCode());
        this.setType(agentEntity.getType());
        this.setLevel(agentEntity.getLevel());
        this.setStatus(agentEntity.getStatus());
        this.setHaveCodes(agentEntity.getHaveCodes());
        this.setSpendCodes(agentEntity.getSpendCodes());
        this.setLeftCodes(agentEntity.getLeftCodes());
        this.setCreateTime(agentEntity.getCreateTime());
        this.setUpdateTime(agentEntity.getUpdateTime());
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getOrdPassword() {
        return ordPassword;
    }

    public void setOrdPassword(String ordPassword) {
        this.ordPassword = ordPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCellPhoneCode() {
        return cellPhoneCode;
    }

    public void setCellPhoneCode(String cellPhoneCode) {
        this.cellPhoneCode = cellPhoneCode;
    }

    public boolean checkCreateParams() {
        if (!CellPhoneUtils.isCellphone(this.getCellphone())
                || StringUtils.isEmpty(this.getPassword()) || this.getPassword().length() < 6
                || this.getType() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkLoginParams() {
        if (!CellPhoneUtils.isCellphone(this.getCellphone())
                || StringUtils.isEmpty(this.getPassword()) || this.getPassword().length() < 6
                || this.getType() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkModifyPasswordParams() {
        if (StringUtils.isEmpty(this.getT())
                || StringUtils.isEmpty(this.getOrdPassword()) || this.getOrdPassword().length() < 6
                || StringUtils.isEmpty(this.getNewPassword()) || this.getNewPassword().length() < 6
                || this.getType() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
