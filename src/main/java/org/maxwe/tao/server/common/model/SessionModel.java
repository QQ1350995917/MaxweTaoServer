package org.maxwe.tao.server.common.model;

import com.alibaba.druid.util.StringUtils;
import org.maxwe.tao.server.common.utils.CellPhoneUtils;
import org.maxwe.tao.server.common.utils.MarkUtils;
import org.maxwe.tao.server.common.utils.CryptionUtils;

import java.io.Serializable;
import java.util.Base64;

/**
 * Created by Pengwei Ding on 2017-01-09 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public abstract class SessionModel implements Serializable {
    private String t;
    private String mark;
    private String cellphone;
    private String sign;

    public SessionModel() {
        super();
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEncryptSing() {
        if (StringUtils.isEmpty(this.getMark()) || StringUtils.isEmpty(this.getCellphone())){
            return null;
        }
        String password = MarkUtils.deMark(this.getMark());
        String content = this.getMark() + "-" + System.currentTimeMillis() + "-" + this.getCellphone();
        String encodeContent = new String(Base64.getEncoder().encode(content.getBytes()));
        byte[] encryptResult = CryptionUtils.encrypt(encodeContent, password);
        String encryptResultStr = CryptionUtils.parseByte2HexStr(encryptResult);
        return encryptResultStr;
    }

    public boolean isDecryptSignOK() {
        String password = MarkUtils.deMark(this.getMark());
        byte[] decryptResult = CryptionUtils.decrypt(CryptionUtils.parseHexStr2Byte(this.getSign()), password);
        byte[] decode = Base64.getDecoder().decode(decryptResult);
        String[] split = new String(decode).split("-");
        if (split == null || split.length != 3) {
            return false;
        }
        if (StringUtils.equals(split[0], this.getMark())
                && System.currentTimeMillis() - Long.parseLong(split[1]) < 15000
                && StringUtils.equals(split[2], this.getCellphone())
                ) {
            return true;
        }
        return false;
    }

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

    public boolean isParamsOk() {
        return isSessionParamsOk();
    }


}
