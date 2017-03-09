package org.maxwe.tao.server.service.tao.alimama.brand;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.controller.tao.model.alimama.BrandCreateRequestModel;
import org.maxwe.tao.server.controller.tao.model.alimama.BrandListRequestModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-03-09 13:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 一键设置推广位的服务
 */
public class BrandServices {
    private static final Logger logger = Logger.getLogger(BrandServices.class.getName());

    public static List<GuideEntity> getBrandList(BrandListRequestModel requestModel) throws Exception {
        // 获取导购管理和导购推广位的信息
        final String URL_AD_ZONE = "http://pub.alimama.com/common/adzone/newSelfAdzone2.json?tag=29";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL_AD_ZONE);
        httpGet.setHeader("Cookie", requestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            Map rootMap = JSON.parseObject(resultJson, Map.class);
            Map<String, Object> dataMap = (Map<String, Object>) rootMap.get("data");
            List<Map<String, Object>> adZoneList = (List<Map<String, Object>>) dataMap.get("otherAdzones");
            if (adZoneList != null && adZoneList.size() > 0) {
                List<GuideEntity> guideEntities = new LinkedList<>();
                for (Map<String, Object> map : adZoneList) {
                    String guideId = map.get("id").toString();
                    String guideName = map.get("name").toString();
                    List<Map<String, Object>> sub = (List<Map<String, Object>>) map.get("sub");
                    GuideEntity guideEntity = new GuideEntity(guideId, guideName);
                    LinkedList<AdZoneEntity> adZoneEntities = new LinkedList<>();
                    if (sub != null) {
                        for (Map<String, Object> subMap : sub) {
                            String adZoneId = subMap.get("id").toString();
                            String adZoneName = subMap.get("name").toString();
                            AdZoneEntity adZoneEntity = new AdZoneEntity(guideId, adZoneId, adZoneName);
                            adZoneEntities.add(adZoneEntity);
                        }
                        guideEntity.setAdZones(adZoneEntities);
                    }
                    guideEntities.add(guideEntity);
                }
                return guideEntities;
            } else {
                logger.error("getBrandList 执行错误，返回的信息是：" + resultJson);
                return null;
            }
        } else {
            logger.error("getBrandList 执行错误，仅仅得到网络响应码：" + closeableHttpResponse.getStatusLine().getStatusCode());
            return null;
        }
    }

    /**
     * 创建一对新的导购推广和导购推广位
     *
     * @param requestModel
     * @return
     * @throws Exception
     */
    public static List<GuideEntity> createBrand(BrandCreateRequestModel requestModel) throws Exception {
        boolean isGuideCreateSuccess = createGuide(requestModel.getCookie(), requestModel.getGuideName(), requestModel.getWeChat());
        if (!isGuideCreateSuccess) {
            return null;
        }

        BrandListRequestModel brandListRequestModel = new BrandListRequestModel();
        brandListRequestModel.setCookie(requestModel.getCookie());
        List<GuideEntity> brandList = getBrandList(brandListRequestModel);
        if (brandList == null || brandList.size() < 1) {
            return null;
        }

        GuideEntity currentGuideEntity = null;
        for (GuideEntity guideEntity : brandList) {
            if (guideEntity.getName().equals(requestModel.getGuideName())) {
                currentGuideEntity = guideEntity;
                break;
            }
        }

        if (currentGuideEntity == null) {
            return null;
        }

        List<GuideEntity> result = new LinkedList<>();
        AdZoneEntity adZone = createADZone(requestModel.getCookie(), currentGuideEntity.getSiteId(), requestModel.getAdZoneName());
        if (adZone != null) {
            LinkedList<AdZoneEntity> adZoneEntities = new LinkedList<>();
            adZoneEntities.add(adZone);
            currentGuideEntity.setAdZones(adZoneEntities);
        }
        result.add(currentGuideEntity);
        return result;
    }

    /**
     * 创建新的导购推广，该接口比较奇葩，仅仅返回创建是否成功的结果，不会返回具体信息。
     * 所以在创建成功后要进行二次获取
     *
     * @param cookie
     * @param guideName
     * @param weChat
     * @return
     * @throws Exception
     */
    private static boolean createGuide(String cookie, String guideName, String weChat) throws Exception {
        final String URL_ADD_GUIDE = "http://pub.alimama.com/common/site/generalize/guideAdd.json";
        final String PARAMS_ADD_GUIDE = "name=" + guideName + "&categoryId=14&account1=" + weChat;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL_ADD_GUIDE + "?" + PARAMS_ADD_GUIDE);
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            Map rootMap = JSON.parseObject(resultJson, Map.class);
            Map<String, Object> infoMap = (Map<String, Object>) rootMap.get("info");
            if (Boolean.parseBoolean(infoMap.get("ok").toString())) {
                logger.info("createGuide 执行成功，提交的名称是：" + guideName + " 返回的信息是：" + resultJson);
                return true;
            } else {
                logger.error("createGuide 执行失败，提交的名称是：" + guideName + " 返回的信息是：" + resultJson);
                return false;
            }
        } else {
            logger.error("createGuide 执行失败，仅仅得到网络响应码是：" + closeableHttpResponse.getStatusLine().getStatusCode());
            return false;
        }
    }

    /**
     * 创建导购推广位
     *
     * @param cookie
     * @param guideId
     * @param adZoneName
     * @return
     * @throws Exception
     */
    private static AdZoneEntity createADZone(String cookie, String guideId, String adZoneName) throws Exception {
        final String URL_ADD_AD_ZONE = "http://pub.alimama.com/common/adzone/selfAdzoneCreate.json";
        final String PARAMS_ADD_AD_ZONE = "tag=29&" +
                "siteid=" + guideId + "&" +
                "t=" + System.currentTimeMillis() + "&" +
                "newadzonename=" + adZoneName + "&" +
                "gcid=8&" +
                "_tb_token_=" + getTaoBaoTokenFormCookie(cookie) + "&" +
                "selectact=add";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL_ADD_AD_ZONE + "?" + PARAMS_ADD_AD_ZONE);
        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            Map rootMap = JSON.parseObject(resultJson, Map.class);
            Map<String, Object> dataMap = (Map<String, Object>) rootMap.get("data");
            Map<String, Object> infoMap = (Map<String, Object>) rootMap.get("info");
            if (Boolean.parseBoolean(infoMap.get("ok").toString())) {
                AdZoneEntity adZoneEntity = new AdZoneEntity(dataMap.get("siteId").toString(), dataMap.get("adzoneId").toString(), adZoneName);
                return adZoneEntity;
            } else {
                logger.error("getBrandList 执行错误，返回的信息是：" + resultJson);
                return null;
            }
        } else {
            logger.error("createGuide 执行错误，仅仅得到网络响应码是：" + closeableHttpResponse.getStatusLine().getStatusCode());
            return null;
        }
    }

    private static String getTaoBaoTokenFormCookie(String cookie) {
        String taoBaoToken = null;
        if (cookie != null) {
            String[] split = cookie.split(";");
            if (split != null) {
                for (String item : split) {
                    if (item.contains("_tb_token_")) {
                        String[] split1 = item.split("=");
                        if (split1.length > 1) {
                            taoBaoToken = split1[1];
                        }
                        break;
                    }
                }
            }
        }
        return taoBaoToken;
    }
}
