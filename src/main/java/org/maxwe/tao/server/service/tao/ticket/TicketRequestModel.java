package org.maxwe.tao.server.service.tao.ticket;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-17 13:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TicketRequestModel extends BaseTaobaoRequest<TicketResponseModel> {
    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_TAO_TICKET_GET;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();

        return map;
    }

    @Override
    public Class<TicketResponseModel> getResponseClass() {
        return TicketResponseModel.class;
    }
}
