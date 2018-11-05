package xyz.s1mple.crawler.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NovelWriter {
    public void write(String content, String pathname) throws IOException {
        FileWriter fw = new FileWriter(new File(pathname));
        fw.write(content);
        fw.flush();
        fw.close();
    }
}
