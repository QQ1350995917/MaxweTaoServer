package org.maxwe.tao.server.controller.account.manager;

import com.jfinal.core.Controller;
import org.maxwe.tao.server.common.utils.RSAUtils;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-09 21:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ManagerController extends Controller implements IManagerController {

    @Override
    public void login() {
        try {
            Map<String, String> keys = RSAUtils.initKey();
            this.setSessionAttr(RSAUtils.PRIVATE_KEY, RSAUtils.getPrivateKey(keys));
            this.setSessionAttr(RSAUtils.PUBLIC_KEY, RSAUtils.getPublicKey(keys));
            this.setAttr(RSAUtils.PUBLIC_KEY, RSAUtils.getPublicKey(keys));
        } catch (Exception e) {
            e.printStackTrace();
        }
        render("/WEB-INF/login.html");
    }

    @Override
    public void access() {
        String privateKey = this.getSessionAttr("privateKey").toString();
        String password = this.getPara("password");
        try {
            String privateKey1 = RSAUtils.decryptByPrivateKey(password, privateKey);
            System.out.println("解密后的数据：" + privateKey1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        render("/WEB-INF/terminal.html");
    }
}
