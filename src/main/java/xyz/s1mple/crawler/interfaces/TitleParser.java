package xyz.s1mple.crawler.interfaces;

import java.util.List;

public interface TitleParser {
    String titleFrom(List<String> directoryHtml, String index);
}
