package by.bsuir.bookmaker.service.impl;

import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.service.ICommand;

import jakarta.servlet.http.HttpServletRequest;

public class NoSuchCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req) {
        return JspPageName.ERROR_PAGE;
    }
}
