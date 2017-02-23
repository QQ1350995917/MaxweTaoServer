package org.maxwe.tao.server.service.tao.bao.favorites;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-23 21:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoFavoritesRequestModel extends BaseTaobaoRequest<TaoFavouritesResponseModel> {
    private long page_no = 1;
    private long page_size =20;
    private String fields = "favorites_title,favorites_id,type";
    private long type = -1;

    public TaoFavoritesRequestModel() {
        super();
    }

    public long getPage_no() {
        return page_no;
    }

    public void setPage_no(long page_no) {
        this.page_no = page_no;
    }

    public long getPage_size() {
        return page_size;
    }

    public void setPage_size(long page_size) {
        this.page_size = page_size;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }


    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public Class<TaoFavouritesResponseModel> getResponseClass() {
        return TaoFavouritesResponseModel.class;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("fields",this.getFields());
        map.put("page_no",this.getPage_no() + "");
        map.put("page_size",this.getPage_size() + "");
        map.put("type",this.getType() + "");
        return map;
    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_GOODS_FAVOURITE;
    }
}
