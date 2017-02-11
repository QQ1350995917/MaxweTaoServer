package org.maxwe.tao.server.service.api.goods;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-11 09:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 详情见 http://open.taobao.com/docs/api.htm?spm=a219a.7386797.0.0.FmOkHP&apiId=122
 */
public class CategoryRequestModel extends BaseTaobaoRequest<GoodsResponseModel> {
    private String cids;// 特殊可选	18957,19562 最大列表长度：1000 商品所属类目ID列表，用半角逗号(,)分隔 例如:(18957,19562,) (cids、parent_cid至少传一个)
    private String fields;// 可选 cid,parent_cid,name,is_parent 默认值：cid,parent_cid,name,is_parent 最大列表长度：20 需要返回的字段列表，见ItemCat，默认返回：cid,parent_cid,name,is_parent；增量类目信息,根据fields传入的参数返回相应的结果。 features字段： 1、如果存在attr_key=freeze表示该类目被冻结了，attr_value=0,5，value可能存在2个值（也可能只有1个），用逗号分割，0表示禁编辑，5表示禁止发布
    private int parent_cid;//特殊可选 	50011999 父商品类目 id，0表示根节点, 传输该参数返回所有子类目。 (cids、parent_cid至少传一个)
    private String methodName;

    public String getCids() {
        return cids;
    }

    public void setCids(String cids) {
        this.cids = cids;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public int getParent_cid() {
        return parent_cid;
    }

    public void setParent_cid(int parent_cid) {
        this.parent_cid = parent_cid;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public Class<GoodsResponseModel> getResponseClass() {
        return GoodsResponseModel.class;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("cids", this.getCids());
        map.put("fields",this.getFields());
        map.put("parent_cid",this.getParent_cid() + "");
        return map;
    }

    @Override
    public String getApiMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
