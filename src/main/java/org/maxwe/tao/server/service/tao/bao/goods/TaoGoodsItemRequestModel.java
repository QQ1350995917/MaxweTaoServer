package org.maxwe.tao.server.service.tao.bao.goods;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-23 16:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsItemRequestModel extends BaseTaobaoRequest<TaoGoodsItemResponseModel> {
    private String fields = "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,nick,seller_id,volume";
    private long platform = 1;
    private String num_iids;

    public TaoGoodsItemRequestModel() {
        super();
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
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

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public Class<TaoGoodsItemResponseModel> getResponseClass() {
        return TaoGoodsItemResponseModel.class;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("fields",this.getFields());
        map.put("platform",this.getPlatform() + "");
        map.put("num_iids",this.getNum_iids());
        return map;
    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_GOODS_ITEM;
    }
}
