package jsoup;

import com.sun.org.apache.bcel.internal.generic.DMUL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * 1.[Jsoup.parse()]    方法是得到dom对象 返回值：Document
 * 2.[dom.getElement(s)By...]   方法是得到一个element(s)对象 返回值：Element(s)
 * 3.[element(s).first().text() OR element.first().text()]    方法是得到标签里面文本内容 返回值：String
 */
public class FirstJsoup {


    private static final String LocalUrl = System.getProperty("user.dir") //获取项目当前所在硬盘位置
            + "\\src\\main\\resources\\static\\test.html";

    /**
     * 解析字符串和url
     *
     * @throws IOException
     */
    @Test
    public void parseUrlAndString() throws IOException {
        //解析Url地址获取dom对象
        //需要参数 url(解析地址) ,time(超时时间 单位毫秒)
        Document dom = Jsoup.parse(new URL("https://blog.csdn.net/qq_41384743/article/details/90293851"), 10000);

        //使用标签选择器，获取title标签中的内容
        String title = dom.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    /**
     * 解析文件
     *
     * @throws IOException
     */
    @Test
    public void parseFile() throws IOException {

        Document dom = Jsoup.parse(new File(LocalUrl), "utf-8");
        String title = dom.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    /**
     * dom解析操作
     *
     * @throws IOException
     */
    @Test

    public void parseDom() throws IOException {
        Document dom = Jsoup.parse(new File(LocalUrl), "utf-8");
        Elements title = dom.getElementsByTag("title");//根据标签解析HTML
        Element elementById = dom.getElementById("lol1");//根据Id解析HTML
        Elements elementsByClass = dom.getElementsByClass("lol1");//根据class解析HTML
        String abc1 = dom.getElementsByAttribute("abc").text();//根据属性名解析HTML
        String abc2 = dom.getElementsByAttributeValue("abc", "456").first().text(); //根据属性名加属性值解析HTML

        System.out.println("文章标题：" + title);
        System.out.println("id元素：" + elementById);
        System.out.println("class元素：" + elementsByClass);
        System.out.println("abc1：" + abc1);
        System.out.println("abc2：" + abc2);
    }

    /**
     * 解析标签里面的属性
     *
     * @throws IOException
     */
    @Test
    public void getAttributeVlue() throws IOException {
        Document dom = Jsoup.parse(new File(LocalUrl), "utf-8");
        Element elementById = dom.getElementById("test");
        String id = elementById.id();//拿到id的值
        Set<String> strings = elementById.classNames();//拿到完整的class的值
        String s = elementById.className();//根据空格拆分拿到class的值
        Attributes attributes = elementById.attributes();//拿到所有属性的值
        String aClass = elementById.attr("class");//拿到class的值


        for (String str : strings) {
            System.out.println("str：" + str);
        }
        System.out.println(s);
        System.out.println("attributes：" + attributes);
        System.out.println("id：" + id);
        System.out.println("aClass：" + aClass);
    }

    /**
     * 根据组合选择器获取元素
     *
     * @throws IOException
     */
    @Test
    public void getForSelector() throws IOException {
        //获取dom对象
        Document dom = Jsoup.parse(new File(LocalUrl), "utf-8");

        //标签名+Id </>#id
        Element first = dom.select("span#span1").first();
        System.out.println("标签名+标签Id：" + first.text());

        //标签名+类名 </>.class
        Elements select = dom.select("span.span1");
        System.out.println("标签名+属性名：" + select);

        //标签名+属性名 </>[attr]
        Elements select1 = dom.select("a[href]");
        System.out.println("标签名+属性名：" + select1);

        //任意组合 </>[attr].class|#id
        Elements select2 = dom.select("span[abc].span1");
        Elements select3 = dom.select("span[abc]#span1");
        System.out.println("标签名+属性名+class：" + select2);
        System.out.println("标签名+属性名+id：" + select3);

        //类名+标签名 查找某个元素下面的子元素
        Elements select4 = dom.select(".classa ol");
        System.out.println("类名+标签名：" + select4);

        //使用 >号： parent > child
        Elements select5 = dom.select("div > li > ol");
        System.out.println("parent > child：" + select5);

        //上个例子的衍生： class > child > child's child
        Elements select6 = dom.select(".classa > li > ul > a");
        System.out.println("class > child > child's child：" + select6);

        //查某个父元素下的所有子元素 parent > *
        Elements select7 = dom.select("div > *");
        System.out.println("parent > *：" + select7);
    }
}
