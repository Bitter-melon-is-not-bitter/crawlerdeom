package com.tang.test.httpclient;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * get带参
 */
public class CrawlerParamGet {
    public static void main(String[] args) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置请求地址 用URIBuilder创建URI
        URIBuilder uriBuilder = new URIBuilder("https://mbd.baidu.com/newspage/data/landingsuper");

        //设置参数
        uriBuilder.setParameter("context", "%7B%22nid%22%3A%22news_8216872134600844369%22%7D");
        uriBuilder.setParameter("n_type", "0");
        uriBuilder.setParameter("p_from", "1");
        //uriBuilder.setParameters()

        HttpGet get = new HttpGet(uriBuilder.build());

        System.out.println(uriBuilder.build());

        CloseableHttpResponse response = null;
        response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            System.out.println(s.length());
        }
        response.close();
        httpClient.close();
    }
}
