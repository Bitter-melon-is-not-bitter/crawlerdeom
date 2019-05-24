package com.tang.test.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * post请求不带参
 */
public class CrawlerPost {
    public static void main(String[] args) throws IOException {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建Post请求方式
        HttpPost post = new HttpPost("https://www.baidu.com");

        //对指定uri发出post请求
        CloseableHttpResponse response = httpClient.execute(post);

        //解析响应成功得到的请求数据
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity,"utf-8");
            System.out.println(s.length());
            System.out.println(s);
        }
        //关闭响应和HttpClient对象 释放资源
        response.close();
        httpClient.close();
    }
}
