package xyz.s1mple.crawler.gateways;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.s1mple.crawler.application.exceptions.UrlCannotConnectException;
import xyz.s1mple.crawler.interfaces.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UrlReader implements Reader {
    private static final Logger log = LoggerFactory.getLogger(UrlReader.class);

    private static final String PREFIX = "http://www.biquge.cm/";
    private static final String GBK = "GBK";

    @Override
    public List<String> from(String uri) throws UrlCannotConnectException {
        String url = PREFIX + uri;
        long before = System.currentTimeMillis();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connect(url).getInputStream(), GBK))) {
            log.debug("connect to url {} using {} ms", url, System.currentTimeMillis() - before);
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
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