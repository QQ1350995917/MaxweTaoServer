package org.maxwe.init;

import org.maxwe.tao.server.service.menu.MenuEntity;
import com.jfinal.plugin.activerecord.Db;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 18:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 初始化菜单
 */
public class InitMenu extends InitBase{

    public static void main(String[] args) throws Exception {
        boolean result = false;
        Db.update("truncate table menu");
        LinkedList<MenuEntity> menuEntities = genMenus();
        for (MenuEntity menuEntity:menuEntities){

        }
        if (result) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }

    private static LinkedList<MenuEntity> genMenus() {
        //                                      id       显示名称  等级 顺序  方法名       位置标记                             父ID   状态
//        MenuEntity menuEntity01 = new MenuEntity(ids[0], "系统状态", 0, 0, "sys_status", "51bf4162-5270-11e6-8311-1cae145b8cab", ids[0], 2);
//        MenuEntity menuEntity02 = new MenuEntity(ids[1], "系统日志", 0, 1, "sys_log", "51bf4162-5270-11e6-8311-1cae145b8cab", ids[1], 2);
//        MenuEntity menuEntity03 = new MenuEntity(ids[2], "流量报表", 0, 2, "sys_flow", "51bf4162-5270-11e6-8311-1cae145b8cab", ids[2], 2);
//        MenuEntity menuEntity04 = new MenuEntity(ids[3], "销售报表", 0, 3, "sale_chart", "51bf4162-5270-11e6-8311-1cae145b8cab", ids[3], 1);
//        MenuEntity menuEntity05 = new MenuEntity(ids[4], "管理员", 0, 0, "manager", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[4], 2);
//        MenuEntity menuEntity06 = new MenuEntity(ids[5], "皮肤设置", 0, 1, "skin", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[5], 2);
//        MenuEntity menuEntity07 = new MenuEntity(ids[6], "链接管理", 0, 2, "link", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[6], 2);
//        MenuEntity menuEntity08 = new MenuEntity(ids[7], "产品管理", 0, 3, "product", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[7], 1);
//        MenuEntity menuEntity09 = new MenuEntity(ids[8], "海报管理", 0, 4, "poster", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[8], 1);
//        MenuEntity menuEntity10 = new MenuEntity(ids[9], "推荐管理", 0, 5, "recommend", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[9], 1);
//        MenuEntity menuEntity11 = new MenuEntity(ids[10], "消息管理", 0, 6, "message", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[10], 1);
//        MenuEntity menuEntity12 = new MenuEntity(ids[11], "用户管理", 0, 7, "user", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[11], 1);
//        MenuEntity menuEntity13 = new MenuEntity(ids[12], "订单管理", 0, 8, "order", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[12], 1);
//        MenuEntity menuEntity14 = new MenuEntity(ids[13], "预约设置", 0, 9, "reser_setting", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[13], 1);
//        MenuEntity menuEntity15 = new MenuEntity(ids[14], "预约管理", 0, 10, "reser_setting", "8e2e3fc7-1968-4f1b-bd4c-07794c5855b5", ids[14], 1);
//
//        LinkedList<MenuEntity> menuEntities = new LinkedList<>();
//
//        menuEntities.add(menuEntity01);
//        menuEntities.add(menuEntity02);
//        menuEntities.add(menuEntity03);
//        menuEntities.add(menuEntity04);
//        menuEntities.add(menuEntity05);
//        menuEntities.add(menuEntity06);
//        menuEntities.add(menuEntity07);
//        menuEntities.add(menuEntity08);
//        menuEntities.add(menuEntity09);
//        menuEntities.add(menuEntity10);
//        menuEntities.add(menuEntity11);
//        menuEntities.add(menuEntity12);
//        menuEntities.add(menuEntity13);
//        menuEntities.add(menuEntity14);
//        menuEntities.add(menuEntity15);
//
//        return menuEntities;

        return null;
    }

    /**
     * 120个静态ID
     */
    private static final String[] ids = {"468c67fc-b88e-47d9-b8b5-02a586d0919a",
            "d8ca12c2-1d15-4449-8789-a57449f014ae", "309f1060-0d0c-4807-805c-358e58511035", "999b42f6-3129-4f09-be16-27a26455c4c5",
            "c341aa66-3775-493f-93cf-358346c3477b", "75a55868-669e-4f72-a368-20302bc79be5", "0f12bdb8-85e2-4fa5-a5aa-25ea1c88b40b",
            "17d1a324-ca6f-4f1c-8e58-fdd4fdb1b9d7", "321aa99c-3f34-433c-9a84-12e929d4a351", "97b34260-ce43-4882-bceb-b8e0ee5b2851",
            "bff0e87f-d59c-4549-a47b-4265ddf0ce37", "ba87ba9c-68cf-439a-9732-f27394702bff", "c7c1677e-d320-4cdf-9820-f97a4cf187ce",
            "1a6f6aca-075a-4e5e-b531-554aa171b8c8", "ba96b69c-c88b-4575-bd8b-cacc82e65027", "9cfe0ce4-2a43-4fa6-a414-c4e97795194d",
            "4222ba8b-1b62-4913-ae7e-695768099257", "80c49966-2569-4bef-bf58-ee21a20b25b8", "01078499-867d-4c0e-a295-65fb830ef28e",
            "11ae6469-096a-4eae-be63-bc8e94c414db", "b0c8e8d4-f56f-4939-b53e-f9f74443c043", "13eab18f-7b8b-48f7-b5db-7f0f01379454",
            "f6740239-c157-422f-b4a9-961b3d582e04", "b36bdc67-8f71-400a-9cf1-2f80138ace6a", "ae0de88d-ba7e-40d1-ba0f-64fb4bf7edbc",
            "edb609bd-b154-4754-a770-0357429ee11f", "befabce5-21a5-45c8-8c57-f813ee56ecf0", "99adf8fd-75a6-43d0-812a-fad3271e5fee",
            "5060e15e-13d7-4c88-aed7-afc0b326a832", "4991365f-a6d9-4b76-9872-8c5470f9caf0", "d20ef5ff-d99c-42cf-a73a-d9c357e004c0",
            "b8da0888-4394-406d-979d-454661253aed", "4de754ea-ddf6-42ac-86e5-ebec59144895", "1ac726dd-245e-46f1-b62e-08aa095d6918",
            "5c2ab04e-9d12-4477-872b-61caed77af31", "673b3530-74ef-40c4-9ca3-02226043321c", "780724ad-1ae4-41ea-931a-4f3f93ed6f4a",
            "3975a82d-7718-4dd1-9a2b-78c88f4bc9c8", "551a88c0-ce49-41b4-8025-e229d697a2bb", "3170e209-75ed-47be-b4b1-a0b705380232",
            "ec09130c-0f9f-4b63-9674-399dfcbe0843", "a6db0462-d864-4ff6-aa0b-82aec1e233fb", "fcdca242-8935-451e-9529-e88924c38f41",
            "859e62b3-5406-4542-b8b9-52e6cb6942e6", "feff7878-0163-4441-a7c9-0798a706a692", "831ce946-8d28-416c-9382-74241bb585dd",
            "55ee4cef-f4a6-4e6b-9d57-0607c6ad362b", "61c536d0-d9d0-4d76-a4e8-c908b22e8e58", "ca12d0e6-93a3-4ade-9722-ed16776a7bd5",
            "6ee85a98-69e0-40c2-879e-cd3c08f6fe6f", "b1ad9198-4357-4a32-8393-eb92ac3d605f", "0b628f70-aeef-414d-80bc-63701cd62807",
            "28a999c0-d970-4aa1-a92e-eb21e073c83b", "4eaa903b-0336-4b47-aa4f-93e6fb0f2882", "5ca0a4f1-558f-4ad6-a175-3dde570da1c2",
            "98742a25-848f-4840-b7e6-ee68f85bef5f", "4e13a464-f62a-409c-822b-aaceb8f77c87", "bf2ad5e6-f271-44b5-b2e4-e10e68b3a45f",
            "d1ff20ab-25ae-4bec-afe4-0726e57fafc2", "a57cfed5-ec42-4539-bfb9-c024184831b9", "2baf4b89-63b4-4285-b066-81fea5569ab0",
            "20a78a81-34c5-4601-84fc-011bd26437c6", "71273c80-0c19-49ad-9ef9-795925009ead", "bda506fd-efa9-4f5f-ac5b-7769347b685d",
            "529d18e2-2880-414d-ae8e-1fc0dbc4b667", "b7892efb-24e2-42d7-a6d5-15216e6fa018", "6fdf12c6-6347-42c9-bda3-183b75410c91",
            "c1dc7ead-31dd-415b-9672-fb1d19051e3c", "8985470b-cd04-498e-9900-61ab3ac43c16", "d27507f2-a85c-452f-b5a0-258e38aa0a99",
            "172bd7c3-21b2-4bad-a62c-1f0eecf2c4f0", "b0f92190-1530-46dd-883a-c7c66ac022ad", "89063397-7f28-40ca-8956-d4f3c9071fe5",
            "b77f459a-34f1-488e-93c4-0de966786a79", "444a80bc-bc1b-4bdc-9ee5-170a7302e6c3", "3bd90db3-3591-4892-8508-124ab742dde4",
            "f38224b3-aaea-4479-a98b-39c5012e7e20", "7642f53d-604b-4aa5-bd43-ff864b7acdd7", "0a6fa58b-c1a8-4202-81bb-b8f35294ec71",
            "c71e65ef-d840-4ee4-8b0c-195a14f27246", "30ee70b6-a2d9-4d61-b5b1-143b44f74e69", "7f27d341-17ce-4b2e-a439-4197ae8a5ca1",
            "bf8ace4d-adfd-4b37-b126-45c1d583356e", "e34d8f10-83f6-459c-9c72-5aefe628916b", "6deefef6-4df1-4724-ada7-330f1e529ad4",
            "70f0d058-0b13-4fdb-84b3-c24364fb8ae7", "27706112-4d64-4b30-b3b3-d844478f5678", "d461f98b-922e-4959-b849-69937e1dd0f3",
            "dd71f4bc-adda-4c44-9b67-5e3cd398ae44", "ea114c46-2a54-49e5-8c1a-7e3236b61a02", "b49b8c0d-3317-49f5-b4ed-4a2bf052b2cd",
            "0c614644-7a01-4bf2-ba9f-218247e27502", "e2ee343e-ed89-448b-b0fa-aed836ad87f3", "f63d76f5-fc9e-4dd9-829f-b5679ccbf8cb",
            "f5c5dddf-2aa0-47c0-bfb6-4855c6dce461", "67f28795-94c8-4b84-9933-9765b3f5a72b", "09bf33a8-44de-488a-804b-58322af88419",
            "8f28cc42-9f8c-446a-a182-a4312401c4cd", "724cc0a0-7589-45e3-b476-4c95a6a93307", "da358e67-b323-4126-bf8b-9cfc901653dc",
            "528170bd-e602-42e6-9df3-15f1ba12933b", "c3022e98-0c0b-430a-b42d-2590ced16865", "6bd9ce2f-7b55-44db-86f2-346088fea6e1",
            "5b29becc-fb6b-4f02-9030-3332193400fc", "c7b045a7-d73f-4f7c-bd93-20172a5aff9c", "c541ff03-ea3c-4816-856b-8bceb94a3fef",
            "cef5511e-33b1-4569-b561-2bfcf565935c", "1c9f00ff-5474-47e7-813c-7b9dcd9fc241", "e52e310e-981e-454e-b043-0b0d845d8ab1",
            "2a142344-892d-4b38-97a5-a1aec5a71bf9", "1e1088d8-0a40-4937-8219-da2b6ff7be53", "c8dbceee-2e55-47da-ba3f-49d2e07da74e",
            "77316fc1-a913-4ff5-affc-d5567e09428f", "541158f1-fe25-4742-9df7-78028a011eb3", "0fcf7689-6457-4667-b6f0-cfc3dc5653d8",
            "79cffdbe-3b52-4e12-8338-b8cc0d907d26", "f4d9ccd5-f444-4685-a083-b79e3c38e805", "3c2e3b23-3845-4337-a1e6-a7c5d69338f3",
            "99f3e888-4a1e-4e5a-a74f-2fe733b3598b", "f3f9d7cb-844c-4644-beaf-08e06b4fc6d5"};
}
