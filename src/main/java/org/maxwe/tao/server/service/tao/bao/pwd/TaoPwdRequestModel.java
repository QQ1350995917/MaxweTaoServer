package org.maxwe.tao.server.service.tao.bao.pwd;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-16 11:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取淘口令
 */
public class TaoPwdRequestModel extends BaseTaobaoRequest<TaoPwdResponseModel> {
    private TaoPwdRequestEntity tpwd_param;

    public TaoPwdRequestModel() {
        super();
    }

    public TaoPwdRequestEntity getTpwd_param() {
        return tpwd_param;
    }

    public void setTpwd_param(TaoPwdRequestEntity tpwd_param) {
        this.tpwd_param = tpwd_param;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_TAO_PWD_GET;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("tpwd_param", JSON.toJSONString(this.tpwd_param));
        return map;
    }

    @Override
    public Class<TaoPwdResponseModel> getResponseClass() {
        return TaoPwdResponseModel.class;
    }
}
