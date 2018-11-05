package xyz.s1mple.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Connector {
    private static final Logger log = LoggerFactory.getLogger(Connector.class);

    private static final String GBK = "GBK";

    public Connector() {
    }

    public BufferedReader read(String url) {
        URLConnection connection;
        try {
            connection = connect(url);
            return new BufferedReader(new InputStreamReader(connection.getInputStream(), GBK));
        } catch (IOException e) {
            log.error(String.format("can not to connect url: %s", url));
            e.printStackTrace();
        }

        return null;
    }

    private URLConnection connect(String novelUrl) throws IOException {
        URL url = new URL(novelUrl);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        connection.connect();

        return connection;
    }
}