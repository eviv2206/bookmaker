package by.bsuir.bookmaker.service.impl.auth;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IAuthDAO;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;


public class SignInCommand implements ICommand {

    private final IAuthDAO AUTH_DAO = DAOFactory.getInstance().getAuthDAO();

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Empty login or password");
            return JspPageName.LOGIN_PAGE;
        }
        if (password.length() < 8){
            req.setAttribute("error", "Password is too short");
            return JspPageName.LOGIN_PAGE;
        }
        User user = AUTH_DAO.getUser(login, password);
        req.getSession().setAttribute("curruser", user);
        return JspPageName.LOGIN_PAGE;
    }
}
