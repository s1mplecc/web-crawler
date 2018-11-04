package xyz.s1mple.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NovelWriter {
    private String pathname;

    public NovelWriter(String pathname) {
        this.pathname = pathname;
    }

    public void write(String content) throws IOException {
        FileWriter fw = new FileWriter(new File(pathname));
        fw.write(content);
        fw.flush();
        fw.close();
    }
}
