package xyz.s1mple.crawler.application;

import org.springframework.stereotype.Service;
import xyz.s1mple.crawler.domain.novel.Novel;
import xyz.s1mple.crawler.interfaces.NovelParser;
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
    private NovelParser parser;

    public Novel crawl(String index) throws IOException, ExecutionException, InterruptedException {
        List<String> directoryHtml = reader.from(index);
        Novel novel = parser.novelFrom(directoryHtml, index);
        novel.write();
        return novel;
    }
}
