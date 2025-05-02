package io.vladprotchenko.ensstartercore.exception.custom;

import io.vladprotchenko.ensstartercore.exception.EnsServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServiceException extends EnsServiceException {

    public InternalServiceException(String message) {
        super(message);
    }
}
