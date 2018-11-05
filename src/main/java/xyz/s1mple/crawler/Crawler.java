package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Crawler {
    private static final String PREFIX = "http://www.biquge.cm/";
    private final Connector connector = new Connector();

    public static void main(String[] args) throws IOException {
        String index = "12/12366";
        new Crawler().run(index);
    }

    public void run(String novelIndex) throws IOException {
        long a = System.currentTimeMillis();

        BufferedReader reader = connector.read(PREFIX + novelIndex);
        List<String> uris = HtmlParser.parseChapterUris(reader, novelIndex);
        String content = uris.parallelStream()
                .map(uri -> PREFIX + uri)
                .map(url -> HtmlParser.parseContent(connector.read(url)))
                .reduce((x, y) -> x + y)
                .orElse("");
        new NovelWriter("./a.txt").write(content);

        long b = System.currentTimeMillis();

        System.out.println("总计用时：" + (b - a));
    }
}
