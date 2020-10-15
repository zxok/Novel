package com.hzx.web;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import com.hzx.cfg.XML_Dom4J;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpImg {

    //图片存入地址
    public static final String USER = "img\\userImg\\";
    //图片缓存地址
    public static final String STORE_BUFFER = XML_Dom4J.getFileRoot() + "\\img\\buffer\\";

    public static String downloadImg(String url,String savePath) {
        String simg=null, su=null;

        if(url!=null && !url.equals("")) {
            su = url.substring(url.lastIndexOf("."), url.length());//截取图片的后缀
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            try {
                CloseableHttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                UUID uuid = UUID.randomUUID();//伪随机生成的随机数
                simg = uuid.toString();//转换成字符串
                OutputStream om = new FileOutputStream(savePath+simg+""+su);

                entity.writeTo(om);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        simg = savePath + simg + su;
        System.out.println(simg);
        return simg;
    }

}
