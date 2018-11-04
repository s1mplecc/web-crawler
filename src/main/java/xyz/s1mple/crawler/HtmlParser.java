package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    private static final String DIV_CONTENT = "<div id=\"content\">";
    private static final String A_HREF = "<a href=\"/";
    private static final String DIV_LIST = "<div id=\"list\">";
    private static final String DIV_END = "</div>";

    public static List<String> parseChapterUris(BufferedReader html, String novelId) throws IOException {
        List<String> uris = new ArrayList<>();
        boolean isUris = false;
        String line;
        while ((line = html.readLine()) != null) {
            if (line.contains(DIV_LIST)) {
                isUris = true;
                continue;
            }

            if (isUris && line.contains(DIV_END)) {
                break;
            }

            if (isUris && line.contains(A_HREF)) {
                String uri = line.substring(line.indexOf(A_HREF + novelId) + A_HREF.length(), line.indexOf("\">"));
                uris.add(uri);
            }
        }

        return uris;
    }

    public static String parseContent(BufferedReader html) throws IOException {
        StringBuilder content = new StringBuilder();
        boolean isContent = false;
        String line;
        while ((line = html.readLine()) != null) {
            if (line.contains(DIV_CONTENT)) {
                isContent = true;
                append(content, line.trim().substring(DIV_CONTENT.length()).trim());
                continue;
            }

            if (isContent && line.contains(DIV_END)) {
                break;
            }

            if (isContent) {
                append(content, line);
            }
        }

        return content.toString();
    }

    private static void append(StringBuilder content, String line) {
        String replaceLine = line.trim()
                .replaceAll("&nbsp;", " ")
                .replaceAll("<br\\s*/>", "\r\n");
        content.append(replaceLine);
    }
}
