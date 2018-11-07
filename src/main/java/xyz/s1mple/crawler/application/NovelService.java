package xyz.s1mple.crawler.application;

import org.springframework.stereotype.Service;
import xyz.s1mple.crawler.domain.Novel;
import xyz.s1mple.crawler.interfaces.Parser;
import xyz.s1mple.crawler.interfaces.Reader;

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

    public void crawl(String index) throws IOException, ExecutionException, InterruptedException {
        List<String> directoryHtml = reader.from(index);
        Novel novel = parser.novelFrom(directoryHtml, index);
        novel.write();
    }
}
