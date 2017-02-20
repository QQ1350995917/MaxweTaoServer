package org.maxwe.tao.server.service.tao.bao;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;

/**
 * Created by Pengwei Ding on 2017-01-18 15:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class APIConstants {

    public static final String URL_FORMAL = "http://gw.api.taobao.com/router/rest";
    public static final String URL_SANDBOX = "http://gw.api.tbsandbox.com/router/rest";

    // 获取类目
    public static final String METHOD_NAME_CATEGORY = "taobao.itemcats.get";

    // 获取商品
    public static final String METHOD_NAME_GOODS = "taobao.tbk.item.get";
    // 获取商品的字段
    public static final String METHOD_NAME_GOODS_FIELDS = "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick";
    // 淘宝客店铺关联推荐查询
    public static final String METHOD_NAME_SHOP = "taobao.tbk.shop.recommend.get";
    // 生成淘口令
    public static final String METHOD_NAME_TAO_PWD_GET = "taobao.wireless.share.tpwd.create";
    // 根据nid批量查询优惠券
    public static final String METHOD_NAME_TAO_TICKET_GET = "taobao.tbk.itemid.coupon.get";
    // 转链
    public static final String METHOD_NAME_CONVERT = "taobao.tbk.item.convert";
    // 物料传播方式获取
    public static final String METHOD_NAME_SPREAD = "taobao.tbk.spread.get";
    //
    public static TaobaoClient getTaoBaoClient() {
        return new DefaultTaobaoClient(URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
    }
}
