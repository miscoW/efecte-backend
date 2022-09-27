package pl.mwawrzyn.efectebackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mwawrzyn.efectebackend.models.dto.ErrorDto;
import pl.mwawrzyn.efectebackend.models.exception.ElementNotFoundException;
import pl.mwawrzyn.efectebackend.models.exception.TooLongNoteException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorDto> handleOptimisticLockingException(ObjectOptimisticLockingFailureException exception) {
        ErrorDto response = new ErrorDto();
        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setMassage("Object already edited by other user or deleted");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TooLongNoteException.class)
    public ResponseEntity<Object> handleTooLongNote(TooLongNoteException exception) {
        ErrorDto response = new ErrorDto();
        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setMassage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ElementNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ElementNotFoundException exception) {
        ErrorDto response = new ErrorDto();
        response.setErrorCode(HttpStatus.NOT_FOUND.value());
        response.setMassage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
