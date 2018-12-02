# web-crawler

Java 网站爬虫，可以爬取 http://www.biquge.cm/ 上的小说，基于之前的 [WebScraping_v2.0](https://github.com/s1mplecc/WebScraping_v2.0) 项目进行重构。

详细内容见我的博客 [Java 网络爬虫](https://s1mple.xyz/java-web-crawler/)

## Python 版本

Python 版本请见 [crawler.py](https://gist.github.com/s1mplecc/dfd15f58cbbe5fad2ab13bc2246d49f4)

## v2.0

```
git checkout master
```

基于 Spring Boot 的 Web 项目，启动后访问 `http://localhost:8080/novel?index=12/12456` 下载文件

## v1.0

```
git checkout v1.0
```

**使用说明**

1. 仅限于爬取 http://www.biquge.cm/ 网站

2. **CrawlerUI** 用户接口类

	```
	String index = "7/7127";
	new Crawler().run(index);
	```
	**index**：小说的编号，在 http://www.biquge.cm/ 搜索小说即可获取，如《大道朝天》章节目录网址为 http://www.biquge.cm/9/9422/，则 index 为 9/9422
	
3. 默认写入的文件名为 `./$TITLE.txt`，写入当前目录下，文件名为小说名
