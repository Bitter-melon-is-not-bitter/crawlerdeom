package com.tang.test.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 此demo还得待完善
 * 有点问题
 */
public class Demo implements PageProcessor {

    private Site site = Site.me()
            .setTimeOut(5000)
            .setRetryTimes(3)
            .setCharset("utf-8")
            .setRetrySleepTime(5000);

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().css("div.el a").links().all());
        page.putField("data", page.getHtml().xpath("//div[@class='bmsg job_msg inbox']/p").all());
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String[] urls = new String[5];
        for (int i = 1; i <= 5; i++) {
            urls[i - 1] = "https://search.51job.com/list/020000,000000,0000,00,9,99,java,2," + i + ".html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
        }
        Spider.create(new Demo())
                .addUrl(urls[1])
                .thread(3)
                .addPipeline(new FilePipeline(System.getProperty("user.dir") + "\\src\\main\\resources\\file"))
                .run();

    }
}
