# web-crawler

Java 网站爬虫，可以爬取 http://www.biquge.cm/ 上的小说，基于之前的 [WebScraping_v2.0](https://github.com/s1mplecc/WebScraping_v2.0) 项目进行重构。

## 更新内容

1. 使用 Gradle 构建项目
	```
	xyz.s1mple.crawler:web-crawler:1.0-SNAPSHOT
	```
2. 使用 JDK8 的 `parallelStream` 并行流处理，并使用 `new ForkJoinPool(PARALLELISM_LEVEL);` 自定义线程数。经测试，在网络稳定的情况下，爬取两千章的小说大概耗时在 20s 左右（受网速影响）。

	伪代码如下：
	
	```
	final ForkJoinPool pool = new ForkJoinPool(PARALLELISM_LEVEL);
    pool.submit(() -> urls.parallelStream()
            .map(url -> htmlParser.parseContent(urlReader.read(url)))
            .reduce((x, y) -> x + y)
            .orElse(""))
            .get();
	```

## 使用说明

1. 仅限于爬取 http://www.biquge.cm/ 网站

2. **CrawlerUI** 用户接口类

	```
	String index = "7/7127";
	new Crawler().run(index);
	```
	**index**：小说的编号，在 http://www.biquge.cm/ 搜索小说即可获取，如《大道朝天》章节目录网址为 http://www.biquge.cm/9/9422/，则 index 为 9/9422
	
3. 默认写入的文件名为 `./$TITLE.txt`，写入当前目录下，文件名为小说名

## 处理逻辑

HtmlParser 解析 Html 的类，主要用于解析小说名（Title）、小说章节的 Url、小说各章内容

### Title

解析小说章节目录网址 Html 源码的 `<h1>...</h1>` 标签中的值

```
// 章节目录网址
http://www.biquge.cm/9/9422/

// html
<h1>大道朝天</h1>

// after parse
大道朝天
```

### Chapter URIs

解析小说章节目录网址 Html 源码的 `<div id="list">...</div>` 中包含的 `<a href="...">`

```
// 章节目录网址
http://www.biquge.cm/9/9422/

// html
<div id="list">
<dl><dd><a href="/9/9422/6927857.html">第一章 三千里禁</a></dd>
<dd><a href="/9/9422/6927858.html">第二章 斩天一剑</a></dd>
<dd><a href="/9/9422/6927859.html">第三章 再次踏进那条河的白衣少年</a></dd>
...
</dl></div>

// after parse
["/9/9422/6927859.html", "/9/9422/6927858.html", "/9/9422/6927857.html" ...]
```

### Content

根据 Chapter URIs 解析每一章的内容，最后拼接在一起写入文件。根据 `<div id="content">...</div>` 解析，`&nbsp;` 替换为空格，`<br />` 替换为换行符

```
// 某一章的网址
http://www.biquge.cm/9/9422/6927857.html

// html
<div id="content">&nbsp;&nbsp;&nbsp;&nbsp;朝天大陆南方，一片青山绵延数千里，数百秀峰终年隐在云雾中。<br />
<br />
&nbsp;&nbsp;&nbsp;&nbsp;天下第一修行大派青山宗便在此间，普通人极难一睹真容。<br />
<br />
...
</div>

// after parse

    朝天大陆南方，一片青山绵延数千里，数百秀峰终年隐在云雾中。
    
    天下第一修行大派青山宗便在此间，普通人极难一睹真容。
```
