package org.naivs.perimeter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConverterException extends RuntimeException {

    public ConverterException(String message) {
        super(message);
    }

    public <S, D> ConverterException(Class<S> source, D destination) {
        super(String.format(
                "Unable to convert %s to %s",
                source.getName(),
                destination));
    }
}
