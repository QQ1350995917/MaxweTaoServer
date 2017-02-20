package org.maxwe.tao.server.service.tao.bao.goods;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-01-18 14:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsRequestModel extends BaseTaobaoRequest<TaoGoodsResponseModel> {

    // 需返回的字段列表
    private String fields = APIConstants.METHOD_NAME_GOODS_FIELDS; // 是
//    // 查询词
    private String q;// 否
//    // 后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
    private String cat = "0";// 否
    // 所在地
    private String itemloc;// 否
    // 排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi）
    private String sort;// 否
    // 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
    private boolean is_tmall = false;// 否
    // 是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
    private boolean is_overseas = false;// 否
    // 折扣价范围下限，单位：元
    private int start_price = 0000;// 否
    // 折扣价范围上限，单位：元
    private int end_price = 9999;// 否
    // 淘客佣金比率上限，如：1234表示12.34%
    private int start_tk_rate = 0000;// 否
    // 淘客佣金比率下限，如：1234表示12.34%
    private int end_tk_rate = 9999;// 否
    // 链接形式：1：PC，2：无线，默认：１
    private int platform = 2;// 否
    // 第几页，默认：１
    private int page_no = 1;// 否
//    // 页大小，默认20，1~100
    private int page_size = 20;// 否


    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getItemloc() {
        return itemloc;
    }

    public void setItemloc(String itemloc) {
        this.itemloc = itemloc;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean getIs_tmall() {
        return is_tmall;
    }

    public void setIs_tmall(boolean is_tmall) {
        this.is_tmall = is_tmall;
    }

    public boolean getIs_overseas() {
        return is_overseas;
    }

    public void setIs_overseas(boolean is_overseas) {
        this.is_overseas = is_overseas;
    }

    public int getStart_price() {
        return start_price;
    }

    public void setStart_price(int start_price) {
        this.start_price = start_price;
    }

    public int getEnd_price() {
        return end_price;
    }

    public void setEnd_price(int end_price) {
        this.end_price = end_price;
    }

    public int getStart_tk_rate() {
        return start_tk_rate;
    }

    public void setStart_tk_rate(int start_tk_rate) {
        this.start_tk_rate = start_tk_rate;
    }

    public int getEnd_tk_rate() {
        return end_tk_rate;
    }

    public void setEnd_tk_rate(int end_tk_rate) {
        this.end_tk_rate = end_tk_rate;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public Class<TaoGoodsResponseModel> getResponseClass() {
        return TaoGoodsResponseModel.class;
    }

    @Override
    public Map<String, String> getTextParams() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("fields",this.getFields());
        map.put("q",this.getQ());
        map.put("cat",this.getCat());
        map.put("itemloc",this.getItemloc());
        map.put("sort",this.getSort());
        map.put("is_tmall",this.getIs_tmall() + "");
        map.put("is_overseas",this.getIs_overseas() + "");
        map.put("start_price",this.getStart_price() + "");
        map.put("end_price",this.getEnd_price() + "");
        map.put("start_tk_rate",this.getStart_tk_rate() + "");
        map.put("end_tk_rate",this.getEnd_tk_rate() + "");
        map.put("platform",this.getPlatform() + "");
        map.put("page_no",this.getPage_no() + "");
        map.put("page_size",this.getPage_size() +"");
        return map;
    }

    @Override
    public String getApiMethodName() {
        return APIConstants.METHOD_NAME_GOODS;
    }

    @Override
    public String toString() {
        return super.toString() + "GoodsRequestModel{" +
                "page_size=" + page_size +
                ", page_no=" + page_no +
                ", platform=" + platform +
                ", end_tk_rate=" + end_tk_rate +
                ", start_tk_rate=" + start_tk_rate +
                ", end_price=" + end_price +
                ", start_price=" + start_price +
                ", is_overseas=" + is_overseas +
                ", is_tmall=" + is_tmall +
                ", sort='" + sort +
                ", itemloc='" + itemloc +
                ", cat='" + cat +
                ", q='" + q +
                ", fields='" + fields +
                '}';
    }
}
