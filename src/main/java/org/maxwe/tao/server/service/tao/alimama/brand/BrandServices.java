package org.maxwe.tao.server.service.tao.alimama.brand;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

    public static List<GuideEntity> createBrand(BrandCreateRequestModel requestModel) throws Exception {
        GuideEntity guide = createGuide(requestModel.getCookie(), requestModel.getGuideName(), requestModel.getWeChat());
        if (guide == null) {
            return null;
        }
        LinkedList<GuideEntity> guideEntities = new LinkedList<>();
        AdZoneEntity adZone = createADZone(requestModel.getCookie(), guide.getSiteId(), requestModel.getAdZoneName());
        if (adZone != null) {
            LinkedList<AdZoneEntity> adZoneEntities = new LinkedList<>();
            adZoneEntities.add(adZone);
            guide.setAdZones(adZoneEntities);
        }
        guideEntities.add(guide);
        return guideEntities;
    }

    private static GuideEntity createGuide(String cookie, String guideName, String weChat) throws Exception {
        final String URL_ADD_GUIDE = "http://pub.alimama.com/common/site/generalize/guideAdd.json";
        final String PARAMS_ADD_GUIDE = "name=" + guideName + "&categoryId=14&account1=" + weChat;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL_ADD_GUIDE + "?" + PARAMS_ADD_GUIDE);
        httpGet.setHeader("Cookie", cookie);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            Map rootMap = JSON.parseObject(resultJson, Map.class);
            Map<String, Object> dataMap = (Map<String, Object>) rootMap.get("data");
            List<Map<String, Object>> adZoneList = (List<Map<String, Object>>) dataMap.get("guideList");
            if (adZoneList != null && adZoneList.size() > 0) {
                Map<String, Object> stringObjectMap = adZoneList.get(0);
                String name = stringObjectMap.get("name").toString();
                String guideId = stringObjectMap.get("guideID").toString();
                if (guideName.equals(name)) {
                    GuideEntity guideEntity = new GuideEntity(guideId, name);
                    return guideEntity;
                } else {
                    logger.error("getBrandList 执行错误，提交的名称是：" + guideName + " 返回的名称是：" + name + "返回的信息是：" + resultJson);
                    return null;
                }
            } else {
                logger.error("getBrandList 执行错误，返回的信息是：" + resultJson);
                return null;
            }
        } else {
            logger.error("createGuide 执行错误，仅仅得到网络响应码是：" + closeableHttpResponse.getStatusLine().getStatusCode());
            return null;
        }
    }

    private static AdZoneEntity createADZone(String cookie, String guideId, String adZoneName) throws Exception {
        final String URL_ADD_AD_ZONE = "http://pub.alimama.com/common/site/generalize/guideAdd.json";
        final String PARAMS_ADD_AD_ZONE = "tag=29&" +
                "siteid=" + guideId + "&" +
                "t=" + System.currentTimeMillis() + "&" +
                "newadzonename=" + adZoneName + "&" +
                "gcid=8&" +
                "_tb_token_=" + getTaoBaoTokenFormCookie(cookie) + "&" +
                "selectact=add";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL_ADD_AD_ZONE + "?" + PARAMS_ADD_AD_ZONE);
        httpGet.setHeader("Cookie", cookie);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
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
