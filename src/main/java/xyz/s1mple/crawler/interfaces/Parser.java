package xyz.s1mple.crawler.interfaces;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Parser {
    String titleFrom(List<String> directoryHtml, String index);

    String contentsFrom(List<String> directoryHtml, String index) throws InterruptedException, ExecutionException;
}
