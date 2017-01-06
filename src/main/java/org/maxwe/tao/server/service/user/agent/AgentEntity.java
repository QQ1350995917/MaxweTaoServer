package org.maxwe.tao.server.service.user.agent;

/**
 * Created by Pengwei Ding on 2016-12-25 15:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AgentEntity {
    private String agentId;
    private String agentPId; // 可为空，只有经过了转让授权码的账户才不为空
    private String cellphone; // 不为空
    private String password1; // 业务类型1的密码，可为空
    private String password2; // 业务类型2的密码，可为空
    private String name; // 自己命名的名字
    private String named; // 被上级备注的名字
    private String grantCode; //自己的授权码
    private int type;// 业务类型，1普通的代理，2高级代理，3综合业务代理
    private int level; // 0 标示创始人，超级管理者
    private int status; // 状态
    private int haveCodes; // 累计购买
    private int spendCodes; // 已经消费
    private int leftCodes; // 当前剩余
    private String createTime;
    private String updateTime;

    public AgentEntity() {
        super();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentPId() {
        return agentPId;
    }

    public void setAgentPId(String agentPId) {
        this.agentPId = agentPId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public String getGrantCode() {
        return grantCode;
    }

    public void setGrantCode(String grantCode) {
        this.grantCode = grantCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHaveCodes() {
        return haveCodes;
    }

    public void setHaveCodes(int haveCodes) {
        this.haveCodes = haveCodes;
    }

    public int getSpendCodes() {
        return spendCodes;
    }

    public void setSpendCodes(int spendCodes) {
        this.spendCodes = spendCodes;
    }

    public int getLeftCodes() {
        return leftCodes;
    }

    public void setLeftCodes(int leftCodes) {
        this.leftCodes = leftCodes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "agentId = " + agentId + ", agentPId = " + agentPId + ", cellphone = " + cellphone
                + ", password1 = " + password1 + ", password2 = " + password2 + ", name" + name
                + ", named = " + named + ", grantCode = " + grantCode + ", type = " + type + ", level = "
                + ", status = " + status + ", haveCodes = " + haveCodes + ", spendCodes = " + spendCodes
                + ", leftCodes = " + leftCodes + ", createTime = " + createTime;
    }
}
