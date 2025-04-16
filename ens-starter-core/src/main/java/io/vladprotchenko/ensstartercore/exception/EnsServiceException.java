package io.vladprotchenko.ensstartercore.exception;

public class EnsServiceException extends RuntimeException {

    public EnsServiceException(String message) {
        super(message);
    }

    public EnsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
