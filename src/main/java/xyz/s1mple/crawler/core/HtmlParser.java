package xyz.s1mple.crawler.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    private static final String DIV_CONTENT = "<div id=\"content\">";
    private static final String A_HREF = "<a href=\"/";
    private static final String DIV_LIST = "<div id=\"list\">";
    private static final String DIV_END = "</div>";
    private static final String H1 = "<h1>";
    private static final String H1_END = "</h1>";

    // todo: close br; readLine -> parallelStream


    public String titleFrom(BufferedReader html) throws IOException {
        String line;
        while ((line = html.readLine()) != null) {
            if (line.trim().matches(H1 + "\\S*" + H1_END)) {
                return line.substring(line.indexOf(H1) + H1.length(), line.indexOf(H1_END));
            }
        }
        return "";
    }

    public List<String> chapterUrisFrom(BufferedReader html, String novelIndex) throws IOException {
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
                String uri = line.substring(line.indexOf(A_HREF + novelIndex) + A_HREF.length(), line.indexOf("\">"));
                uris.add(uri);
            }
        }

        return uris;
    }

    public String contentFrom(BufferedReader html) throws IOException {
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
                append(content, line.substring(0, line.lastIndexOf(DIV_END)).trim());
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
