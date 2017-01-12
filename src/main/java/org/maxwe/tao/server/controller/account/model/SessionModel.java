package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.CryptionUtils;
import org.maxwe.tao.server.common.utils.MarkUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SessionModel implements Serializable {
    private String t;
    private String mark;
    private String cellphone;
    private String verification;//敏感操作的验证密码
    @JSONField(serialize=false)
    private String sign;

    public SessionModel() {
        super();
    }

    public SessionModel(String t, String mark, String cellphone) {
        this.t = t;
        this.mark = mark;
        this.cellphone = cellphone;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @JSONField(serialize=false)
    public String getEncryptSing() throws Exception {
        if (StringUtils.isEmpty(this.getMark()) || StringUtils.isEmpty(this.getCellphone())){
            return null;
        }
        String deMark = MarkUtils.deMark(this.getMark());//生成的ID是11位
        String password = (deMark + deMark).substring(0, 16);//生成的ID是11位，补全16位密码
        String content = this.getMark() + "-" + System.currentTimeMillis() + "-" + this.getCellphone();
        String encodeContent = new String(new BASE64Encoder().encode(content.getBytes()));
        byte[] encryptResult = CryptionUtils.encryptCustomer(encodeContent, password);
        String encryptResultStr = CryptionUtils.parseByte2HexStr(encryptResult);
        return encryptResultStr;
    }

    @JSONField(serialize=false)
    public boolean isDecryptSignOK() throws Exception {
        String deMark = MarkUtils.deMark(this.getMark());
        String password = (deMark + deMark).substring(0, 16);
        byte[] decryptResult = CryptionUtils.decryptCustomer(CryptionUtils.parseHexStr2Byte(this.getSign()), password);
        byte[] decode = new BASE64Decoder().decodeBuffer(new String(decryptResult));
        String[] split = new String(decode).split("-");
        if (split == null || split.length != 3) {
            return false;
        }
        if (StringUtils.equals(split[0], this.getMark())
                && System.currentTimeMillis() - Long.parseLong(split[1]) < 60 * 1000
                && StringUtils.equals(split[2], this.getCellphone())
                ) {
            return true;
        }
        return false;
    }

    @JSONField(serialize=false)
    private boolean isSessionParamsOk() {
        if (!StringUtils.isEmpty(this.getT())
                && !StringUtils.isEmpty(this.getMark())
                && CellPhoneUtils.isCellphone(this.getCellphone())
                && !StringUtils.isEmpty(this.getSign())) {
            return true;
        } else {
            return false;
        }
    }

    @JSONField(serialize=false)
    public boolean isParamsOk() {
        return isSessionParamsOk();
    }


}
