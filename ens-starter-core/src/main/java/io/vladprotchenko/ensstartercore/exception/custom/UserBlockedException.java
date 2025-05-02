package io.vladprotchenko.ensstartercore.exception.custom;

import io.vladprotchenko.ensstartercore.exception.EnsServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserBlockedException extends EnsServiceException {
    public UserBlockedException(String message) {
        super(message);
    }
}
