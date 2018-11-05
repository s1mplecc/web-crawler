package xyz.s1mple.crawler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(HtmlParser.class);
    private static final String PREFIX = "http://www.biquge.cm/";
    private static final int PARALLELISM_LEVEL = 200;

    private final UrlReader urlReader;
    private final HtmlParser htmlParser;

    public Crawler() {
        urlReader = new UrlReader();
        htmlParser = new HtmlParser();
    }

    public void run(String novelIndex) throws IOException, ExecutionException, InterruptedException {
        List<String> uris = htmlParser.parseChapterUris(urlReader.read(PREFIX + novelIndex), novelIndex);
        String content = content(uris);
        new NovelWriter("./a.txt").write(content);
    }

    private String content(List<String> uris) throws InterruptedException, ExecutionException {
        final ForkJoinPool pool = new ForkJoinPool(PARALLELISM_LEVEL);
        return pool.submit(() -> uris.parallelStream()
                .map(uri -> PREFIX + uri)
                .map(url -> {
                    try {
                        log.info("正在爬取 {}", url);
                        return htmlParser.parseContent(urlReader.read(url));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return "";
                })
                .reduce((x, y) -> x + y)
                .orElse(""))
                .get();
    }
}