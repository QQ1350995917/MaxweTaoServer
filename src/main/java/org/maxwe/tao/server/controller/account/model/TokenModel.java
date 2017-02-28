package org.maxwe.tao.server.controller.account.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.codec.binary.Base64;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.CryptionUtils;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-01-09 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TokenModel implements Serializable {
    private String t;
    private int id;
    private String cellphone;
    private String verification;//敏感操作的验证密码
    private int apt; // 登录类型
    @JSONField(serialize = false)
    private String sign;

    public TokenModel() {
        super();
    }

    public TokenModel(String t, int id, String cellphone) {
        this.t = t;
        this.id = id;
        this.cellphone = cellphone;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getApt() {
        return apt;
    }

    public void setApt(int apt) {
        this.apt = apt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "SessionModel{" +
                ", id='" + id + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", verification='" + "******" + '\'' +
                ", apt=" + apt +
                '}';
    }

    @JSONField(serialize = false)
    public String getEncryptSing() throws Exception {
        if (this.getId() == 0 || !CellPhoneUtils.isCellphone(this.getCellphone())) {
            return null;
        }
        String password = (this.getCellphone() + new StringBuffer(this.getCellphone()).reverse()).substring(1, 17);//生成的ID是11位，补全16位密码

        String content = this.getId() + "-" + System.currentTimeMillis() + "-" + this.getCellphone();
        String encodeContent = new String(Base64.encodeBase64(content.getBytes()));
        byte[] encryptResult = CryptionUtils.encryptCustomer(encodeContent, password);
        String encryptResultStr = CryptionUtils.parseByte2HexStr(encryptResult);
        return encryptResultStr;
    }

    @JSONField(serialize = false)
    public boolean isDecryptSignOK() throws Exception {
        String password = (this.getCellphone() + new StringBuffer(this.getCellphone()).reverse()).substring(1, 17);//生成的ID是11位，补全16位密码
        byte[] decryptResult = CryptionUtils.decryptCustomer(CryptionUtils.parseHexStr2Byte(this.getSign()), password);
        byte[] decode = Base64.decodeBase64(decryptResult);
        String[] split = new String(decode).split("-");
        if (split == null || split.length != 3) {
            return false;
        }
        if (StringUtils.equals(split[0], this.getId() + "")
                && System.currentTimeMillis() - Long.parseLong(split[1]) < 60 * 1000
                && StringUtils.equals(split[2], this.getCellphone())
                ) {
            return true;
        }
        return false;
    }

    @JSONField(serialize = false)
    public boolean isTokenParamsOk() {
        if (!StringUtils.isEmpty(this.getT())
                && !StringUtils.isEmpty(this.getId() + "")
                && CellPhoneUtils.isCellphone(this.getCellphone())
                && !StringUtils.isEmpty(this.getSign())
                && (this.getApt() == 1 || this.getApt() == 2)) {
            return true;
        } else {
            return false;
        }
    }
}
