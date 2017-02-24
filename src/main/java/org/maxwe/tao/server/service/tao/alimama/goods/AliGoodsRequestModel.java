package org.maxwe.tao.server.service.tao.alimama.goods;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by Pengwei Ding on 2017-02-24 20:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 * <p>
 * cookie2=020ced2ee06335cb909b62b67432a9ec; _tb_token_=4I8mOHSE8Oq; t=ebacd4901230296718e49324fbef0fa2; v=0; cna=LuE1EdZ/MW4CAXYasAbrYIvg; cookie32=92564623f2a60c482fcf302df67f18d4; cookie31=MTIwMTM0NjIzLHd3d19kaW5nLHd3dy5kaW5ncGVuZ3dlaUBmb3htYWlsLmNvbSxUQg%3D%3D; alimamapwag=TW96aWxsYS81LjAgKExpbnV4OyBBbmRyb2lkIDcuMDsgTmV4dXMgNiBCdWlsZC9OQkQ5MFo7IHd2KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBWZXJzaW9uLzQuMCBDaHJvbWUvNTEuMC4yNzA0LjkwIE1vYmlsZSBTYWZhcmkvNTM3LjM2; login=UtASsssmOIJ0bQ%3D%3D; alimamapw=FhFHagFZWVAwB1dcVw5RVA5VA1MHDFdTUQdaBFULUFoBVAgDVVdUUAc%3D
 */
public class AliGoodsRequestModel {
    private static final String URL = "http://pub.alimama.com/items/search.json?";

    private long toPage = 1;
    private long perPageSize = 20;
    private String q;
    //    @JSONField(serialize=false)
//    private transient long t = System.currentTimeMillis();
    private String _tb_token_;
    private int queryType = 1;
    private String cookie;

    public AliGoodsRequestModel() {
        super();
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
        this.q = q;
    }

//    public long getT() {
//        return t;
//    }
//
//    public void setT(long t) {
//        this.t = t;
//    }

    public String get_tb_token_() {
        return _tb_token_;
    }

    public void set_tb_token_(String _tb_token_) {
        this._tb_token_ = _tb_token_;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUrl() {
        if (StringUtils.isEmpty(this.getQ()) || !this.getQ().startsWith("http")) {
            return URL + "toPage=" + getToPage() +
                    "&perPageSize=" + getPerPageSize();
        } else {
            return URL + "toPage=" + getToPage() +
                    "&perPageSize=" + getPerPageSize() +
                    "&q=" + getQ();
        }

    }

    public boolean isParamsOk() {
        if (this.getToPage() < 0 ||
                this.getPerPageSize() < 0 ||
                this.getPerPageSize() > 100) {
            return false;
        }

        if (StringUtils.isEmpty(this.getCookie())) {
            return false;
        } else {
            String[] split = this.getCookie().split(";");
            for (String looper : split) {
                if (looper.contains("_tb_token_")) {
                    this.set_tb_token_(looper.replace("_tb_token_=", ""));
                    break;
                }
            }
            if (StringUtils.isEmpty(this.get_tb_token_())) {
                return false;
            }
            return true;
        }
    }
}
