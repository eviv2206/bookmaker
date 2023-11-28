package by.bsuir.bookmaker.service;


import jakarta.servlet.http.HttpServletRequest;

public interface ICommand {
    public String execute(HttpServletRequest req) ;
}
