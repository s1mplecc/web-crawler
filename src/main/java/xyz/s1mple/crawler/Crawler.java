package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Crawler {
    private static final String PREFIX = "http://www.biquge.cm/";
    private final UrlReader urlReader = new UrlReader();

    public static void main(String[] args) throws IOException {
        String index = "9/9422";
        new Crawler().run(index);
    }

    public void run(String novelIndex) throws IOException {
        BufferedReader reader = urlReader.read(PREFIX + novelIndex);
        List<String> uris = HtmlParser.parseChapterUris(reader, novelIndex);
        String content = uris.parallelStream()
                .map(uri -> PREFIX + uri)
                .map(url -> HtmlParser.parseContent(urlReader.read(url)))
                .reduce((x, y) -> x + y)
                .orElse("");
        new NovelWriter("./a.txt").write(content);
    }
}
