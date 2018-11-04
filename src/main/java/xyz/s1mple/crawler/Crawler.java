package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Crawler {
    private static final String PREFIX = "http://www.biquge.cm/";
    private final Connector connector = new Connector();

    public static void main(String[] args) {
        String index = "12/12367";
        new Crawler().run(PREFIX + index, index);
    }

    public void run(String url, String novelIndex) {
        try {
            BufferedReader reader = connector.read(url);
            List<String> uris = HtmlParser.parseChapterUris(reader, novelIndex);
            String content = uris.parallelStream()
                    .map(uri -> PREFIX + uri)
                    .map(item -> {
                        try {
                            return HtmlParser.parseContent(connector.read(item));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "";
                    })
                    .reduce((x, y) -> x + y)
                    .orElse("");
            new NovelWriter("./a.txt").write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
