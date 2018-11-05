package xyz.s1mple.crawler.core;

public class UrlCannotConnectException extends RuntimeException {
    public UrlCannotConnectException(String message) {
        super(message);
    }

    public UrlCannotConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
