package by.bsuir.bookmaker.service.impl.sportType;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ISportTypeDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import jakarta.servlet.http.HttpServletRequest;

public class EditSportTypeCommand implements ICommand {
    public static final ISportTypeDAO sportTypeDAO = DAOFactory.getInstance().getSportTypeDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !user.isPrivileges()) {
            return JspPageName.LOGIN_PAGE;
        }
        String sportTypeID = req.getParameter("sportTypeID");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        try {
            sportTypeDAO.updateSportType(name, description, Integer.parseInt(sportTypeID));
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
