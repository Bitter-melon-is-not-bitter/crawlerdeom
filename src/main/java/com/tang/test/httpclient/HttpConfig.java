package com.tang.test.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 配置请求信息
 */
public class HttpConfig {
    public static void main(String[] args) throws IOException {
        //创建Http请求对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建Get请求
        HttpGet get = new HttpGet("https://www.baidu.com");

        //配置请求信息
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(1000).//创建连接的最长时间 单位毫秒
                setConnectionRequestTimeout(500).//设置获取连接的最长时间 单位毫秒
                setSocketTimeout(10 * 1000).//设置数据传输的最长时间 单位毫秒
                build();

        //给请求设置请求信息
        get.setConfig(config);

        //得到响应
        CloseableHttpResponse response = httpClient.execute(get);

        //解析成功的响应
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s.length());
        }
    }

}
