package xyz.s1mple.crawler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlReader {
    private static final Logger log = LoggerFactory.getLogger(UrlReader.class);
    private static final String GBK = "GBK";

    public UrlReader() {
    }

    public BufferedReader read(String url) throws UrlCannotConnectException {
        try {
            return new BufferedReader(new InputStreamReader(connect(url).getInputStream(), GBK));
        } catch (IOException ex) {
            log.error("can not to connect url: {}", url);
            throw new UrlCannotConnectException(url);
        }
    }

    private URLConnection connect(String novelUrl) throws IOException {
        URL url = new URL(novelUrl);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        connection.connect();
        return connection;
    }
}