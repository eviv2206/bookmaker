package by.bsuir.bookmaker.service;

import by.bsuir.bookmaker.exception.AppException;

public class CommandException extends AppException {
    private static final long serialVersionUID = 1L;
    public CommandException(String msg){
        super(msg);
    }
    public CommandException(String msg, Exception e){
        super(msg, e);
    }
}
