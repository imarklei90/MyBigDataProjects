package edu.sse.ustc.elasticsearch.weblog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/** 准备测试数据
 * @author imarklei90
 * @since 2019.01.22
 */
public class GenerateTestData {
    public static void main(String[] args) {
        // 1. 创建一个文件
        File file = new File(System.getProperty("user.dir") + File.separator + "Storm/data/" + "log.txt");

        // 2. 准备数据
        String[] hosts = {"www.ustc.edu.cn"};

        String[] sessionIds = {"ACAHJJLK123456FASFF", "ADSDJIOA45675432SHFNAJ", "ASHDLHFJKA1234567",
                    "FAJKLFJAKSLF1324567", "ASHFJLASFJQWHO1234"};

        String[] time = {"2019-01-22 21:35:00", "2019-03-22 21:35:00","2018-01-22 21:35:00",
                "2017-05-10 21:35:00","2016-08-22 09:35:00","2014-12-22 23:35:00","2013-02-21 09:10:00","2017-05-21 7:43:00",};

        // 3. 拼接数据
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < 30; i++){
            buffer.append(hosts[0] + "\t" + sessionIds[random.nextInt(5)] + "\t" + time[random.nextInt(8)] + "\n");
        }

        FileOutputStream outputStream = null;
        // 4. 保存到文件
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(buffer.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 5. 关闭资源
            try {
                outputStream.close();
            } catch (IOException e) {

            }
        }



    }
}
