package org.maxwe.init;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-07-28 20:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 初始化超级管理员
 */
public class InitManager extends InitBase {
    public static final String id = "xxxx-xxxx-xxxx-xxxx-xxxx-xxxx-xxxx";

    public static void main(String[] args) throws Exception {
        Db.update("truncate table manager");
        boolean result = false;
        try {
            Record admin = new Record()
                    .set("managerId", id)
                    .set("username", "admin")
                    .set("password", "123456")
                    .set("level", 0)
                    .set("status", 1)
                    .set("pId", id);

            Record DingPengwei = new Record()
                    .set("managerId", UUID.randomUUID().toString())
                    .set("username", "DingPengwei")
                    .set("password", "123456")
                    .set("level", 1)
                    .set("status", 1)
                    .set("pId", id);


            Record PengweiDing = new Record()
                    .set("managerId", UUID.randomUUID().toString())
                    .set("username", "PengweiDing")
                    .set("password", "123456")
                    .set("level", 1)
                    .set("status", 1)
                    .set("pId", id);

            Record dingpw = new Record()
                    .set("managerId", UUID.randomUUID().toString())
                    .set("username", "dingpw")
                    .set("password", "123456")
                    .set("level", 1)
                    .set("status", 1)
                    .set("pId", id);

            Record dpw = new Record()
                    .set("managerId", UUID.randomUUID().toString())
                    .set("username", "dpw")
                    .set("password", "123456")
                    .set("level", 1)
                    .set("status", 1)
                    .set("pId", id);


            Record pwd = new Record()
                    .set("managerId", UUID.randomUUID().toString())
                    .set("username", "pwd")
                    .set("password", "123456")
                    .set("level", 1)
                    .set("status", 1)
                    .set("pId", id);

            result = Db.save("manager", admin) && Db.save("manager", DingPengwei) && Db.save("manager", PengweiDing) && Db.save("manager", dingpw) && Db.save("manager", dpw) && Db.save("manager", pwd);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (result) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }
}
