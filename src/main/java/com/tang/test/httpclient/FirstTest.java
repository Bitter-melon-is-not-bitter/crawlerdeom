package com.tang.test.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 简单入门
 */
public class FirstTest {
    public static void main(String[] args) throws IOException {

        //打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建git请求方式
        HttpGet get = new HttpGet("https://www.baidu.com");

        //执行git请求
        CloseableHttpResponse response = httpClient.execute(get);

        //对状态码为200的进行打印
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            System.out.println(s);
        }
        response.close();
        httpClient.close();
    }
}
