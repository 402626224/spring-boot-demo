package com.imain.javademo.downloadfile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * author Songrui.Liu
 * date 2019/12/26 15:16
 */
public class URLFile {

    public static void main(String[] args) throws FileNotFoundException {
        URL url = null;

        try {
            url = new URL("http://pfd.premierfarnell.com/eCat_element14_ICEasy_7713568_CN_Data.zip");   //想要读取的url地址
            File fp = new File("E://temp//Data.zip");
            OutputStream os = new FileOutputStream(fp);          //建立文件输出流
            System.out.println("kaishi");

            URLConnection conn = url.openConnection();          //打开url连接
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            byte[] current = new byte[4096];
            while (bis.read(current, 0, current.length) != -1) {
                os.write(current, 0, current.length);
                os.flush();
                System.out.println("--");
            }
            os.close();
            System.out.println("结束");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
