package xyz.s1mple.crawler.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Novel {
    private String index;
    private String title;
    private String contents;

    public Novel(String index, String title, String contents) {
        this.index = index;
        this.title = title;
        this.contents = contents;
    }

    public void write() throws IOException {
        FileWriter fw = new FileWriter(new File(String.format("./%s.txt", title())));
        fw.write(contents());
        fw.flush();
        fw.close();
    }

    public String index() {
        return index;
    }

    public String title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String contents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
