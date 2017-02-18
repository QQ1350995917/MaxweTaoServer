package org.maxwe.tao.server.service.tao;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-01-18 14:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoCommonModel implements ITaoModel {
    // API接口名称
    private String method;// 是
    // TOP分配给应用的AppKey
    // 这里要注意正式环境和沙箱环境的AppKey是不同的（包括AppSecret），使用时要注意区分；
    // 进入开放平台控制台“应用管理-概览” 和 “应用管理-沙箱环境管理”可分别查看正式环境及沙箱环境的AppKey、AppSecret
    private String app_key;// 是
    // 用户登录授权成功后，TOP颁发给应用的授权信息，详细介绍请点击这里。
    // 当此API文档的标签上注明：“需要授权”，则此参数必传；“不需要授权”，则此参数不需要传；“可选授权”，则此参数为可选。
    private String session; // 否
    // 时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2016-01-01 12:00:00。
    // 淘宝API服务端允许客户端请求最大时间误差为10分钟。
    private String timestamp; // 是
    // 响应格式。默认为xml格式，可选值：xml，json。
    private String format = "json"; // 否
    // API协议版本，可选值：2.0。
    private String v = "2.0"; // 是
    // 合作伙伴身份标识
    private String partner_id; // 否
    // 被调用的目标AppKey，仅当被调用的API为第三方ISV提供时有效。
    private String target_app_key; // 否
    // 是否采用精简JSON返回格式，仅当format=json时有效，默认值为：false。
    private boolean simplify = false; // 否
    // 签名的摘要算法，可选值为：hmac，md5。
    private String sign_method = "hmac"; // 是
    // API输入参数签名结果
    private String sign; // 是

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getTarget_app_key() {
        return target_app_key;
    }

    public void setTarget_app_key(String target_app_key) {
        this.target_app_key = target_app_key;
    }

    public boolean getSimplify() {
        return simplify;
    }

    public void setSimplify(boolean simplify) {
        this.simplify = simplify;
    }

    public String getSign_method() {
        return sign_method;
    }

    public void setSign_method(String sign_method) {
        this.sign_method = sign_method;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public static class Constants {
        public static String SIGN_METHOD_MD5 = "md5";
        public static String SIGN_METHOD_HMAC = "hmac";
        public static String CHARSET_UTF8 = "utf-8";
    }


    /**
     * 签名算法
     * 1:对所有API请求参数（包括公共参数和业务参数，但除去sign参数和byte[]类型的参数），根据参数名称的ASCII码表的顺序排序。如：foo=1, bar=2, foo_bar=3, foobar=4排序后的顺序是bar=2, foo=1, foo_bar=3, foobar=4。
     * 2:将排序好的参数名和参数值拼装在一起，根据上面的示例得到的结果为：bar2foo1foo_bar3foobar4。
     * 3:把拼装好的字符串采用utf-8编码，使用签名算法对编码后的字节流进行摘要。如果使用MD5算法，则需要在拼装的字符串前后加上app的secret后，再进行摘要，如：md5(secret+bar2foo1foo_bar3foobar4+secret)；如果使用HMAC_MD5算法，则需要用app的secret初始化摘要算法后，再进行摘要，如：hmac_md5(bar2foo1foo_bar3foobar4)。
     * 4:将摘要得到的字节流结果使用十六进制表示，如：hex(“helloworld”.getBytes(“utf-8”)) = “68656C6C6F776F726C64”
     *
     * @param params     参数
     * @param secret     app的secret
     * @param signMethod 签名算法名称
     * @return 签名后的字符串
     * @throws IOException
     */
    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (Constants.SIGN_METHOD_MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (key != null && !key.trim().equals("") && value != null && !value.trim().equals("")) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes = null;
        if (Constants.SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    public static byte[] encryptMD5(String data) throws IOException {
//        return encryptMD5(data.getBytes(Constants.CHARSET_UTF8));
        return null;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    @Override
    public String toString() {
        return "CommonModel{" +
                "sign='" + sign + '\'' +
                ", sign_method='" + sign_method + '\'' +
                ", simplify=" + simplify +
                ", target_app_key='" + target_app_key + '\'' +
                ", partner_id='" + partner_id + '\'' +
                ", v='" + v + '\'' +
                ", format='" + format + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", session='" + session + '\'' +
                ", app_key='" + app_key + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
