package xyz.s1mple.crawler.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.s1mple.crawler.application.exceptions.UrlCannotConnectException;
import xyz.s1mple.crawler.interfaces.Parser;
import xyz.s1mple.crawler.interfaces.Reader;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static xyz.s1mple.crawler.domain.HtmlParser.HtmlTag.*;

@Component
public class HtmlParser implements Parser {
    private static final Logger log = LoggerFactory.getLogger(HtmlParser.class);
    private static final int PARALLELISM_LEVEL = 32;

    @Resource
    private Reader reader;

    @Override
    public String titleFrom(List<String> directoryHtml, String index) {
        return directoryHtml.parallelStream()
                .map(String::trim)
                .filter(line -> line.matches(H1 + "\\S*" + H1_END))
                .findFirst()
                .map(line -> line.substring(H1.length(), line.indexOf(H1_END)))
                .orElse(index);
    }

    @Override
    public String contentsFrom(List<String> directoryHtml, String index) throws InterruptedException, ExecutionException {
        List<String> uris = chapterUrisFrom(directoryHtml, index);
        final ForkJoinPool pool = new ForkJoinPool(PARALLELISM_LEVEL);
        AtomicInteger i = new AtomicInteger(1);
        return pool.submit(() -> uris.parallelStream()
                .map(url -> {
                    try {
                        log.info("Crawling {}/{}: {}", i.getAndIncrement(), uris.size(), url);
                        return contentFrom(reader.from(url));
                    } catch (UrlCannotConnectException ex) {
                        ex.printStackTrace();
                    }
                    return "";
                })
                .reduce((x, y) -> x + y)
                .orElse(""))
                .get();
    }

    private List<String> chapterUrisFrom(List<String> directoryHtml, String index) {
        return directoryHtml.parallelStream()
                .filter(line -> line.matches(".*" + A_HREF + index + ".*"))
                .map(line -> line.substring(line.indexOf(A_HREF + index) + A_HREF.length(), line.indexOf("\">")))
                .collect(Collectors.toList());
    }

    private String contentFrom(List<String> contentHtml) {
        AtomicBoolean isContent = new AtomicBoolean(false);
        return contentHtml.stream()
                .reduce("", (x, y) -> {
                    if (y.contains(DIV_CONTENT)) {
                        isContent.set(true);
                        return x + y.replaceAll(DIV_CONTENT, "").trim();
                    }
                    if (isContent.get() && y.contains(DIV_END)) {
                        isContent.set(false);
                        return x + y.replaceAll(DIV_END, "").trim();
                    }
                    if (isContent.get()) {
                        return x + y.trim();
                    }
                    return x;
                })
                .replaceAll("&nbsp;", " ")
                .replaceAll("<br\\s*/>", "\r\n");
    }

    static class HtmlTag {
        static final String DIV_CONTENT = "<div id=\"content\">";
        static final String A_HREF = "<a href=\"/";
        static final String DIV_END = "</div>";
        static final String H1 = "<h1>";
        static final String H1_END = "</h1>";
    }
}
