package xyz.s1mple.crawler;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlParserTest {
    private BufferedReader br1;
    private BufferedReader br2;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        br1 = bufferReaderBy("test-parseChapterUris.html");
        br2 = bufferReaderBy("test-parseContent.html");
    }

    private BufferedReader bufferReaderBy(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(
                new File(HtmlParserTest.class.getClassLoader().getResource(filename).getPath())), "GBK"));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() throws IOException {
        br1.close();
        br2.close();
    }

    @Test
    void should_parse_chapter_uris_from_html() throws IOException {
        List<String> uris = HtmlParser.parseChapterUris(br1, "9/9434");

        assertThat(uris).hasSize(321);
        assertThat(uris.get(0)).isEqualTo("9/9434/7418069.html");
        assertThat(uris.get(320)).isEqualTo("9/9434/7752299.html");
    }

    @Test
    void should_parse_novel_content_from_html() {
        String content = HtmlParser.parseContent(br2);

        assertThat(content).isNotEmpty();
        assertThat(content).startsWith("    炎炎夏日");
    }
}