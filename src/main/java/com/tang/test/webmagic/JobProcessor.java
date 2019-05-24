package com.tang.test.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * WebMagic入门案列
 */
public class JobProcessor implements PageProcessor {

    private Site site = Site
            .me()
            .setCharset("uft-8")//设置编码
            .setTimeOut(10000)//设置超时时间
            .setRetrySleepTime(2000)//设置重试时间
            .setRetryTimes(3);//设置重试次数

    /**
     * 此方法负责解析页面
     *
     * @param page
     */
    public void process(Page page) {
        //解析返回的数据page，并且把解析的结果放到ResultItems中
        //第一案列  CSS选择器
        //page.putField("data1", page.getHtml().css("ul.fast_guidul a").all());

        //第二案列  XPth
        //page.putField("data2", page.getHtml().xpath("//div[@class=txt]/h3/a").all());

        //第三案列
        //get() toString() 俩个方法是一样的 都是返回一条数据  all()是返回多条数据
        //page.putField("data3", page.getHtml().css("div.yqlj2 a").regex(".*网.*").get());
        //page.putField("data4", page.getHtml().css("div.yqlj2 a").regex(".*网.*").toString());
        //page.putField("data5", page.getHtml().css("div.yqlj2 a").regex(".*网.*").all());

        //第四案列  二次链接请求解析
        page.addTargetRequests(page.getHtml().css("div.dd_bt").links().regex(".*6$").all());
        page.putField("data6", page.getHtml().css("h1").all());
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //第一案列
        //Spider.create(new JobProcessor())
        //        .addUrl("http://www.chinanews.com/")//设置爬取数据的页面
        //        .run();//执行爬虫

        //第二案列
        //Spider.create(new JobProcessor())
        //        .addUrl("http://www.chinanews.com/live.shtml")
        //        .run();

        //第三案列
        //Spider.create(new JobProcessor())
        //        .addUrl("http://auto.chinanews.com/")
        //        .run();

        //第四案列
        Spider.create(new JobProcessor())
                .addPipeline(new FilePipeline(System.getProperty("user.dir") + "\\src\\main\\resources\\file"))//将文件保存在本地
                .thread(5)//设置多个线程处理
                .addUrl("http://www.chinanews.com/mil/news.shtml")//解析地址
                .run();//执行爬虫
    }
}
