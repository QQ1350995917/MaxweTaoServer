package org.maxwe.tao.server.service.tao;

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
    public static final String METHOD_NAME_ITEM = "taobao.itemcats.get"; // http://open.taobao.com/docs/api.htm?spm=a219a.7386793.0.0.owNYvV&apiId=122

    // 获取商品
    public static final String METHOD_NAME_GOODS = "taobao.tbk.item.get";
    // 获取商品的字段
    public static final String METHOD_NAME_GOODS_FIELDS = "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick";

    // 生成淘口令
    public static final String METHOD_NAME_TAO_PWD_GET = "taobao.wireless.share.tpwd.create";
    // 查询某个卖家的店铺优惠券领取活动
    public static final String METHOD_NAME_TAO_TICKET_GET = "taobao.promotion.activity.get";
    //
    public static TaobaoClient getTaoBaoClient() {
        return new DefaultTaobaoClient(URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
    }
}
