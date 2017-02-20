package org.maxwe.tao.server.service.tao.mami;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-19 10:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 返回到移动端的商品数据信息
 */
public class GoodsEntity implements Serializable {
    private long user_id;//卖家ID
    private String seller_nick;//卖家昵称
    private String shop_type;//店铺类型
    private String shop_title;//店铺名称
    private long num_iid;//商品ID
    private String title;//商品标题
    private String category;//后台一级类目
    private String reserve_price;//商品一口价格
    private String zk_final_price;//商品折扣价格
    private String coupon_info;//优惠券面额
    private long coupon_total_count;//优惠券总量
    private long coupon_remain_cou;//优惠券剩余量
    private String coupon_click_url;//商品优惠券推广链接
    private String coupon_start_time;//优惠券开始时间
    private String coupon_end_time;//优惠券借宿时间
    private String commission_rate;//佣金比率(%)
    private long volume;//月销量
    private String[] small_images;//商品小图标
    private String pict_url;//商品大图标
    private String item_url;//商品详情链接

    public GoodsEntity() {
        super();
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getSeller_nick() {
        return seller_nick;
    }

    public void setSeller_nick(String seller_nick) {
        this.seller_nick = seller_nick;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }

    public long getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(long num_iid) {
        this.num_iid = num_iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReserve_price() {
        return reserve_price;
    }

    public void setReserve_price(String reserve_price) {
        this.reserve_price = reserve_price;
    }

    public String getZk_final_price() {
        return zk_final_price;
    }

    public void setZk_final_price(String zk_final_price) {
        this.zk_final_price = zk_final_price;
    }

    public String getCoupon_info() {
        return coupon_info;
    }

    public void setCoupon_info(String coupon_info) {
        this.coupon_info = coupon_info;
    }

    public long getCoupon_total_count() {
        return coupon_total_count;
    }

    public void setCoupon_total_count(long coupon_total_count) {
        this.coupon_total_count = coupon_total_count;
    }

    public long getCoupon_remain_cou() {
        return coupon_remain_cou;
    }

    public void setCoupon_remain_cou(long coupon_remain_cou) {
        this.coupon_remain_cou = coupon_remain_cou;
    }

    public String getCoupon_click_url() {
        return coupon_click_url;
    }

    public void setCoupon_click_url(String coupon_click_url) {
        this.coupon_click_url = coupon_click_url;
    }

    public String getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(String coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public String getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(String coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public String getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(String commission_rate) {
        this.commission_rate = commission_rate;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String[] getSmall_images() {
        return small_images;
    }

    public void setSmall_images(String[] small_images) {
        this.small_images = small_images;
    }

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }
}
