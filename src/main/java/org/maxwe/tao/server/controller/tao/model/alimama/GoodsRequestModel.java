package org.maxwe.tao.server.controller.tao.model.alimama;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-08 17:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * 以阿里妈妈模型作为标准
 * 构建淘妈咪系统内的商品请求统一模型
 */
public class GoodsRequestModel extends TokenModel {
    private long toPage = 1;// 页面
    private long perPageSize = 20;// 页面数据量
    private String q; // 查询关键字
    private String cookie; // 登录淘宝后产生的cookie
    private int sortType = 0;// 0:默认 1:佣金 2:优惠券 3:价格降低 4:价格升高 9:销量降序
    private int urlType = 0;//标记链接类型

    public GoodsRequestModel() {
        super();
    }

    public long getToPage() {
        return toPage;
    }

    public void setToPage(long toPage) {
        this.toPage = toPage;
    }

    public long getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(long perPageSize) {
        this.perPageSize = perPageSize;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    @Override
    public String toString() {
        return "GoodsRequestModel{" +
                "toPage=" + toPage +
                ", perPageSize=" + perPageSize +
                ", q='" + q + '\'' +
                ", cookie='" + cookie + '\'' +
                ", sortType=" + sortType +
                ", urlType=" + urlType +
                '}';
    }

    @JSONField(serialize=false)
    public boolean isGoodsRequestParamsOk() {
        if (this.getToPage() < 0 ||
                this.getPerPageSize() < 0 ||
                this.getPerPageSize() > 100) {
            return false;
        }

        if (StringUtils.isEmpty(this.getCookie())) {
            return false;
        }
        return true;
    }

}
