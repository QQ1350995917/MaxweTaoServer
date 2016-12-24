//package cn.foodslab.meta.area;
//
//import junit.framework.TestCase;
//
//import java.io.*;
//
///**
// * Created by Pengwei Ding on 2016-07-26 19:43.
// * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
// * Description: 初始化全国行政地域数据到数据库
// */
//public class AreaInit extends TestCase {
//
//    public static void main(String[] args) throws Exception {
//        File fileIn = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/area_source.sql");
//
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
//
//        AreaEntity level1 = null;
//        AreaEntity level2 = null;
//        AreaEntity level3 = null;
//        AreaEntity level4 = null;
//
//        String line;
//        int index = 0;
//        int insertNum = 0;
//
//        File fileOut = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/county.address.sql");
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut));;
//        bufferedWriter.write("CREATE TABLE `address` (`code` varchar(12) NOT NULL,`label` varchar(64) NOT NULL, `name` varchar(64) NOT NULL,`desc` varchar(100) DEFAULT NULL, `level` int(4) NOT NULL, `order` int(4) NOT NULL, `status` int(1) NOT NULL, `Pcode` varchar(12) NOT NULL, `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, PRIMARY KEY (`code`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//
//        while ((line = bufferedReader.readLine()) != null) {
//            if (line.startsWith("INSERT")) {
//                //排除干扰数据
//                insertNum ++;
//                continue;
//            }
//            line = line.replace("(", "").replace(")", "").replace("'", "").replace(" ", "");
//            String[] split = line.split(",");
//
//            AreaEntity address1 = new AreaEntity(split[1] + "0000000000",split[2], 1,index, 1,split[1] + "0000000000");
//            if (level1 == null || !level1.equals(address1)) {
////                System.out.println(address1);
//                level1 = address1;
//                bufferedWriter.write(address1.toString());
//                bufferedWriter.newLine();
//            }
//
//            AreaEntity address2 = new AreaEntity(split[3], split[4], split[4], 2, index, 1, split[1] + "0000000000");
//            if (level2 == null || !level2.equals(address2)) {
////                System.out.println(address2);
//                level2 = address2;
//                bufferedWriter.write(address2.toString());
//                bufferedWriter.newLine();
//            }
//
//            AreaEntity address3 = new AreaEntity(split[5], split[6], split[6], 3, index, 1,  split[3]);
//            if (level3 == null || !level3.equals(address3)) {
////                System.out.println(address3);
//                level3 = address3;
//                bufferedWriter.write(address3.toString());
//                bufferedWriter.newLine();
//            }
//
////            AreaEntity address4 = new AreaEntity(split[7], split[8], split[8], 4, index, 1,  split[5]);
////            if (level4 == null || !level4.equals(address4)) {
//////                System.out.println(address4);
////                level4 = address4;
////                bufferedWriter.write(address4.toString());
////                bufferedWriter.newLine();
////            }
//
////            AreaEntity address5 = new AreaEntity(split[9], split[10], split[10], 5, index, 1,  split[7]);
////            bufferedWriter.write(address5.toString());
////            bufferedWriter.newLine();
//
//            index ++;
//            if (index % 1000 == 0){
//                System.out.println(index);
//            }
//        }
//        System.out.println(index + " " + insertNum + " " + (index + insertNum));
//        bufferedWriter.flush();
//        bufferedWriter.close();
//        bufferedReader.close();
//    }
//
//}
