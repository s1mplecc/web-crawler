package xyz.s1mple.crawler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(HtmlParser.class);
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

    public void run(String novelIndex) throws IOException, ExecutionException, InterruptedException {
        BufferedReader directoryHtml = urlReader.read(PREFIX + novelIndex);
        String title = htmlParser.parseTitle(directoryHtml);
        List<String> uris = htmlParser.parseChapterUris(directoryHtml, novelIndex);
        String contents = parseContentsFrom(uris);
        novelWriter.write(contents, String.format("./%s.txt", title));
    }

    private String parseContentsFrom(List<String> uris) throws InterruptedException, ExecutionException {
        final ForkJoinPool pool = new ForkJoinPool(PARALLELISM_LEVEL);
        AtomicInteger i = new AtomicInteger(1);
        return pool.submit(() -> uris.parallelStream()
                .map(uri -> PREFIX + uri)
                .map(url -> {
                    try {
                        log.info("Crawling {}/{}: {}", i.getAndIncrement(), uris.size(), url);
                        return htmlParser.parseContent(urlReader.read(url));
                    } catch (IOException | UrlCannotConnectException ex) {
                        ex.printStackTrace();
                    }
                    return "";
                })
                .reduce((x, y) -> x + y)
                .orElse(""))
                .get();
    }
}
