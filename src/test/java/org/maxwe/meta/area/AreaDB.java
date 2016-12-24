//package cn.foodslab.meta.area;
//
//import junit.framework.TestCase;
//
///**
// * Created by Pengwei Ding on 2016-07-27 11:17.
// * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
// * Description: @TODO
// */
//public class AreaDB extends TestCase {
//    public static void main(String[] args) throws Exception {
////        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
////        aClass.newInstance();
////        String url="jdbc:mysql://localhost:3306/test?user=maxwe&password=maxwe";
////        Connection connection = DriverManager.getConnection(url);
////        Statement statement = connection.createStatement();
//
////        File fileIn = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/address.sql");
////        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
////
////        String line;
////        int index = 0;
////        long start = System.currentTimeMillis();
////        while ((line = bufferedReader.readLine()) != null) {
////            String insert = "insert into meta_address(addressCode,addressLabel,addressName,addressDesc,addressLevel,addressOrder,addressStatus,addressPCode) values('" + line + "');";
////            statement.addBatch(insert);
////            index ++;
////            if (index % 1000 == 0){
////                statement.executeBatch();
////                System.out.println("current index = " + index + " ; consuming = " + ((System.currentTimeMillis() - start) / 1000));
////            }
////            if (index > 2){
////                break;
////            }
////        }
////        System.out.println("current index = " + index + " ; consuming = " + ((System.currentTimeMillis() - start) / 1000));
////        statement.executeBatch();
////        statement.close();
////        connection.close();
//
//        /**
//         * 上传发布
//         */
////        File fileIn = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/address.sql");
////        File fileOut = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/address1.sql");
////        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
////        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut));
////        bufferedWriter.write("CREATE TABLE `address` (`code` varchar(12) NOT NULL,`label` varchar(64) NOT NULL, `name` varchar(64) NOT NULL,`desc` varchar(100) DEFAULT NULL, `level` int(4) NOT NULL, `order` int(4) NOT NULL, `status` int(1) NOT NULL, `Pcode` varchar(12) NOT NULL, `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, PRIMARY KEY (`addressCode`)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
////        bufferedWriter.newLine();
////        bufferedWriter.flush();
////        String line;
////        int index = 0;
////        while ((line = bufferedReader.readLine()) != null) {
////            String insert = "insert into address(code,label,name,desc,level,order,status,Pcode) values('" + line + "');";
////
////            bufferedWriter.write(insert);
////            bufferedWriter.newLine();
////
////            index ++;
////
////            if (index % 1000 == 0){
////                System.out.println(index);
////            }
////        }
////        bufferedWriter.flush();
////        bufferedWriter.close();
////        bufferedReader.close();
//    }
//}
