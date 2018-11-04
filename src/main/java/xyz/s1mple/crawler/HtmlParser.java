package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    public static List<String> parseChapterUris(BufferedReader html, String novelId) throws IOException {
        String line;
        List<String> uris = new ArrayList<>();
        boolean isUris = false;
        while ((line = html.readLine()) != null) {
            if (line.contains("<div id=\"list\">")) {
                isUris = true;
                continue;
            }

            if (isUris && line.contains("</div>")) {
                break;
            }

            String href = "<a href=\"/";
            if (isUris && line.contains(href)) {
                String uri = line.substring(line.indexOf(href + novelId) + href.length(), line.indexOf("\">"));
                uris.add(uri);
            }
        }

        return uris;
    }

    public static void parseContent(String html) {

    }
}
