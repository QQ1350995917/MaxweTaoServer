package org.maxwe.tao.client.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-04-15 18:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 阿里妈妈选品库中的接口测试
 */
public class GoodsClientTest {

    /**
     * 商品列表测试
     * @throws Exception
     */
    @Test
    public void goodsList() throws Exception {
        GoodsRequestModel goodsClient = new GoodsRequestModel();
        String goodsJson = GoodsClient.goodsList(goodsClient);
        Map map = JSON.parseObject(goodsJson, Map.class);
        assert Boolean.parseBoolean(map.get("ok").toString());
    }

    /**
     * 商品列表测试
     * @throws Exception
     */
    @Test
    public void goodsSearch() throws Exception {
        String[] urls = {
//                "https://item.taobao.com/item.htm?id=538007232739",
//                "https://item.taobao.com/item.htm?ut_sk=1.VH3TzEap4s" +
//                        "YDAOQIz5Pp4y3L_21380790_1491563391466.Copy.1&" +
//                        "id=538007232739&sourceType=item&price=18.5&" +
//                        "origin_price=21&suid=D893449F-7C81-4250-ACDF-5" +
//                        "1B80EA6A55F&un=f734e8809ee8d36fcca3f2c0d66365a2&" +
//                        "share_crt_v=1&cpp=1&shareurl=true&spm=a313p.22.1r1." +
//                        "31927715317&short_name=h.4rUoo3&cv=fiqPLIy2WP&" +
//                        "sm=51afd4&app=chrome",
                "http://c.b1wt.com/h.4rUoo3?cv=fiqPLIy2WP&sm=51afd4"
//            "https://uland.taobao.com/coupon/edetail?e=kG33US6KbnIbwa0ArmopK4gqbW%2BcbvidmmG8APN6%2FqO0XdNMpJUPKpndXyGQ3kxINgn5Z1tdZtWSq14Z6kG8J673htRU%2BHSH%2BMUwzxYlSKGhsAgV4swGin7I2%2FEspWQW0Nhis9XPAmWMlQJ16vCpa6J7%2BkHL3AEW&dx=1&src=tblm_lmapp&scm=20140618.1.01010001.s101c4&spm=a311n.8241267.2355084.1.&cpp=1&shareurl=true&short_name=h.5nVaVu&cv=ex5aZtr5e0S&app=chrome"
        };

        for (String url:urls){
            GoodsRequestModel goodsClient = new GoodsRequestModel();
            goodsClient.setQ(url);
            String goodsJson = GoodsClient.goodsSearch(goodsClient);
            Map map = JSON.parseObject(goodsJson, Map.class);
            assert Boolean.parseBoolean(map.get("ok").toString());
            assert map.containsKey("data");
            Map<String, Object> data = (Map<String, Object>) map.get("data");
            assert data != null;
            assert data.containsKey("pageList");
            JSONArray pageList = (JSONArray)data.get("pageList");
            assert pageList != null;
            assert pageList.size() == 1;
        }
    }

//    public static void main(String[] args) throws Exception {
//        GoodsRequestModel goodsClient = new GoodsRequestModel();
//        String goods = GoodsClient.goodsList(goodsClient);
//        System.out.println(goods);
//    }
}
