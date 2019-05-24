package com.tang.test.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 带表单的post请求
 */
public class CrawlerParamPost {
    public static void main(String[] args) throws IOException {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Post请方式
        HttpPost post = new HttpPost("https://mbd.baidu.com/newspage/data/landingsuper");

        //声明一个List集合，封装表单中的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("context", "%7B%22nid%22%3A%22news_8216872134600844369%22%7D"));
        params.add(new BasicNameValuePair("n_type", "0"));
        params.add(new BasicNameValuePair("p_from", "1"));

        //创建表单的Entity对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");

        //把准备好的表单Entity对象设置到post里面
        post.setEntity(formEntity);

        //发出post请求 得到response请求
        CloseableHttpResponse response = httpClient.execute(post);

        //解析请求成功的数据
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            System.out.println(s.length());
        } else {
            System.out.println("请求失败");
        }
        response.close();
        httpClient.close();
    }
}
