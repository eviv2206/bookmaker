package by.bsuir.bookmaker.service.impl.auth;

import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IAuthDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;


public class RegisterCommand implements ICommand {

    private final IAuthDAO AUTH_DAO = DAOFactory.getInstance().getAuthDAO();
    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeat_password = req.getParameter("repeat_password");
        String email = req.getParameter("email");
        if (login.isEmpty()){
            req.setAttribute("error", "Empty login");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (password.isEmpty()){
            req.setAttribute("error", "Empty password");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (!password.equals(repeat_password)){
            req.setAttribute("error", "Passwords do not match");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (password.length() < 8){
            req.setAttribute("error", "Password is too short");
            return JspPageName.REGISTRATION_PAGE;
        }
        if (AUTH_DAO.getUser(login, password) != null){
            req.setAttribute("error", "User already exists");
            return JspPageName.REGISTRATION_PAGE;
        }
        try {
            AUTH_DAO.createUser(login, password, email);
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            return JspPageName.REGISTRATION_PAGE;
        }
        return JspPageName.LOGIN_PAGE;
    }
}
