package by.bsuir.bookmaker.service.impl.event;

import by.bsuir.bookmaker.beans.Event;
import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class GetEventCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetEventCommand.class);
    private static final IEventDAO eventDAO = DAOFactory.getInstance().getEventDAO();

    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Event event = eventDAO.getEvent(id);
            req.setAttribute("event", event);
            return JspPageName.EVENT_PAGE;
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
