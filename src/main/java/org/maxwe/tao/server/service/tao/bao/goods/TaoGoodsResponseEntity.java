package org.maxwe.tao.server.service.tao.bao.goods;

/**
 * Created by Pengwei Ding on 2017-01-18 15:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsResponseEntity {
    private long num_iid;// Number 123商品ID
    private String title;// String 连衣裙商品标题
    private String pict_url;// String http://gi4.md.alicdn.com/bao/uploaded/i4/xxx.jpg商品主图
    private Object small_images;// String [] http://gi4.md.alicdn.com/bao/uploaded/i4/xxx.jpg商品小图列表
    private String reserve_price;// String 102.00商品一口价格
    private String zk_final_price;// String 88.00商品折扣价格
    private long user_type;// Number;// 1卖家类型，0表示集市，1表示商城
    private String provcity;// String 杭州宝贝所在地
    private String item_url;// String http://detail.m.tmall.com/item.htm?id=xxx商品地址
    private String nick;// String demo卖家昵称
    private long seller_id;//Number 123卖家id
    private long volume;//Number 130天销量

    public TaoGoodsResponseEntity() {
        super();
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

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public Object getSmall_images() {
        return small_images;
    }

    public void setSmall_images(Object small_images) {
        this.small_images = small_images;
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

    public long getUser_type() {
        return user_type;
    }

    public void setUser_type(long user_type) {
        this.user_type = user_type;
    }

    public String getProvcity() {
        return provcity;
    }

    public void setProvcity(String provcity) {
        this.provcity = provcity;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
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

}
