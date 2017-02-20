package org.maxwe.tao.server.service.tao.bao.coupon;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-17 13:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoCouponRequestModel extends BaseTaobaoRequest<TaoCouponResponseModel> {
    private long platform = 1;//	Number	可选 	1 默认值：1  1：PC，2：无线，默认：1
    private String num_iids;//	String	必须	123,456		商品ID串，用逗号分割，从taobao.tbk.item.coupon.get接口获取num_iid字段，最大40个。（如果传入了没有优惠券的宝贝num_iid，则优惠券相关的字段返回为空，请做好容错）
    private String pid = "mm_123_123_123";//	String	必须	mm_123_123_123		三方pid，满足mm_xxx_xxx_xxx格式

    public TaoCouponRequestModel() {
        super();
    }

    public long getPlatform() {
        return platform;
    }

    public void setPlatform(long platform) {
        this.platform = platform;
    }

    public String getNum_iids() {
        return num_iids;
    }

    public void setNum_iids(String num_iids) {
        this.num_iids = num_iids;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

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
    public Class<TaoCouponResponseModel> getResponseClass() {
        return TaoCouponResponseModel.class;
    }
}
