package xyz.s1mple.crawler.gateways;

import xyz.s1mple.crawler.interfaces.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NovelWriter implements Writer {
    @Override
    public void write(String content, String pathname) throws IOException {
        FileWriter fw = new FileWriter(new File(pathname));
        fw.write(content);
        fw.flush();
        fw.close();
    }
}
