package pl.mwawrzyn.efectebackend.models.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TooLongNoteException extends Exception {
    public TooLongNoteException() {
        super();
    }
}
