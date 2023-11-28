package by.bsuir.bookmaker.service.impl.locale;

import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req) {
        String language = req.getParameter("language");
        req.getSession().setAttribute("language", language);
        return req.getHeader("referer");
    }
}
