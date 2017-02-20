package org.maxwe.tao.server.service.tao.bao.category;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-19 10:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 获取后台供卖家发布商品的标准商品类目
 * http://open.taobao.com/docs/api.htm?spm=a219a.7386797.0.0.hMvsH5&apiId=122
 */
public class TaoCategoryRequestModel extends BaseTaobaoRequest<TaoCategoryResponseModel> {
    private long cids;//	Number []	特殊可选	18957,19562 最大列表长度：1000 商品所属类目ID列表，用半角逗号(,)分隔 例如:(18957,19562,) (cids、parent_cid至少传一个)
    private String fields;//	String []	可选	cid,parent_cid,name,is_parent 默认值：cid,parent_cid,name,is_parent 最大列表长度：20 需要返回的字段列表，见ItemCat，默认返回：cid,parent_cid,name,is_parent；增量类目信息,根据fields传入的参数返回相应的结果。 features字段： 1、如果存在attr_key=freeze表示该类目被冻结了，attr_value=0,5，value可能存在2个值（也可能只有1个），用逗号分割，0表示禁编辑，5表示禁止发布
    private long parent_cid;// Number	特殊可选	50011999 父商品类目 id，0表示根节点, 传输该参数返回所有子类目。 (cids、parent_cid至少传一个)

    public TaoCategoryRequestModel() {
        super();
    }

    public long getCids() {
        return cids;
    }

    public void setCids(long cids) {
        this.cids = cids;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public long getParent_cid() {
        return parent_cid;
    }

    public void setParent_cid(long parent_cid) {
        this.parent_cid = parent_cid;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public Class<TaoCategoryResponseModel> getResponseClass() {
        return TaoCategoryResponseModel.class;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("cids", this.getCids() + "");
        map.put("fields", this.getFields());
        map.put("parent_cid", this.getParent_cid() + "");
        return map;
    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_CATEGORY;
    }

}
