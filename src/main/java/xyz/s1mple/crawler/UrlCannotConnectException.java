package xyz.s1mple.crawler;

public class UrlCannotConnectException extends RuntimeException {
    public UrlCannotConnectException(String message) {
        super(message);
    }

    public UrlCannotConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
