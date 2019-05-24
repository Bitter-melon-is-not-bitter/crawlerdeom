package com.tang.test.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 连接池
 */
public class HttpClientPool {

    public static void main(String[] args) throws IOException {
        //创建连接处管理器
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

        //设置最大连接数
        pool.setMaxTotal(100);

        //设置每个主机的最大连接数来分配连接 每个主机地址只分配10个
        pool.setDefaultMaxPerRoute(10);

        //使用连接池
        doGet(pool);
    }

    private static void doGet(PoolingHttpClientConnectionManager pool) throws IOException {
        //利用连接处创建HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool).build();

        //创建get请求
        HttpGet get = new HttpGet("https://www.baidu.com");

        //得到响应 解析响应成功的数据
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s);
            System.out.println(s.length());
        }

        //关闭response和HttpClient
        response.close();
        //不能自己关闭HttpClient 因为HttpClient由连接池管理
    }
}
