package org.maxwe.tao.server.controller.tao.model.alimama;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.SearchUrlUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-08 17:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * 以阿里妈妈模型作为标准
 * 构建淘妈咪系统内的商品请求统一模型
 */
public class GoodsRequestModel extends TokenModel {
    @JSONField(serialize=false)
    public static final int GOODS_TAO_BAO = 0;
    @JSONField(serialize=false)
    public static final int GOODS_GAO_YONG = 1;
    @JSONField(serialize=false)
    public static final int GOODS_TAO_MA_MI = 2;
    @JSONField(serialize=false)
    public static final int GOODS_OTHER = 3;


    private long toPage = 1;// 页面
    private long perPageSize = 20;// 页面数据量
    private String q; // 查询关键字
    private String cookie; // 登录淘宝后产生的cookie
    private int sortType = 0;// 0:默认 1:佣金 2:优惠券 3:价格降低 4:价格升高 9:销量降序
    private int urlType = 0;//标记链接类型
    private int dpyhq = 0;// 1店铺优惠券 其他无
    private int userType = -1;// 0 淘宝，1天猫

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
        if (!StringUtils.isEmpty(this.q) && this.q.startsWith("http")){
            return SearchUrlUtils.cleanUrl(this.q);
        }
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

    public int getDpyhq() {
        return dpyhq;
    }

    public void setDpyhq(int dpyhq) {
        this.dpyhq = dpyhq;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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
                ", dpyhq=" + dpyhq +
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
