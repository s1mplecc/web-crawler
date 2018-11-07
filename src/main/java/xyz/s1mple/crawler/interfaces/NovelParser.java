package xyz.s1mple.crawler.interfaces;

import xyz.s1mple.crawler.domain.Novel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NovelParser extends TitleParser, ContentsParser {
    Novel novelFrom(List<String> directoryHtml, String index) throws ExecutionException, InterruptedException;
}
