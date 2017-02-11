package org.maxwe.tao.server.controller.page;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.maxwe.tao.server.interceptor.ManagerInterceptor;
import org.maxwe.tao.server.interceptor.TokenInterceptor;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-19 16:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 前端页面接口，所有有关页面的跳转均由该类实现的接口分发
 */
public class PageController extends Controller implements IPageController {

    @Override
    public void index() {
        if ("initdata".equals(this.getAttr("key"))) {
            int times = Integer.parseInt(this.getAttr("times").toString());
            LinkedList<Record> records = new LinkedList<>();
            for (int i = 0; i < times; i++) {
                Record agentRecord = new Record()
                        .set("agentId", UUID.randomUUID().toString())
                        .set("cellphone", "185" + String.format("%08d", i))
                        .set("password1", "111111")
                        .set("code", "185" + String.format("%08d", i));
                records.add(agentRecord);
            }
            Db.batchSave("mate", records, 100);
        }
        this.renderJson("OK");
    }

    public static void main() {

    }

    @Override
    public void ps() {
        this.setAttr("title", "食坊-系列");

//        LinkedList<Map<String, String>> metas = new LinkedList<>();
//        String seriesId = this.getAttr("seriesId");
//        if (seriesId != null) {
//            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
//            formatMap.put("metaId", "seriesId");
//            formatMap.put("metaValue", seriesId);
//            metas.add(formatMap);
//            this.setAttr("metas", metas);
//        }
//        LinkedList<String> styleSheets = new LinkedList<>();
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
//        this.setAttr("styleSheets", styleSheets);
//        LinkedList<String> javaScripts = new LinkedList<>();
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/series.js\"></script>");
//        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pd() {
        this.setAttr("title", "食坊-详情");
//        LinkedList<Map<String, String>> metas = new LinkedList<>();
//        String typeId = this.getAttr("typeId");
//        if (typeId != null) {
//            LinkedHashMap<String, String> typeMap = new LinkedHashMap<>();
//            typeMap.put("metaId", "typeId");
//            typeMap.put("metaValue", typeId);
//            metas.add(typeMap);
//        }
//        String formatId = this.getAttr("formatId");
//        if (formatId != null) {
//            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
//            formatMap.put("metaId", "formatId");
//            formatMap.put("metaValue", formatId);
//            metas.add(formatMap);
//        }
//        this.setAttr("metas", metas);
//
//        LinkedList<String> styleSheets = new LinkedList<>();
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/detail.css\">");
//        this.setAttr("styleSheets", styleSheets);
//
//        LinkedList<String> javaScripts = new LinkedList<>();
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/detail.js\"></script>");
//        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pb() {
//        String params = this.getAttr("p");
//        Map<String, Object> objectMap = JSON.parseObject(params, Map.class);
//
//        LinkedList<Map<String, String>> metas = new LinkedList<>();
//        LinkedHashMap<String, String> mappingIdsMap = new LinkedHashMap<>();
//        mappingIdsMap.put("metaId", "productIds");
//        mappingIdsMap.put("metaValue", objectMap.get("productIds").toString());
//        metas.add(mappingIdsMap);
//
//        this.setAttr("metas", metas);
//
//        this.setAttr("title", "食坊-支付");
//        LinkedList<String> styleSheets = new LinkedList<>();
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.css\">");
//        this.setAttr("styleSheets", styleSheets);
//
//        LinkedList<String> javaScripts = new LinkedList<>();
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing_anonymous.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing_named.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing_payment.js\"></script>");
//        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }


    @Override
    public void pq() {
//        this.setAttr("title", "食坊-订单查询");
//        String params = this.getAttr("p");
//        Map<String,Object> requestEntity = JSON.parseObject(params, Map.class);
//        LinkedList<Map<String, String>> metas = new LinkedList<>();
//        if (requestEntity != null && requestEntity.get("orderId") != null){
//            String orderId = requestEntity.get("orderId").toString();
//            if (orderId != null) {
//                LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
//                formatMap.put("metaId", "orderId");
//                formatMap.put("metaValue", orderId);
//                metas.add(formatMap);
//            }
//        }
//        this.setAttr("metas", metas);
//
//        LinkedList<String> styleSheets = new LinkedList<>();
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/query.css\">");
//        this.setAttr("styleSheets", styleSheets);
//
//        LinkedList<String> javaScripts = new LinkedList<>();
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/query.js\"></script>");
//        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Before(TokenInterceptor.class)
    @Override
    public void pm() {
//        String params = this.getAttr("p");
//        VPageEntity vPageEntity = JSON.parseObject(params, VPageEntity.class);
//        if (vPageEntity.getDir() == null) {
//            vPageEntity.setDir("history");
//        }
//
//        LinkedList<Map<String, String>> metas = new LinkedList<>();
//
//        LinkedHashMap<String, String> dirMap = new LinkedHashMap<>();
//        dirMap.put("metaId", "dir");
//        dirMap.put("metaValue", vPageEntity.getDir());
//        metas.add(dirMap);
//
//        this.setAttr("metas", metas);
//        this.setAttr("title", "食坊-我的");
//
//        LinkedList<String> styleSheets = new LinkedList<>();
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_cart.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_order.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_account.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_receiver.css\">");
//        styleSheets.add("<level rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine.css\">");
//        this.setAttr("styleSheets", styleSheets);
//
//        LinkedList<String> javaScripts = new LinkedList<>();
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_cart.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_order.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_account.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_receiver.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine.js\"></script>");
//        this.setAttr("javaScripts", javaScripts);

        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pp() {
//        this.setAttr("title", "食坊-协议");
//        LinkedList<String> javaScripts = new LinkedList<>();
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
//        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
//        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void ml() {
        this.render("/webapp/widgets/login.html");
    }

    @Override
    @Before({TokenInterceptor.class, ManagerInterceptor.class})
    public void frame() {
//        String params = getAttr("p");
//        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
//        HttpSession session = SessionContext.getSession(paramsMap.get("cs").toString());
//        // TODO 把JS文件进行拆分 根据管理员的权限进行动态分配JS文件
        this.render("/webapp/widgets/frame.html");
    }

    public void api() {
        this.render("/webapp/widgets/goods.html");
    }
}
