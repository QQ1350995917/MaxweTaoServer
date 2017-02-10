/**
 * Created by dingpengwei on 2/9/17.
 */
function LOGIN(loginName, password) {
    var pwd = document.domain.substring(0,1) + document.domain.substring(4,5) + loginName.substring(2,3) + loginName.substring(2,5);
    var params = "loginName=" + loginName + "&password=" + password;
    document.getElementById("params").value = CryptoJS.AES.encrypt(params,pwd).toString();
    document.getElementById("form1").submit();
    //CryptoJS.AES.decrypt(en,pwd).toString(CryptoJS.enc.Utf8)
}

function rsaEncrypt() {
    var text = "dingpegnwei";
    var pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKzwTOaVQMPNlXUrlbZJT5LIm8ZvD0o1rUkVX8me+mnZc+CKC2DirgBY43+pwIJO2Rw63OcQxy5XC0t0rg3eRbdxvWi11ek8YLE8zFFWdL0+s+XqFHnYMdpgg3ECG50HBZl21l9N2rPn/FWdTNUy7fNL0VeBcPtTOwIRVLgIlmBwIDAQAB";
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(pubKey);
    var encrypted = encrypt.encrypt(password);

    var prikey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMrPBM5pVAw82VdSuVtklPksibxm8PSjWtSRVfyZ76adlz4IoLYOKuAFjjf6nAgk7ZHDrc5xDHLlcLS3SuDd5Ft3G9aLXV6TxgsTzMUVZ0vT6z5eoUedgx2mCDcQIbnQcFmXbWX03as+f8VZ1M1TLt80vRV4Fw+1M7AhFUuAiWYHAgMBAAECgYEAs3qNmFXiSYo4FW1iGB1lMTZXFmJLb1R5d5C+9fMNAPiJ9h4Qi8zx6JtCBwxDtXlovQzc7cth5vRhz6w+Gc6E3VFZ+cCfw9q5IjVzQXL63RZUfVHM73j/fuiQxsQ+D75fsEaz1PeA8Xi9a6L82ydXQitGqdQsArE8zEXXiKdLCOECQQDt2CKV4g1nFmX5htQWqgg3SrC5EwjwkNIsJSxny54zwMa1AcaPwDYAS5EXMEl5/tjNiyFuF9wG0zJIdqBdmnX/AkEA2ko7ES+N1C6hSVHETGWaVO3MhIVC4MDJ1plJnRO/qKixqCEd4Aa0RoiUV15MIDlL5Na9Fy+72s0V6+ITen1f+QJBAK/m2mVJQqpMGWz6914vsFXcKmkmF8V7BcVaVMYLBFy4JP13a7ei6w061fp1XtIX1ZXmYPoAMVzm01heS2oOs1cCQFbM5afzzOXulNve07u9Ox1CYezgQLO2dYwL8CWx0Kh0TBCa+pI2mEDj1njXWlYF8pPQf+hUZtKecnUVtMEkUaECQQCJsOx0IKhDEHUCKnU0uKMQDtoRwJgByFQIODt1EzdI4At0ndBDcWSQzGLs1GHdg2jukmNqWXbLJbHdNhYipajA";
    var decrypt = new JSEncrypt();
    decrypt.setPrivateKey(prikey);
    var uncrypted = decrypt.decrypt(encrypted);

    console.log("明文：" + text);
    console.log("已经加密：" + encrypted);
    console.log("已经解密：" + uncrypted);

}
