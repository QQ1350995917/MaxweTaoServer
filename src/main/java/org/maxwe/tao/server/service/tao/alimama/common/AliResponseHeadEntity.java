package org.maxwe.tao.server.service.tao.alimama.common;

/**
 * Created by Pengwei Ding on 2017-02-24 20;//47.
 * Email;// www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description;// @TODO
 */
public class AliResponseHeadEntity {

    private String version;// "1.0",
    private String status;// "OK",
    private long pageSize;// 10,
    private long pageNo;// 1,
    private String searchUrl;// null,
    private String pvid;// "100_10.103.34.87_32741_1487939808719439276",
    private long docsfound;// 19844,
    private String errmsg;// null,
    private String fromcache;// null,
    private long processtime;// 13604,
    private long ha3time;// 0,
    private long docsreturn;// 10,
    private String responseTxt;// null

    public AliResponseHeadEntity() {
        super();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getPvid() {
        return pvid;
    }

    public void setPvid(String pvid) {
        this.pvid = pvid;
    }

    public long getDocsfound() {
        return docsfound;
    }

    public void setDocsfound(long docsfound) {
        this.docsfound = docsfound;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getFromcache() {
        return fromcache;
    }

    public void setFromcache(String fromcache) {
        this.fromcache = fromcache;
    }

    public long getProcesstime() {
        return processtime;
    }

    public void setProcesstime(long processtime) {
        this.processtime = processtime;
    }

    public long getHa3time() {
        return ha3time;
    }

    public void setHa3time(long ha3time) {
        this.ha3time = ha3time;
    }

    public long getDocsreturn() {
        return docsreturn;
    }

    public void setDocsreturn(long docsreturn) {
        this.docsreturn = docsreturn;
    }

    public String getResponseTxt() {
        return responseTxt;
    }

    public void setResponseTxt(String responseTxt) {
        this.responseTxt = responseTxt;
    }
}
