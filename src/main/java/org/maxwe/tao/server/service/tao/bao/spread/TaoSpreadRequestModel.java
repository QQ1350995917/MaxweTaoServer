package org.maxwe.tao.server.service.tao.bao.spread;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-19 16:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoSpreadRequestModel extends BaseTaobaoRequest<TaoSpreadResponseModel> {

    private LinkedList<TaoSpreadEntity> requests;

    public TaoSpreadRequestModel() {
        super();
    }

    public LinkedList<TaoSpreadEntity> getRequests() {
        return requests;
    }

    public void setRequests(LinkedList<TaoSpreadEntity> requests) {
        this.requests = requests;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_SPREAD;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("requests", JSON.toJSONString(this.getRequests()));
        return map;
    }

    @Override
    public Class<TaoSpreadResponseModel> getResponseClass() {
        return TaoSpreadResponseModel.class;
    }
}
