package by.bsuir.bookmaker.exception;

public class AppException extends Error {
    private static final long serialVersionUID = 1L;
    private Exception hiddenException;
    public AppException(String msg){
        super(msg);
    }
    public AppException(String msg, Exception e){
        super(msg);
        hiddenException = e;
    }
    public Exception getHiddenException(){
        return hiddenException;
    }
}
