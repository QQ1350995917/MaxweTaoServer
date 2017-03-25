package org.maxwe.tao.server.service.tao.alimama.shop;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.maxwe.tao.server.controller.tao.model.alimama.AuctionRequestModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-03-24 16:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取店铺中定向推广服务
 */
public class ShopServices {

    public static boolean applyCampaignByOneKey(AuctionRequestModel requestModel,String reason) throws Exception {
        List<CampaignEntity> campaignEntities = ShopServices.queryCampaignByGoodsId(new GoodsCampaignQueryRequestModel(requestModel.getAuctionid(), requestModel.getCookie()));
        if (campaignEntities == null || campaignEntities.size() < 1) {
            return false;
        }
        CampaignEntity currentCommission = null;
        for (CampaignEntity campaignEntity : campaignEntities) {
            if (currentCommission == null || currentCommission.getCommissionRate() < campaignEntity.getCommissionRate()) {
                currentCommission = campaignEntity;
            }
        }
        if (currentCommission.getCommissionRate() > 0f) {
            AskForCampaignRequestModel askForCampaignRequestModel = new AskForCampaignRequestModel(currentCommission.getCampaignID(),
                    currentCommission.getShopKeeperID(), reason, requestModel.getCookie());
            boolean askForCampaign = askForCampaign(askForCampaignRequestModel);
            if (!askForCampaign){
                return false;
            }
        }

//        CampaignDetailRequestModel campaignDetailRequestModel = new CampaignDetailRequestModel(currentCommission.getCampaignID(),
//                currentCommission.getShopKeeperID(), requestModel.getCookie());
//        String oriMemberid = queryCampaignDetail(campaignDetailRequestModel);
//        if (StringUtils.isEmpty(oriMemberid)){
//            return false;
//        }

        return true;
    }

    /**
     * 第一步
     * 通过商品的ID查询推广活动
     * @param requestModel
     * @return
     * @throws Exception
     */
    private static List<CampaignEntity> queryCampaignByGoodsId(GoodsCampaignQueryRequestModel requestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestModel.getUrl());
        httpGet.setHeader("Cookie", requestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            GoodsCampaignQueryResponseModel responseModel = JSON.parseObject(resultJson, GoodsCampaignQueryResponseModel.class);
            if (responseModel != null) {
                List<CampaignEntity> data = responseModel.getData();
                if (data != null) {
                    return data;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }


    /**
     * 第二步
     * 申请加入推广活动
     * @param requestModel
     * @return
     * @throws Exception
     */
    private static boolean askForCampaign(AskForCampaignRequestModel requestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpGet = new HttpPost(requestModel.getUrl());
        httpGet.setHeader("Cookie", requestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            AskForCampaignResponseModel responseModel = JSON.parseObject(resultJson, AskForCampaignResponseModel.class);
            if (responseModel != null && responseModel.isOk()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 第三步
     * 查询推广活动的原始发起人信息
     * 为第四步做准备
     * @param requestModel
     * @return
     * @throws Exception
     */
    private static String queryCampaignDetail(CampaignDetailRequestModel requestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestModel.getUrl());
        httpGet.setHeader("Cookie", requestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            Map<String, Object> responseModel = JSON.parseObject(resultJson, Map.class);
            if (responseModel != null &&
                    responseModel.get("data") != null) {
                String oriMemberid = ((Map<String, Object>) responseModel.get("data")).get("oriMemberid").toString();
                if (StringUtils.isEmpty(oriMemberid)) {
                    return null;
                } else {
                    return oriMemberid;
                }
            } else {
                return null;
            }
        }
        return null;
    }


    /**
     * 第四步
     * 获取链接
     * @param requestModel
     * @return
     * @throws Exception
     */
    private static ShopCodeResponseModel getShopCode(ShopCodeRequestModel requestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestModel.getUrl());
        httpGet.setHeader("Cookie", requestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
            ShopCodeResponseModel responseModel = JSON.parseObject(resultJson, ShopCodeResponseModel.class);
            if (responseModel != null && responseModel.isOk()) {
                return null;
            } else {
                return responseModel;
            }
        } else {
            return null;
        }
    }
}
