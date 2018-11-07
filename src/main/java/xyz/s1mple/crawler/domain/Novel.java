package xyz.s1mple.crawler.domain;

import java.util.List;

public class Novel {
    private String index;
    private String title;
    private List<String> chapterUris;
    private String contents;

    public Novel(String index) {
        this.index = index;
    }
}
