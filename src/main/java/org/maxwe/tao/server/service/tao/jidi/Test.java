package org.maxwe.tao.server.service.tao.jidi;

/**
 * Created by Pengwei Ding on 2017-02-20 21:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {
    private static void test() throws Exception {
//        int pageIndex = 1;
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpGet httpget = new HttpGet("http://api.tkjidi.com/getGoodsLink?appkey=db7818534504f08a4b5a8c584a6af3ca&type=www_lingquan&page=" + pageIndex);
//        HttpResponse response = client.execute(httpget);
//        /**请求发送成功，并得到响应**/
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            /**读取服务器返回过来的json字符串数据**/
//            String strResult = EntityUtils.toString(response.getEntity());
//            /**把json字符串转换成json对象**/
//            JiDiGoodsModel jiDiGoodsModel = JSONObject.parseObject(strResult, JiDiGoodsModel.class);
//
//            System.out.println(strResult);
//        } else {
//
//        }
    }

    public static String getGoodsId() {
        String goodsId = null;
        String[] split = "https://world.tmall.com/item/xxx.htm?id=535933437297&ali_refid=a3_430620_1006:1105484261:N:iPhone6%E6%89%8B%E6%9C%BA%E5%A3%B3:ea538d6504c872f2f9cd95392620bf1a&ali_trackid=1_ea538d6504c872f2f9cd95392620bf1a&spm=a312a.7700714.0.0.0v9xmL".split("\\?");
        if (split.length > 1) {
            String[] split1 = split[1].split("&");//id=535933437297
            for (String sp : split1) {
                if (sp.startsWith("id=")) {
                    goodsId = sp.replace("id=", "");
                    break;
                }
            }
        }
        if (goodsId == null){
            goodsId = split[0].substring(split[0].lastIndexOf("/") + 1, split[0].lastIndexOf("."));
        }
        return goodsId;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getGoodsId());
    }
}
