package xyz.s1mple.crawler.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static xyz.s1mple.crawler.domain.HtmlParser.HtmlTag.*;


public class HtmlParser {

    public String titleFrom(List<String> directoryHtml, String index) {
        return directoryHtml.parallelStream()
                .map(String::trim)
                .filter(line -> line.matches(H1 + "\\S*" + H1_END))
                .findFirst()
                .map(line -> line.substring(H1.length(), line.indexOf(H1_END)))
                .orElse(index);
    }

    public List<String> chapterUrisFrom(List<String> directoryHtml, String index) {
        return directoryHtml.parallelStream()
                .filter(line -> line.matches(".*" + A_HREF + index + ".*"))
                .map(line -> line.substring(line.indexOf(A_HREF + index) + A_HREF.length(), line.indexOf("\">")))
                .collect(Collectors.toList());
    }

    public String contentFrom(List<String> contentHtml) {
        AtomicBoolean isContent = new AtomicBoolean(false);
        return contentHtml.stream()
                .reduce("", (x, y) -> {
                    if (y.contains(DIV_CONTENT)) {
                        isContent.set(true);
                        return x + y.replaceAll(DIV_CONTENT, "").trim();
                    }
                    if (isContent.get() && y.contains(DIV_END)) {
                        isContent.set(false);
                        return x + y.replaceAll(DIV_END, "").trim();
                    }
                    if (isContent.get()) {
                        return x + y.trim();
                    }
                    return x;
                })
                .replaceAll("&nbsp;", " ")
                .replaceAll("<br\\s*/>", "\r\n");
    }

    static class HtmlTag {
        static final String DIV_CONTENT = "<div id=\"content\">";
        static final String A_HREF = "<a href=\"/";
        static final String DIV_END = "</div>";
        static final String H1 = "<h1>";
        static final String H1_END = "</h1>";
    }
}
