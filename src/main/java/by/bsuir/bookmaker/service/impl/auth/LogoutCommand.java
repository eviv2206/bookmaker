package by.bsuir.bookmaker.service.impl.auth;

import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;


public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().invalidate();
        return JspPageName.LOGIN_PAGE;
    }
}
