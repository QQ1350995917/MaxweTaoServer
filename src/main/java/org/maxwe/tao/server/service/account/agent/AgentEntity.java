package org.maxwe.tao.server.service.account.agent;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.service.account.AccountEntity;

/**
 * Created by Pengwei Ding on 2016-12-25 15:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理账户
 */
public class AgentEntity extends AccountEntity{
    private int pId; // 可为空，只有加入了代理体系的账户才不为空
    private int reach; // 就代理关系达成一致意见 数据库可为空，1达成，其他不达成
    private String named; // 可为空，被上级命名的名字
    private int level;//级别标记
    private String weight; // 数据权重 下级对上级发出操作请求权重变为1，上级操作后权重变为0，上级读取下级列表时候按照更新时间和数据权重降序排列
    private int haveCodes; // 累计购买
    private int spendCodes; // 已经消费
    private int leftCodes; // 当前剩余
    private String wechat; // 微信号码
    private String trueName; // 真实姓名
    private String zhifubao;// 支付宝账户
    private long pIdTime;
    private long reachTime;

    @JSONField(serialize=false)
    private String createTimeLabel;
    @JSONField(serialize=false)
    private String updateTimeLabel;

    public AgentEntity() {
        super();
    }


    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao;
    }

    public long getpIdTime() {
        return pIdTime;
    }

    public void setpIdTime(long pIdTime) {
        this.pIdTime = pIdTime;
    }

    public long getReachTime() {
        return reachTime;
    }

    public void setReachTime(long reachTime) {
        this.reachTime = reachTime;
    }

    public String getCreateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getCreateTime());
    }


    public String getUpdateTimeLabel() {
        return DateTimeUtils.parseLongToFullTime(this.getUpdateTime());
    }

    @Override
    public String toString() {
        return super.toString() + "AgentEntity{" +
                "pId='" + pId + '\'' +
                ", reach='" + reach + '\'' +
                ", named='" + named + '\'' +
                ", weight='" + weight + '\'' +
                ", haveCodes=" + haveCodes +
                ", spendCodes=" + spendCodes +
                ", leftCodes=" + leftCodes +
                ", trueName='" + trueName + '\'' +
                ", zhifubao='" + zhifubao + '\'' +
                ", pIdTime='" + pIdTime + '\'' +
                ", reachTime='" + reachTime + '\'' +
                '}';
    }
}
