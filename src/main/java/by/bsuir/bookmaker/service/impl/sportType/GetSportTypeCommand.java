package by.bsuir.bookmaker.service.impl.sportType;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ISportTypeDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import jakarta.servlet.http.HttpServletRequest;

public class GetSportTypeCommand implements ICommand {
    public static final ISportTypeDAO sportTypeDAO = DAOFactory.getInstance().getSportTypeDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        String sportTypeID = req.getParameter("sportTypeID");
        try {
            req.setAttribute("sportType", sportTypeDAO.getSportType(Integer.parseInt(sportTypeID)));
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
        }
        return JspPageName.ADMIN_PAGE;
    }
}
