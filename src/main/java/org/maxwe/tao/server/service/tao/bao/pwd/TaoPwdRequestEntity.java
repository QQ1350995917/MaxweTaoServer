package org.maxwe.tao.server.service.tao.bao.pwd;

/**
 * Created by Pengwei Ding on 2017-02-18 10:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoPwdRequestEntity {
    private String ext;//可选 {"xx":"xx"}扩展字段JSON格式
    private String logo;//可选 http://m.taobao.com/xxx.jpg口令弹框logoURL
    private String text;//必须 超值活动，惊喜活动多多口令弹框内容
    private String url;//必须 http://m.taobao.com口令跳转url
    private long user_id;//可选 24234234234


    public TaoPwdRequestEntity() {
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
