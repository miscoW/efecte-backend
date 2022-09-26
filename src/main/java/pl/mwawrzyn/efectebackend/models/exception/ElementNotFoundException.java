package pl.mwawrzyn.efectebackend.models.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends Exception{
    public ElementNotFoundException() {
        super();
    }
}
