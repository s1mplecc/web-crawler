package xyz.s1mple.crawler.interfaces;

import java.io.IOException;

public interface Writer {
    void write(String content, String target) throws IOException;
}
