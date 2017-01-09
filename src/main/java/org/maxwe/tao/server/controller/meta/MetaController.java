package org.maxwe.tao.server.controller.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.core.Controller;
import com.taobao.api.ApiException;
import org.apache.log4j.Logger;
import org.maxwe.tao.server.common.response.IResultSet;
import org.maxwe.tao.server.common.response.ResultSet;
import org.maxwe.tao.server.common.sms.SMSManager;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.IPUtils;
import org.maxwe.tao.server.service.account.agent.AgentEntity;

/**
 * Created by Pengwei Ding on 2017-01-09 22:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MetaController extends Controller implements IMetaController {
    private final Logger logger = Logger.getLogger(MetaController.class.getName());

    @Override
    public void smsCode() {
        String params = this.getPara("p");
        SMSCodeModel requestModel = JSON.parseObject(params, SMSCodeModel.class);
        IResultSet iResultSet = new ResultSet();
        if (requestModel == null || !CellPhoneUtils.isCellphone(requestModel.getCellphone())) {
            this.logger.info("smsCode : 请求参数错误 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestModel);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));
            return;
        }

        if (SMSManager.isCacheAddress(IPUtils.getIpAddress(this.getRequest()))) {
            this.logger.info("smsCode : 请求频繁 " + params);
            iResultSet.setCode(IResultSet.ResultCode.RC_TO_MANY.getCode());
            iResultSet.setData(requestModel.getCellphone());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(AgentEntity.class, "t")));
            return;
        }

        try {
            SMSManager.sendSMS(requestModel.getCellphone());
            this.logger.info("smsCode : 验证码" + SMSManager.getSMSCode(requestModel.getCellphone()) + "已经发送 " + params);
        } catch (ApiException e) {
            this.logger.error("smsCode : 验证码发送错误 " + params + "\r\n" + e.getMessage());
            e.printStackTrace();
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestModel);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VAgentEntity.class, "cellphone")));

    }
}
