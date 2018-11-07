package xyz.s1mple.crawler.application;

import xyz.s1mple.crawler.interfaces.Reader;
import xyz.s1mple.crawler.interfaces.Writer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CrawlerService {
    private static final String PREFIX = "http://www.biquge.cm/";

    @Resource
    private Reader reader;
    @Resource
    private Writer writer;

    public void run(String index) throws IOException, ExecutionException, InterruptedException {
        List<String> directoryHtml = reader.read(PREFIX + index);
//        List<String> uris = htmlParser.chapterUrisFrom(directoryHtml, index);
//        writer.write(contentsFrom(uris), String.format("./%s.txt", htmlParser.titleFrom(directoryHtml, index)));
    }
}
