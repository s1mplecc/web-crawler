package xyz.s1mple.crawler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);
    private static final String PREFIX = "http://www.biquge.cm/";
    private static final int PARALLELISM_LEVEL = 200;

    private final UrlReader urlReader;
    private final HtmlParser htmlParser;
    private final NovelWriter novelWriter;

    public Crawler() {
        urlReader = new UrlReader();
        htmlParser = new HtmlParser();
        novelWriter = new NovelWriter();
    }

    public void run(String index) throws IOException, ExecutionException, InterruptedException {
        List<String> directoryHtml = urlReader.read(PREFIX + index);
        List<String> uris = htmlParser.chapterUrisFrom(directoryHtml, index);
        novelWriter.write(contentsFrom(uris), String.format("./%s.txt", htmlParser.titleFrom(directoryHtml, index)));
    }

    private String contentsFrom(List<String> uris) throws InterruptedException, ExecutionException {
        final ForkJoinPool pool = new ForkJoinPool(PARALLELISM_LEVEL);
        AtomicInteger i = new AtomicInteger(1);
        return pool.submit(() -> uris.parallelStream()
                .map(uri -> PREFIX + uri)
                .map(url -> {
                    try {
                        log.info("Crawling {}/{}: {}", i.getAndIncrement(), uris.size(), url);
                        return htmlParser.contentFrom(urlReader.read(url));
                    } catch (UrlCannotConnectException ex) {
                        ex.printStackTrace();
                    }
                    return "";
                })
                .reduce((x, y) -> x + y)
                .orElse(""))
                .get();
    }
}
