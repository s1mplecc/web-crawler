package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Crawler {
    private static final String PREFIX = "http://www.biquge.cm/";
    private final UrlReader urlReader = new UrlReader();
    private final HtmlParser htmlParser = new HtmlParser();

    public static void main(String[] args) throws IOException {
        String index = "12/12366";
        new Crawler().run(index);
    }

    public void run(String novelIndex) throws IOException {
        BufferedReader reader = urlReader.read(PREFIX + novelIndex);
        List<String> uris = htmlParser.parseChapterUris(reader, novelIndex);
        String content = uris.parallelStream()
                .map(uri -> PREFIX + uri)
                .map(url -> {
                    try {
                        return htmlParser.parseContent(urlReader.read(url));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return "";
                })
                .reduce((x, y) -> x + y)
                .orElse("");
        new NovelWriter("./a.txt").write(content);
    }
}
