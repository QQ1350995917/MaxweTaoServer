//package cn.foodslab.meta.area;
//
//import junit.framework.TestCase;
//
//import java.io.*;
//
///**
// * Created by Pengwei Ding on 2016-07-27 09:04.
// * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
// * Description: @TODO
// */
//public class PreHandler extends TestCase {
//
//    public static void main(String[] args) throws Exception {
//        File fileIn = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/area_source.sql");
//        File fileOut = new File("/Users/dingpengwei/workspace/dingpw/www.maxwe.org/src/test/resources/area/area.sql");
//
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut));
//
//        String line;
//        int insertNum = 0;
//        int index = 0;
//        long start = System.currentTimeMillis();
//        while ((line = bufferedReader.readLine()) != null) {
//            if (line.startsWith("INSERT")) {
//                //排除干扰数据
//                insertNum ++;
//                System.out.println("排除干扰项" + line);
//                continue;
//            }
//
//            line = line.replace("(", "").replace(")", "").replace("'", "").replace(" ", "");
//            String[] split = line.split(",");
//
//            if (split[4].contains(split[2])){
//                split[4] = split[4].replace(split[2], "");
//            }
//
//
//            if (split[6].contains(split[4])){
//                split[6] = split[6].replace(split[4], "");
//            }
//            if (split[6].contains(split[2])){
//                split[6] = split[6].replace(split[2], "");
//            }
//
//
//            if (split[8].contains(split[6])){
//                split[8] = split[8].replace(split[6], "");
//            }
//            if (split[8].contains(split[4])){
//                split[8] = split[8].replace(split[4], "");
//            }
//            if (split[8].contains(split[2])){
//                split[8] = split[8].replace(split[2], "");
//            }
//
//
//            if (split[10].contains(split[8])){
//                split[10] = split[10].replace(split[8], "");
//            }
//            if (split[10].contains(split[6])){
//                split[10] = split[10].replace(split[6], "");
//            }
//            if (split[10].contains(split[4])){
//                split[10] = split[10].replace(split[4], "");
//            }
//            if (split[10].contains(split[2])){
//                split[10] = split[10].replace(split[2],"");
//            }
//
//            StringBuffer stringBuffer = new StringBuffer();
//            for (int i=0;i<split.length;i++){
//                stringBuffer.append(",").append(split[i]);
//            }
//
//            line = stringBuffer.toString().replaceFirst(",","");
//
////            String[] splitNew = line.split(",");
////            for (int j = 0;j<splitNew.length;j++){
////                if (split[8].length() > 3){
////                    if (splitNew[10].startsWith(splitNew[8].substring(0, 4))){
////                        bufferedWriter.write(line);
////                        bufferedWriter.newLine();
////                    }
////                }
////            }
//
//
//            bufferedWriter.write(line);
//            bufferedWriter.newLine();
//            index ++;
//            if (index % 1000 == 0){
//                System.out.println(index + " ,耗时：" + ((System.currentTimeMillis() - start) / 1000));
//            }
//        }
//
//        bufferedWriter.flush();
//        bufferedWriter.close();
//        bufferedReader.close();
//
//
//        System.out.println("共完成项：" + index + " ； 共排除干扰项" + insertNum);
//
//    }
//}
//
//
//
//
