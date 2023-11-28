package by.bsuir.bookmaker.service.impl.betType;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IBetTypeDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;

public class GetBetTypeCommand implements ICommand {
    private static final IBetTypeDAO betTypeDAO = DAOFactory.getInstance().getBetTypeDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        String sportTypeID = req.getParameter("betTypeID");
        try {
            req.setAttribute("betType", betTypeDAO.getBetType(Integer.parseInt(sportTypeID)));
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
        }
        return JspPageName.ADMIN_PAGE;
    }
}
