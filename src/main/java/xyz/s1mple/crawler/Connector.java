package xyz.s1mple.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Connector {
    private static final String GBK = "GBK";

    public Connector() {
    }

    public BufferedReader read(String url) throws IOException {
        URLConnection connection = connect(url);
        return new BufferedReader(new InputStreamReader(connection.getInputStream(), GBK));
    }

    private URLConnection connect(String novelUrl) throws IOException {
        URL url = new URL(novelUrl);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        connection.connect();

        return connection;
    }
}