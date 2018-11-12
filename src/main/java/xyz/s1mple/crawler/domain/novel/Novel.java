package xyz.s1mple.crawler.domain.novel;

import xyz.s1mple.crawler.application.exceptions.CannotWriteToFileException;

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

    public void write() {
        String target = String.format("./%s.txt", title());
        try (FileWriter fw = new FileWriter(new File(target))) {
            fw.write(contents());
            fw.flush();
        } catch (IOException ex) {
            throw new CannotWriteToFileException(target, ex);
        }
    }

    public String index() {
        return index;
    }

    public String title() {
        return title;
    }

    public String contents() {
        return contents;
    }
}
