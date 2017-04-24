package org.maxwe.tao.server.controller.meta;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.IPUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.interceptor.AppInterceptor;
import org.maxwe.tao.server.service.meta.IMetaServices;
import org.maxwe.tao.server.service.meta.MetaServices;
import org.maxwe.tao.server.service.meta.SpecialLinkEntity;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 22:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class MetaController extends Controller implements IMetaController {
    private final Logger logger = Logger.getLogger(MetaController.class.getName());
    private static IMetaServices iMetaServices = new MetaServices();

    private static LinkedList<SpecialLinkEntity> currentSpecialLinkEntities = new LinkedList<>();
    static{
        currentSpecialLinkEntities.addAll(iMetaServices.retrieveSpecialLinks(0,1000));
    }

    public static boolean isSpecialLink(String url){
        Iterator<SpecialLinkEntity> iterator = currentSpecialLinkEntities.iterator();
        while (iterator.hasNext()){
            SpecialLinkEntity specialLinkEntity = iterator.next();
            boolean startsWith = url.startsWith(specialLinkEntity.getUrl());
            if (startsWith){
                return true;
            }
        }
        return false;
    }

    public static boolean removeSpecialLink(int id){
        Iterator<SpecialLinkEntity> iterator = currentSpecialLinkEntities.iterator();
        while (iterator.hasNext()){
            SpecialLinkEntity specialLinkEntity = iterator.next();
            if (id == specialLinkEntity.getId()){
                return true;
            }
        }
        return false;
    }

    public static void addSpecialLink(SpecialLinkEntity specialLinkEntity){
        currentSpecialLinkEntities.add(specialLinkEntity);
    }


    @Override
    @Before({AppInterceptor.class})
    public void smsCode() {
        String params = this.getAttr("p");
        SMSCodeRequestModel requestModel = JSON.parseObject(params, SMSCodeRequestModel.class);
        sendSmsCode(requestModel);
    }

    /**
     * 供网页调用的短信发送接口
     */
    public void smscode() {
        String cellphone = this.getPara("cellphone");
        SMSCodeRequestModel requestModel = new SMSCodeRequestModel();
        requestModel.setCellphone(cellphone);
        sendSmsCode(requestModel);
    }

    private void sendSmsCode(SMSCodeRequestModel requestModel) {
        if (requestModel == null || !requestModel.isSMSCodeRequestParamsOk()) {
            this.logger.info("smsCode : 请求参数错误 " + requestModel.toString());
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_BAD_PARAMS);
            smsCodeResponseModel.setMessage("请输入正确的手机号码");
            renderJson(JSON.toJSONString(smsCodeResponseModel, TokenModel.valueFilter));
            return;
        }

        if (SMSManager.isCacheAddress(IPUtils.getIpAddress(this.getRequest()))) {
            this.logger.info("smsCode : 请求频繁 " + requestModel.toString());
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_TO_MANY);
            smsCodeResponseModel.setMessage("网络忙，请稍后");
            renderJson(JSON.toJSONString(smsCodeResponseModel, TokenModel.valueFilter));
            return;
        }

        try {
            SMSManager.sendSMS(requestModel.getCellphone());
            this.logger.info(
                "smsCode : 验证码" + SMSManager.getSMSCode(requestModel.getCellphone()) + "已经发送 "
                    + requestModel.toString());
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_SUCCESS);
            smsCodeResponseModel.setMessage("发送成功");
            renderJson(JSON.toJSONString(smsCodeResponseModel, TokenModel.valueFilter));
        } catch (Exception e) {
            this.logger
                .error("smsCode : 验证码发送错误 " + requestModel.toString() + "\r\n" + e.getMessage());
            e.printStackTrace();
            SMSCodeResponseModel smsCodeResponseModel = new SMSCodeResponseModel(requestModel);
            smsCodeResponseModel.setCode(ResponseModel.RC_SERVER_ERROR);
            smsCodeResponseModel.setMessage("发送失败");
            renderJson(JSON.toJSONString(smsCodeResponseModel, TokenModel.valueFilter));
        }
    }

    @Override
    public void createLink() {
        String url = this.getPara("url");
        if (StringUtils.isEmpty(url) || url.contains("?")) {
            this.logger.info("createLink create: 参数错误：" + url);
            renderError(500);
            return;
        } else {
            SpecialLinkEntity specialLink =
                iMetaServices.createSpecialLink(new SpecialLinkEntity(url));
            if (specialLink == null) {
                this.logger.info("createLink create: 服务器错误：" + url);
                renderError(500);
            } else {
                addSpecialLink(specialLink);
                this.logger.info("createLink create: 服务器错误：" + url);
                int pageIndex =
                    this.getParaToInt("pageIndex") == null ? 0 : this.getParaToInt("pageIndex");
                int pageSize =
                    this.getParaToInt("pageSize") == null ? 12 : this.getParaToInt("pageSize");
                listLinks(pageIndex, pageSize);
            }
        }
    }

    @Override
    public void deleteLink() {
        int id = this.getParaToInt("id") == null ? -1 : this.getParaToInt("id");
        if (id < 0) {
            return;
        }
    }

    @Override
    public void listLinks() {
        int pageIndex = this.getParaToInt("pageIndex") == null ? 0 : this.getParaToInt("pageIndex");
        int pageSize = this.getParaToInt("pageSize") == null ? 12 : this.getParaToInt("pageSize");
        listLinks(pageIndex, pageSize);
    }

    private void listLinks(int pageIndex, int pageSize) {
        LinkedList<SpecialLinkEntity> specialLInkEntities =
            iMetaServices.retrieveSpecialLinks(pageIndex, pageSize);
        int accountsSum = iMetaServices.specialLinksCount();
        this.setAttr("links", specialLInkEntities);
        this.setAttr("pages", accountsSum / pageSize + (accountsSum % pageSize == 0 ? 0 : 1));
        this.setAttr("pageIndex", pageIndex);
        render("/webapp/widgets/managerSpecialLinkList.view.html");
    }
}
