package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;

public class Crawler implements Runnable {
    private final Connector connector = new Connector();

    public static void main(String[] args) {
        new Crawler().run();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = connector.read("http://www.biquge.cm/9/9434/7420206.html");
            String content = HtmlParser.parseContent(reader);
            new NovelWriter("./a.txt").write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
