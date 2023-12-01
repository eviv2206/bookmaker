package by.bsuir.bookmaker.service.impl.userBet;

import by.bsuir.bookmaker.beans.*;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.*;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetAllUserBetCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetAllUsersBetCommand.class);
    private static final IUserBetDAO userBetDAO = DAOFactory.getInstance().getUserBetDAO();
    private static final IEventDAO eventDAO = DAOFactory.getInstance().getEventDAO();
    private static final IAuthDAO authDAO = DAOFactory.getInstance().getAuthDAO();
    private static final IBetTypeDAO betTypeDAO = DAOFactory.getInstance().getBetTypeDAO();
    private static final IBetTypeEventDAO betTypeEventDAO = DAOFactory.getInstance().getBetTypeEventDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        try {
            List<UserBet> userBets = userBetDAO.getAllUserBetsByUserId(user.getId());
            req.setAttribute("userBets", userBets);
            List<BetTypeEvent> betTypeEvents = new ArrayList<>();
            for (UserBet userBet : userBets) {
                betTypeEvents.add(betTypeEventDAO.getById(userBet.getBetTypeEventID()));
            }
            req.setAttribute("betTypeEvents", betTypeEvents);
            List<BetType> betTypes = new ArrayList<>();
            for (BetTypeEvent betTypeEvent : betTypeEvents) {
                betTypes.add(betTypeDAO.getBetType(betTypeEvent.getBetTypeId()));
            }
            req.setAttribute("betTypes", betTypes);
            List<Event> events = new ArrayList<>();
            for (BetTypeEvent betTypeEvent : betTypeEvents) {
                events.add(eventDAO.getEvent(betTypeEvent.getEventId()));
            }
            req.setAttribute("events", events);
            List<BetTypeEvent> allBetTypeEvents = betTypeEventDAO.getAllBetTypeEvents();
            List<Event> allEvents = new ArrayList<>();
            List<BetType> allBetTypes = new ArrayList<>();
            for (BetTypeEvent betTypeEvent : allBetTypeEvents) {
                allEvents.add(eventDAO.getEvent(betTypeEvent.getEventId()));
                allBetTypes.add(betTypeDAO.getBetType(betTypeEvent.getBetTypeId()));
            }
            req.setAttribute("allBetTypeEvents", allBetTypeEvents);
            req.setAttribute("allEvents", allEvents);
            req.setAttribute("allBetTypes", allBetTypes);
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.BOOKMAKER_PAGE;
    }
}
