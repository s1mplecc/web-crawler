package xyz.s1mple.crawler.application;

import org.springframework.stereotype.Service;
import xyz.s1mple.crawler.interfaces.Parser;
import xyz.s1mple.crawler.interfaces.Reader;
import xyz.s1mple.crawler.interfaces.Writer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class NovelService {

    @Resource
    private Reader reader;
    @Resource
    private Parser parser;
    @Resource
    private Writer writer;

    public void crawl(String index) throws IOException, ExecutionException, InterruptedException {
        List<String> directoryHtml = reader.from(index);
        String title = parser.titleFrom(directoryHtml, index);
        String contents = parser.contentsFrom(directoryHtml, index);
        writer.write(contents, String.format("./%s.txt", title));
    }
}
