package com.tang.test.httpclient;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * get不带参
 */
public class CrawlerGet {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet("https://www.baidu.com");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity, "utf-8");
                System.out.println(s.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
