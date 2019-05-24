
HttpClient案列在main文件下；Jsoup案列在main-->test文件夹下

Jsoup介绍：
        1.Jsoup是一款Java的HTML解析器，可直接解析某个URL地址、HTML文本内容。
        2.它提供了一套非常省力的API，可以通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据

Jsoup功能：
        1.从一个URL，文件或字符串中解析HTML；
        2.使用DOM或CSS选择器来查找、取出数据；
        3.可操作HTML元素、属性、文本。

Jsoup缺点：
        虽然使用Jsoup可以代替HttpClient直接发起请求解析数据，但是往往不会这样用，因为实际开发过程
    中，需要使用到多线程，连接池，代理等等方式，而Jsoup对这些的支持并不是很好，所有我们一般把Jsoup
    仅仅作为Html解析工具使用，

总结：HttpClient是专业抓取数据，而Jsoup是专业解析HTML

WebMagic介绍：
        WebMagic项目代码分为核心和扩张俩部分。
        1.核心部分（webmagic-core）:是一个精简的、模块化的爬虫实现，而扩展部分则包括一些便利、实用性
    的功能。
        2.WebMagic的设计目标是尽量的模块化，并体现爬虫的功能特点。这部分体现非常简单、灵活的API，在基
    本不改变编辑模式的情况下，编写爬虫。
        3.扩张部分（webmagic-extension）:提供一些便捷的功能，例如注解模式编写爬虫等。同时内置了一些
    常用的组件，便于爬虫开发。

WebMagic机构：
        1.WebMagic的机构分为Downloader（下载器）、PageProcessor（解析器）、Scheduler（定时器）、
    Pipeline（持久化）四大组件，并由Spider将它们彼此组织起来。这四大组件对应爬虫生命周期中的下载、
    处理、管理和持久化等功能。WebMagic的设计参考了Scapy，但是实现方式更Java化一些。
        2.而Spider则将这几个组件组织起来，让它们可以相互交互，流程化的执行，可以认为Spider是一个大容器，
    它也是WebMagic逻辑的核心。

WebMagic运行路线：

        在Spider容器中：
                http                page                    ResultItems
    Internet---------->Downloader---------->PageProcessor---------->Pipeline
                              ↑                         |
                              |Request                  |Request
                              |                         |
                              |                         ↓
                              --------------------- Scheduler

WebMagic抽取元素（Selectable）：
        WebMagic里主要使用了三种抽取技术：XPath、正则表达式和CSS选择器。另外，对JSON格式的内容，可以使用
     JsonPath进行解析。
        1.XPath：例如想获取class=mt 的div标签，里面的h1标签的内容--> page.getHtml.xpath("//div[@class=mt]/h1/text()")
        2.CSS选择器：CSS选择器是与XPath类似的语言，它比XPath写起来要简单一些，但是如果写复杂一点的抽取规则，就相对要麻烦一些
        3.正则表达式：正则表达式则是一种通用的文本抽取语言。在这里一般用于获取url地址
        idea：ctrl+alt+b-->查看实现类
              ctrl+h-->查看实现类关系

WebMagic的配置、启动和终止：
        Spider是爬虫启动的入口。在启动爬虫之前，我们需要使用一个PageProcessor创建一个Spider对象，然后使用run()进行启动。
        同时Spider的其他组件（Downloader、Scheduler、Pipeline）都可以通过set方法来进行设置

        Spider的API如下图
        方法：                                      说明：                                        示例：
        create(PageProcessor)                      创建Spider                                     Spider.create(new PageProcessor'son)
        addUrl(String...)                          添加初始的URL                                   Spider.addUrl("Http:...")
        thread(int...)                             开启n个线程                                     Spider.thread(5)
        run()                                      启动，会阻塞当前线程执行                         Spider.run()
        start()/runAsync()                         异步启动，当前线程继续执行                        Spider.start()/Spider.runAsync()
        stop()                                     停止爬虫                                        Spider.stop()
        addPipeline(Pipeline)                      添加用户Pipeline，一个Spider可以有多个Pipeline    Spider.addPipeline(new FilePipeline())
        setScheduler(Scheduler)                    设置Scheduler，一个Spider只能有一个Scheduler      Spider.setScheduler(new RedisScheduler())
        setDownloader(Downloader)                  设置Downloader，一个Spider只能有一个Downloader    Spider.setDownloader(new SeleniumDownloader())
        get(String...)                             同步调用，并直接取得结果                           ResultItems result =Spider.get("http:...")
        getAll(String...)                          同步调用，并直接获取一推结果                       List<ResultItems> result=Spider.getAll("http:...","Http:...",...)

        Site.me()可以对爬虫进行一些信息配置，包括编码、抓取间隔、超时时间、重试次数等。
        方法：                                      说明：                                       示例：
        setCharset(String)                         设置编码                                     site.setCharset("utf-8")
        setUserAgent(String)                       设置UserAgent                                site.setUserAgent("Spider")
        setTimeOut(int)                            设置超时时间 单位：毫秒                        site.setTomeOut(3000)
        setRetryTimes(int)                         设置重试次数                                  site.setRetryTimes(3)
        setCycleRetryTimes(int)                    设置循环重试次数                               site.CycleRetryTimes(3)
        addCookie(String,String)                   添加一条Cookie                                site.addCookie("user","password")
        setDomain(String)                          设置域名，需设置域名后，addCookie才可生效        site.setDomain("github.com")
        addHeader(String,String)                   添加一条addHeader                              site.addHeader("Referer","Http:...")
        setHttpProxy(HttpHost)                     设置http代理                                   site.setHttpProxy(new HttpHost("127.0.0.1"),"8080")

爬虫分类：
        网络爬虫按照系统结构和实现技术，大致可以分为以下几种类型：通用网络爬虫、聚焦网络爬虫、增量式网络爬虫、深层网络爬虫
     实际的网络爬虫系统通常都是几种爬虫技术相结合实现的。
        1.通用网络爬虫：通用网络爬虫又称全网络爬虫（Scalable Web Crawler），爬取对象从一些种子URL扩充到整个Web，主要为
     门户站点搜索引擎和大型Web服务提供商采集数据。这类网络爬虫的爬行范围和数量巨大，对于爬行速度和存储空间要求较高，对于爬行
     页面的顺序要求相对较低，同时由于待刷新的页面太多，通常采用并行工作方式，但需要较长时间才能刷新一次页面。
         简单的说就是互联网上抓取所有数据。

        2.聚焦网络爬虫（Focused Crawler）：又称主题网络爬虫（Topical Crawler），是指选择性地爬行那些与预先定义好的主题相关
     网页的爬虫。和通用网络爬虫相比，聚焦爬虫只需要爬行与主题相关的页面，极大地节省了硬件和网络资源，保存的页面也由于数量少而
     更新快，还可以很好地满足一些特定人群对特定领域信息的需求。
         简单的说就是互联网上只抓取某一种数据。

        3.增量式网络爬虫（Incremental Web Crawler）：是指对已下载网页采取增量式更新和只爬行新产生的或者已经发生变化网页的爬虫
    ，它能够在一定程度上保证所爬行的页面是尽可能新的页面。和周期性爬虫和刷新页面的网络爬虫相比，增量式爬虫只会在需要的时间爬行新
    产生或发生更新的页面，并不重新下载没有发生变化的页面，可有效减少数据下载量，及时更新已爬行的网页，减小时间和空间上的耗费，但
    是增加爬行算法的复杂度和实现难度。
         简单的说就是互联网上只抓取刚刚更新的数据。

        4.深层网络爬虫（Deep Web Crawler）：Web页面按存在方式可以分为表层页面（Surface Web）和深层网页（Deep Web，也称
    Invisible Web Pages 或 Hidden Web)。表面网页是指传统搜索引擎可以索引的页面，以超链接可以到达的静态网页为主构成的Web页面。
    Deep Web 是那些大部分内容不能通过静态链接获取的、隐藏在搜索表单后的，只有用户提交一些关键词才能获取的Web页面

WebMagic中的Scheduler组件：
        Scheduler组件俩大功能：
        1.对待抓取得URL队列进行管理
        2.对已抓取的URL进行去重
        队列进行管理：
        类：                                      说明：                                           备注：
        DuplicateRemovedScheduler                抽象基类，提供一些模板                             继承它可以实现注解的功能
        QueueScheduler                           使用内存队列保存待抓取URL
        PriorityScheduler                        使用带有优先级的内存队列保存待抓取URL               耗费内存较QueueScheduler更大，但是当设置了Request.priority之后，只能使用PriorityScheduler才可使优先级生效
        FileCacheQueueScheduler                  使用文件保存抓取URL，可以在关闭程序并下次启动时，    需指定路径，会建立.urls.txt和.cursor.txt俩个文件
                                                 从之前抓取到的URL继续抓取
        RedisScheduler                           使用Redis保存抓取队列，可进行多台机器同时合作抓取    需要安装并启动Redis

        去重：
        类：                                      说明：
        HashSetDuplicateRemover                  使用HashSet来进行去重，占用内存较大
        BloomFilterDuplicateRemover              使用BloomFilter来进行去重，占用内存较小，但是可能漏抓页面
        RedisScheduler是使用Redis的set进行去重，其他的Scheduler默认都使用HashSetDuplicateRemover来进行去重
        如果要使用BloomFilterDuplicateRemover，必须要加入guava依赖

        三种去重方式：
        1.HashSet：使用Java中的HashSet不能重复的特点去重。优点是容易理解。使用方便。缺点：占用内存大，性能较低。
        2.Redis：使用redis的set进行去重。优点是速度快（Redis本身速度就很快），而且去重不会占用爬虫服务器的资源，可以处理更大数据量的数据爬取。缺点：需要准备Redis服务器，增加开发和使用成本。
        3.BloomFilter（布隆过滤器）：使用布隆过滤器也可以实现去重。优点是占用的内存要比使用HashSet要小的多，也适合大量数据的去重操作。缺点有误判的可能。没有重复可以会被判定重复，但是重复数据一定会被判断重复。