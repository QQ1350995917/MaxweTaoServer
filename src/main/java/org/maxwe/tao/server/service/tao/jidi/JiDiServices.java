package org.maxwe.tao.server.service.tao.jidi;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.service.tao.mami.GoodsEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-20 22:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class JiDiServices implements IJiDiGoodsServices {
    private final Logger logger = Logger.getLogger(JiDiServices.class.getName());
    private static JiDiServices instance;

    private JiDiServices() {

    }

    public static synchronized JiDiServices getInstance() {
        if (instance == null) {
            instance = new JiDiServices();
        }
        return instance;
    }

    private static final String APPKEY = "db7818534504f08a4b5a8c584a6af3ca";
    private static final String allGoodsUrl = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=www_lingquan&page=";
    private static final String goodsTop100Url = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=top100&page=";
    private static final String goodsDaPaiUrl = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=dapai&page=";
    private static final String goodsMeiRiBiMaiUrl = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=bipai&page=";

    private static final LinkedList<GoodsEntity> allGoods = new LinkedList<>();
    private static final LinkedList<GoodsEntity> goodsTop100 = new LinkedList<>();
    private static final LinkedList<GoodsEntity> goodsDaPai = new LinkedList<>();
    private static final LinkedList<GoodsEntity> goodsMeiRiBiPai = new LinkedList<>();

    private static volatile boolean isReadable = true;

    @Override
    public synchronized void init() {
        logger.info("init : 将要执行初始化 ");
        if (!isReadable) {
            this.logger.info("init : 执行初始化正在执行中... ");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("init : 执行初始化开始 ");
                isReadable = false;
                boolean isGoing = true;
                try {
                    //等待5秒，让所有的读操作完毕
                    Thread.sleep(5000);

                    allGoods.clear();
                    goodsTop100.clear();
                    goodsDaPai.clear();
                    goodsMeiRiBiPai.clear();

                    int pageIndex = 0;

                    System.out.println("================正在获取全量数据===============");
                    logger.info("================正在获取全量数据===============");
                    while (isGoing) {
                        pageIndex++;
                        try {
                            LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(allGoodsUrl, pageIndex);
                            if (goodsEntityFormServer.size() < 1) {
                                isGoing = false;
                            } else {
                                allGoods.addAll(goodsEntityFormServer);
                                System.out.println("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + allGoods.size());
                                logger.info("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + allGoods.size());
                            }
                        } catch (Exception e) {
                            isGoing = false;
                            e.printStackTrace();
                            logger.error(e);
                        }
                    }

                    pageIndex = 0;
                    isGoing = true;

                    System.out.println("================正在获取Top100数据===============");
                    logger.info("================正在获取Top100数据===============");
                    while (isGoing) {
                        pageIndex++;
                        try {
                            LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(goodsTop100Url, pageIndex);
                            if (goodsEntityFormServer.size() < 1) {
                                isGoing = false;
                            } else {
                                goodsTop100.addAll(goodsEntityFormServer);
                                System.out.println("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + goodsTop100.size());
                                logger.info("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + goodsTop100.size());
                            }
                        } catch (Exception e) {
                            isGoing = false;
                            e.printStackTrace();
                            logger.error(e);
                        }
                    }


                    pageIndex = 0;
                    isGoing = true;
                    System.out.println("================正在获取大牌数据===============");
                    logger.info("================正在获取大牌数据===============");
                    while (isGoing) {
                        pageIndex++;
                        try {
                            LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(goodsDaPaiUrl, pageIndex);
                            if (goodsEntityFormServer.size() < 1) {
                                isGoing = false;
                            } else {
                                goodsDaPai.addAll(goodsEntityFormServer);
                                System.out.println("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + goodsDaPai.size());
                                logger.info("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + goodsDaPai.size());
                            }
                        } catch (Exception e) {
                            isGoing = false;
                            e.printStackTrace();
                            logger.error(e);
                        }
                    }

                    pageIndex = 0;
                    isGoing = true;
                    System.out.println("================正在获取每日必买数据===============");
                    logger.info("================正在获取每日必买数据===============");
                    while (isGoing) {
                        pageIndex++;
                        try {
                            LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(goodsMeiRiBiMaiUrl, pageIndex);
                            if (goodsEntityFormServer.size() < 1) {
                                isGoing = false;
                            } else {
                                goodsMeiRiBiPai.addAll(goodsEntityFormServer);
                                System.out.println("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + goodsMeiRiBiPai.size());
                                logger.info("第" + pageIndex + "页，数据量：" + goodsEntityFormServer.size() + "，总量：" + goodsMeiRiBiPai.size());
                            }
                        } catch (Exception e) {
                            isGoing = false;
                            e.printStackTrace();
                            logger.error(e);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e);
                } finally {
                    isGoing = false;
                    isReadable = true;
                }
            }
        }).start();
    }

    @Override
    public LinkedList<GoodsEntity> getGoods(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        if (isReadable) {
            int startIndex = pageIndex * pageSize;
            int endIndex = (pageIndex + 1) * pageSize;
            for (int index = startIndex; index < endIndex && index < allGoods.size(); index++) {
                result.add(allGoods.get(index));
            }
        }
        return result;
    }

    @Override
    public LinkedList<GoodsEntity> getGoodsTop100(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        if (isReadable) {
            int startIndex = pageIndex * pageSize;
            int endIndex = (pageIndex + 1) * pageSize;
            for (int index = startIndex; index < endIndex && index < goodsTop100.size(); index++) {
                result.add(goodsTop100.get(index));
            }
        }
        return result;
    }

    @Override
    public LinkedList<GoodsEntity> getGoodsDaPai(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        if (isReadable) {
            int startIndex = pageIndex * pageSize;
            int endIndex = (pageIndex + 1) * pageSize;
            for (int index = startIndex; index < endIndex && index < goodsDaPai.size(); index++) {
                result.add(goodsDaPai.get(index));
            }
        }
        return result;
    }

    @Override
    public LinkedList<GoodsEntity> getGoodsMeiRiBiPai(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        if (isReadable) {
            int startIndex = pageIndex * pageSize;
            int endIndex = (pageIndex + 1) * pageSize;
            for (int index = startIndex; index < endIndex && index < goodsMeiRiBiPai.size(); index++) {
                result.add(goodsMeiRiBiPai.get(index));
            }
        }
        return result;
    }

    public int getDataCounter() {
        return allGoods.size();
    }

    private  LinkedList<GoodsEntity> getGoodsEntityFormServer(String url, int pageIndex) throws Exception {
        LinkedList<GoodsEntity> result = new LinkedList<>();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url + pageIndex);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

        CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet);

        try {
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
                JiDiGoodsModel jiDiGoodsModel = JSONObject.parseObject(resultJson, JiDiGoodsModel.class);
                List<JiDiGoodsEntity> data = jiDiGoodsModel.getData();
                for (JiDiGoodsEntity jiDiGoodsEntity : data) {
                    if (jiDiGoodsEntity != null) {
                        GoodsEntity goodsEntity = new GoodsEntity();
                        goodsEntity.setTitle(jiDiGoodsEntity.getGoods_name());
                        goodsEntity.setNum_iid(Long.parseLong(jiDiGoodsEntity.getGoods_id() == null ? "0" : jiDiGoodsEntity.getGoods_id()));
                        goodsEntity.setCategory(jiDiGoodsEntity.getCate_name());
                        goodsEntity.setPict_url(jiDiGoodsEntity.getPic());
                        goodsEntity.setReserve_price(jiDiGoodsEntity.getPrice());
                        goodsEntity.setZk_final_price(jiDiGoodsEntity.getPrice_after_coupons());
                        goodsEntity.setCoupon_info(jiDiGoodsEntity.getPrice_coupons());
                        goodsEntity.setVolume(Long.parseLong(jiDiGoodsEntity.getSales() == null ? "0" : jiDiGoodsEntity.getSales()));
                        goodsEntity.setCommission_rate(jiDiGoodsEntity.getRate());
                        goodsEntity.setCoupon_click_url(jiDiGoodsEntity.getQuan_link());
                        goodsEntity.setCoupon_total_count(Long.parseLong(jiDiGoodsEntity.getQuan_zhong() == null ? "0" : jiDiGoodsEntity.getQuan_zhong()));
                        goodsEntity.setCoupon_remain_cou(Long.parseLong(jiDiGoodsEntity.getQuan_shengyu() == null ? "0" : jiDiGoodsEntity.getQuan_shengyu()));
                        goodsEntity.setCoupon_end_time(jiDiGoodsEntity.getQuan_expired_time());
                        goodsEntity.setItem_url(jiDiGoodsEntity.getGoods_url());
//                private String cate_id;// 1,/*分类id*/
//                private String commission_type;// 3,/*佣金类型*/
//                private String commission_type_name;// 鹊桥,/*佣金类型名称*/
//                private String quan_note;// 单笔满69元可用，每人限领1 张,/*领券的备注*/
//                private String quan_guid_content;// 儿童纯棉中厚款，贴身舒适，打底家居套装，必备保暖套装！/*领的导购文案*/
//                private String quan_qq_img;// http://images.***.*******.jpg/*QQ群发时候用的图片*/
                        result.add(goodsEntity);
                    }
                }
            }
            // do something useful with the response body
            // and ensure it is fully consumed
        } finally {
            closeableHttpResponse.close();
        }
        return result;
    }
}
