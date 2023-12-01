package by.bsuir.bookmaker.service.impl.betTypeEvent;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class AddBetTypeEventCommand implements ICommand {
    private static final Logger log = Logger.getLogger(AddBetTypeEventCommand.class);
    private static final IBetTypeEventDAO betTypeEventDAO = DAOFactory.getInstance().getBetTypeEventDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null || !user.isPrivileges()) {
            return JspPageName.LOGIN_PAGE;
        }
        int betTypeEventEventId = Integer.parseInt(req.getParameter("betTypeEventEventId"));
        int betTypeEventBetTypeId = Integer.parseInt(req.getParameter("betTypeEventBetTypeId"));
        double betTypeEventCoefficient = Double.parseDouble(req.getParameter("betTypeEventCoefficient"));
        try {
            betTypeEventDAO.add(betTypeEventEventId, betTypeEventBetTypeId, betTypeEventCoefficient);
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
