package org.maxwe.tao.server.service.tao.bao.convert;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-19 15:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoConvertRequestModel extends BaseTaobaoRequest<TaoConvertResponseModel> {
    private String fields;//	String	必须	num_iid,click_url		需返回的字段列表
    private String num_iids;//	String	必须	123,456		商品ID串，用','分割，从taobao.tbk.item.get接口获取num_iid字段，最大40个
    private long adzone_id;//	Number	必须	123		广告位ID，区分效果位置
    private long platform = 1;//	Number	可选	123 默认值：1 链接形式：1：PC，2：无线，默认：１
    private String unid;//	String	可选	demo		自定义输入串，英文和数字组成，长度不能大于12个字符，区分不同的推广渠道

    public TaoConvertRequestModel() {
        super();
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getNum_iids() {
        return num_iids;
    }

    public void setNum_iids(String num_iids) {
        this.num_iids = num_iids;
    }

    public long getAdzone_id() {
        return adzone_id;
    }

    public void setAdzone_id(long adzone_id) {
        this.adzone_id = adzone_id;
    }

    public long getPlatform() {
        return platform;
    }

    public void setPlatform(long platform) {
        this.platform = platform;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_CONVERT;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("fields",this.getFields());
        map.put("num_iids",this.getNum_iids());
        map.put("adzone_id",this.getAdzone_id() + "");
        map.put("platform",this.getPlatform() + "");
        map.put("unid",this.getUnid());
        return map;
    }

    @Override
    public Class<TaoConvertResponseModel> getResponseClass() {
        return TaoConvertResponseModel.class;
    }
}
