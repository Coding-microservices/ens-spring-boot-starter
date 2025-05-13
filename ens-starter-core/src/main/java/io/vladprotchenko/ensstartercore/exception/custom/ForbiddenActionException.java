package io.vladprotchenko.ensstartercore.exception.custom;

import io.vladprotchenko.ensstartercore.exception.EnsServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenActionException extends EnsServiceException {
    public ForbiddenActionException(String message) {
        super(message);
    }
}
