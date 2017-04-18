package org.maxwe.tao.client.goods;

import org.apache.http.util.TextUtils;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-04-15 17:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsRequestModel {
    private static final String URL0 = "http://pub.alimama.com/items/search.json?";//普通链接
    private static final String URL1 = "http://pub.alimama.com/items/channel/qqhd.json?";//高佣金链接
    private String URL = URL0; // 客户端忽略该属性
    private long toPage = 1;// 页面
    private long perPageSize = 12;// 页面数据量
    private String q; // 查询关键字
    private int queryType = 0;
    private String channel = "qqhd";
    private int sortType = 0;// 0:默认 1:佣金 2:优惠券 3:价格降低 4:价格升高 9:销量降序
    private int urlType = 0;//标记链接类型
    private String shopTag;// 店铺优惠券 0 无关，1有关
    private int dpyhq = 0;// 店铺优惠券 0 无关，1有关
    private int userType = -1;// 0 淘宝，1天猫

    public GoodsRequestModel() {
    }

    public GoodsRequestModel(Map<String,String> paramsMap) {
        if (paramsMap != null){
            if (paramsMap.containsKey("toPage")){
                this.toPage = Integer.parseInt(paramsMap.get("toPage"));
            }
            if (paramsMap.containsKey("perPageSize")){
                this.perPageSize = Integer.parseInt(paramsMap.get("perPageSize"));
            }
            if (paramsMap.containsKey("q")){
                this.q = paramsMap.get("q");
            }
            if (paramsMap.containsKey("queryType")){
                this.queryType = Integer.parseInt(paramsMap.get("queryType"));
            }
            if (paramsMap.containsKey("channel")){
                this.channel = paramsMap.get("channel");
            }
            if (paramsMap.containsKey("sortType")){
                this.sortType = Integer.parseInt(paramsMap.get("sortType"));
            }
            if (paramsMap.containsKey("urlType")){
                this.urlType = Integer.parseInt(paramsMap.get("urlType"));
            }
            if (paramsMap.containsKey("shopTag")){
                this.shopTag = paramsMap.get("shopTag");
            }
            if (paramsMap.containsKey("dpyhq")){
                this.dpyhq = Integer.parseInt(paramsMap.get("dpyhq"));
            }
            if (paramsMap.containsKey("userType")){
                this.userType = Integer.parseInt(paramsMap.get("userType"));
            }
        }
    }

    public String getURL() {
        if (urlType == 0) {
            URL = URL0;
        } else if (urlType == 1) {
            URL = URL1;
        }
        String result = null;
        if (TextUtils.isEmpty(this.getQ())) {
            result = URL + "toPage=" + getToPage() +
                    "&perPageSize=" + getPerPageSize() +
                    "&sortType=" + getSortType() +
                    "&userType=" + getUserType() +
                    "&dpyhq=" + getDpyhq();
        } else {
            result = URL + "toPage=" + getToPage() +
                    "&perPageSize=" + getPerPageSize() +
                    "&sortType=" + getSortType() +
                    "&dpyhq=" + getDpyhq() +
                    "&userType=" + getUserType() +
                    "&q=" + getQ();
        }
        if (this.getDpyhq() == 1) {
            result = result + "&shopTag=dpyhq";
        }
        return result;
    }

    public long getToPage() {
        return toPage;
    }

    public void setToPage(long toPage) {
        this.toPage = toPage;
    }

    public long getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(long perPageSize) {
        this.perPageSize = perPageSize;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        if (q.startsWith("http")) {
            this.q = clearQueryUrl(q);
        } else {
            this.q = q;
        }

    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getShopTag() {
        return shopTag;
    }

    public void setShopTag(String shopTag) {
        this.shopTag = shopTag;
    }

    public int getDpyhq() {
        return dpyhq;
    }

    public void setDpyhq(int dpyhq) {
        this.dpyhq = dpyhq;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * 清理url上的多余干扰信息
     *
     * @param url 原始URL
     * @return 清理后的URL
     */
    public static String clearQueryUrl(String url) {
        String[] splits = url.split("\\?");
        String id = null;
        String spm = null;
        if (splits != null && splits.length > 1) {
            String[] subSplits = splits[1].split("&");
            for (String split : subSplits) {
                if (split.startsWith("id=")) {
                    id = split;
                } else if (split.startsWith("spm=")) {
                    spm = split;
                }
            }
        }
        if (TextUtils.isEmpty(id) && !TextUtils.isEmpty(spm)) {
            url = url.replace(spm, "");
            if (url.contains("?&")) {
                return url.replace("?&", "?");
            }
        } else {
            return splits[0] + "?" + id;
        }
        return url;
    }
}
