package xyz.s1mple.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.s1mple.crawler.core.HtmlParser;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlParserTest {
    private List<String> lines1;
    private List<String> lines2;

    @BeforeEach
    void setUp() {
        lines1 = linesFrom("test-parseChapterUris.html");
        lines2 = linesFrom("test-parseContent.html");
    }

    private List<String> linesFrom(String filename) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                new File(HtmlParserTest.class.getClassLoader().getResource(filename).getPath())), "GBK"))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void should_parse_novel_title_from_html() {
        String title = new HtmlParser().titleFrom(lines1, "default");

        assertThat(title).isEqualTo("三寸人间");
    }

    @Test
    void should_parse_chapter_uris_from_html() {
        List<String> uris = new HtmlParser().chapterUrisFrom(lines1, "9/9434");

        assertThat(uris).hasSize(321);
        assertThat(uris.get(0)).isEqualTo("9/9434/7418069.html");
        assertThat(uris.get(320)).isEqualTo("9/9434/7752299.html");
    }

    @Test
    void should_parse_novel_content_from_html() throws IOException {
        String content = new HtmlParser().contentFrom(lines2);

        assertThat(content).isNotEmpty();
        assertThat(content).startsWith("    炎炎夏日");
        assertThat(content).endsWith("急需守护~~~");
    }
}