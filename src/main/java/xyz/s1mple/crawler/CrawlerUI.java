package xyz.s1mple.crawler;

import xyz.s1mple.crawler.core.Crawler;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CrawlerUI {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String index = "11/11140";
        new Crawler().run(index);
    }
}