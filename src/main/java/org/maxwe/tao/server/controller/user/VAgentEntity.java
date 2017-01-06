package org.maxwe.tao.server.controller.user;

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
    private String password;
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
        if (this.getType() == 1) {
            this.setPassword1(agentEntity.getPassword1());
        } else if (this.getType() == 2) {
            this.setPassword2(agentEntity.getPassword2());
        }
        this.setName(agentEntity.getName());
        this.setNamed(agentEntity.getNamed());
        this.setGrantCode(agentEntity.getGrantCode());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCellPhoneCode() {
        return cellPhoneCode;
    }

    public void setCellPhoneCode(String cellPhoneCode) {
        this.cellPhoneCode = cellPhoneCode;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    public boolean checkCreateParams() {
        if (this.getType() == 1) {
            if (!CellPhoneUtils.isCellphone(this.getCellphone())
                    || StringUtils.isEmpty(this.getPassword()) || this.getPassword().length() < 6) {
                return false;
            } else {
                return true;
            }
        } else if (this.getType() == 2) {
            if (!CellPhoneUtils.isCellphone(this.getCellphone())
                    || StringUtils.isEmpty(this.getPassword()) || this.getPassword().length() < 6) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean checkLoginParams() {
        if (this.getType() == 1) {
            if (!CellPhoneUtils.isCellphone(this.getCellphone())
                    || StringUtils.isEmpty(this.getPassword()) || this.getPassword().length() < 6) {
                return false;
            } else {
                return true;
            }
        } else if (this.getType() == 2) {
            if (!CellPhoneUtils.isCellphone(this.getCellphone())
                    || StringUtils.isEmpty(this.getPassword()) || this.getPassword().length() < 6) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public boolean checkModifyPasswordParams() {
        if (StringUtils.isEmpty(this.getT())
                || StringUtils.isEmpty(this.getOrdPassword()) || this.getOrdPassword().length() < 6
                || StringUtils.isEmpty(this.getNewPassword()) || this.getNewPassword().length() < 6
                || (this.getType() != 1 && this.getType() != 2)) {
            return false;
        } else {
            return true;
        }
    }

}
