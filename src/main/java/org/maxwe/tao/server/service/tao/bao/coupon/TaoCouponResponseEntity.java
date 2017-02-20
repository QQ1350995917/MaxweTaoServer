package org.maxwe.tao.server.service.tao.bao.coupon;

/**
 * Created by Pengwei Ding on 2017-02-19 14:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoCouponResponseEntity {
    private String coupon_start_time;// String 2016-09-25优惠券开始时间
    private String coupon_end_time;// String 2016-09-26优惠券结束时间
    private String coupon_click_url;// String https://uland.taobao.com/coupon/edetail?e=nqUNB1NOF3Bt3vqbdXnGloankzPYmeEFkgNrw6YHQf9pZTj41Orn8MwBAs06HAOzqQomYNedOiHDYPmqkFXqLR0HgBdG%2FDDL%2F1M%2FBw7Sf%2FesGXLf%2BqX4cbeC%2F2cR0p0NlWH0%2BknxpnCJJP%2FQkZSsyo1HvKjXo4uz&pid=mm_123_123_123&af=1商品优惠券推广链接
    private String shop_title;// String 秉迪数码专营店店铺名称
    private long coupon_total_count;// Number 8000优惠券总量
    private long user_type;// Number 1卖家类型，0表示集市，1表示商城
    private String zk_final_price;// String 28.00折扣价
    private long coupon_remain_count;// Number 6859优惠券剩余量
    private String commission_rate;// String 20.3佣金比率(%)
    private String title;// String zoyu kindle保护套 paperwhite1/2/3套958壳KPW3超薄皮套休眠499商品标题
    private String category;// String 1后台一级类目
    private long num_iid;// Number 524136796550商品ID
    private String nick;// String 秉迪数码专营店卖家昵称
    private long seller_id;// Number 1779343388卖家id
    private long volume;// Number 479230天销量
    private String pict_url;// String http://img4.tbcdn.cn/tfscom/i3/TB1vPdxHXXXXXbtXpXXXXXXXXXX_!!2-item_pic.png商品主图
    private String coupon_info;// String 满16元减10元优惠券面额
    private String item_url;// String http://item.taobao.com/item.htm?id=524136796550商品详情页链接地址
    private String small_images;// String [] http://img4.tbcdn.cn/tfscom/i3/TB1vPdxHXXXXXbtXpXXXXXXXXXX_!!2-item_pic.png商品小图列表

    public TaoCouponResponseEntity() {
        super();
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

    public String getCoupon_click_url() {
        return coupon_click_url;
    }

    public void setCoupon_click_url(String coupon_click_url) {
        this.coupon_click_url = coupon_click_url;
    }

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }

    public long getCoupon_total_count() {
        return coupon_total_count;
    }

    public void setCoupon_total_count(long coupon_total_count) {
        this.coupon_total_count = coupon_total_count;
    }

    public long getUser_type() {
        return user_type;
    }

    public void setUser_type(long user_type) {
        this.user_type = user_type;
    }

    public String getZk_final_price() {
        return zk_final_price;
    }

    public void setZk_final_price(String zk_final_price) {
        this.zk_final_price = zk_final_price;
    }

    public long getCoupon_remain_count() {
        return coupon_remain_count;
    }

    public void setCoupon_remain_count(long coupon_remain_count) {
        this.coupon_remain_count = coupon_remain_count;
    }

    public String getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(String commission_rate) {
        this.commission_rate = commission_rate;
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

    public long getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(long num_iid) {
        this.num_iid = num_iid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public String getCoupon_info() {
        return coupon_info;
    }

    public void setCoupon_info(String coupon_info) {
        this.coupon_info = coupon_info;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

    public String getSmall_images() {
        return small_images;
    }

    public void setSmall_images(String small_images) {
        this.small_images = small_images;
    }
}
