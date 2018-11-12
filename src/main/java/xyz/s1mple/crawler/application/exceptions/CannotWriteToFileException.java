package xyz.s1mple.crawler.application.exceptions;

public class CannotWriteToFileException extends RuntimeException {
    public CannotWriteToFileException(String message) {
        super(message);
    }

    public CannotWriteToFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
