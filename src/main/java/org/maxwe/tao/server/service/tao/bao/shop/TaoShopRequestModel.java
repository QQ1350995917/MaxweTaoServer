package org.maxwe.tao.server.service.tao.bao.shop;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-19 14:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoShopRequestModel extends BaseTaobaoRequest<TaoShopResponseModel> {
    private String fields = "user_id,shop_title,shop_type,seller_nick,pict_url,shop_url";//	String	必须	user_id,shop_title,shop_type,seller_nick,pict_url,shop_url		需返回的字段列表
    private long user_id;//	Number	必须	123		卖家Id
    private long count;//	Number	可选	20		返回数量，默认20，最大值40
    private long platform;//	Number	可选	1		链接形式：1：PC，2：无线，默认：１

    public TaoShopRequestModel() {
        super();
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPlatform() {
        return platform;
    }

    public void setPlatform(long platform) {
        this.platform = platform;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_SHOP;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("fields",this.fields);
        map.put("user_id",this.getUser_id() + "");
        map.put("count",this.getCount() + "");
        map.put("platform",this.platform + "");
        return map;
    }

    @Override
    public Class<TaoShopResponseModel> getResponseClass() {
        return TaoShopResponseModel.class;
    }
}
