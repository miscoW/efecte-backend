package pl.mwawrzyn.efectebackend.models.exception;

public class TooLongNoteException extends Exception {
    public TooLongNoteException(String message) {
        super(message);
    }
}
