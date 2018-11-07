package xyz.s1mple.crawler.interfaces;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ContentsParser {
    String contentsFrom(List<String> directoryHtml, String index) throws InterruptedException, ExecutionException;

    List<String> chapterUrisFrom(List<String> directoryHtml, String index);

    String contentFrom(List<String> contentHtml);
}
