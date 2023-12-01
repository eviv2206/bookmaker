package by.bsuir.bookmaker.service.impl.betTypeEvent;

import by.bsuir.bookmaker.beans.BetType;
import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.beans.Event;
import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IBetTypeDAO;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.IEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetAllBetTypeEventCommand implements ICommand {

    private static final Logger log = Logger.getLogger(GetAllBetTypeEventCommand.class);
    private static final IBetTypeEventDAO betTypeEventDAO = DAOFactory.getInstance().getBetTypeEventDAO();
    private static final IEventDAO eventDAO = DAOFactory.getInstance().getEventDAO();
    private static final IBetTypeDAO betTypeDAO = DAOFactory.getInstance().getBetTypeDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        try {
            List<BetTypeEvent> betTypeEvents = betTypeEventDAO.getAllBetTypeEvents();
            req.setAttribute("betTypeEvents", betTypeEvents);
            List<BetType> currBetTypes = new ArrayList<>();
            List<Event> currEvents = new ArrayList<>();
            for (BetTypeEvent betTypeEvent : betTypeEvents) {
                currEvents.add(eventDAO.getEvent(betTypeEvent.getEventId()));
                currBetTypes.add(betTypeDAO.getBetType(betTypeEvent.getBetTypeId()));
            }
            req.setAttribute("currEvents", currEvents);
            req.setAttribute("currBetTypes", currBetTypes);
            req.setAttribute("allEvents", eventDAO.getAllEvents());
            req.setAttribute("allBetTypes", betTypeDAO.getAllBetTypes());
        } catch (DAOException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
