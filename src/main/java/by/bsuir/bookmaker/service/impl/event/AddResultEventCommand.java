package by.bsuir.bookmaker.service.impl.event;

import by.bsuir.bookmaker.beans.BetType;
import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.beans.Event;
import by.bsuir.bookmaker.beans.UserBet;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IBetTypeDAO;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.IEventDAO;
import by.bsuir.bookmaker.dao.IUserBetDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.List;

public class AddResultEventCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(AddResultEventCommand.class);
    private static final IEventDAO eventDAO = DAOFactory.getInstance().getEventDAO();
    private static final IUserBetDAO userBetDAO = DAOFactory.getInstance().getUserBetDAO();
    private static final IBetTypeEventDAO betTypeEventDAO = DAOFactory.getInstance().getBetTypeEventDAO();

    @Override
    public String execute(HttpServletRequest req) {
        int eventId = Integer.parseInt(req.getParameter("eventID"));
        String result = req.getParameter("result");
        int winner = parseAndCompare(result);
        int winnerId = Integer.parseInt(req.getParameter("winnerID"));
        try {
            Event currentEvent = eventDAO.getEvent(eventId);
            eventDAO.updateEvent(currentEvent.getName(), currentEvent.getDescription(), currentEvent.getStart_time(), result, currentEvent.getTournamentID(), currentEvent.getParticipantsIDs(), winnerId, eventId);
            List<UserBet> userBetsForEvent = userBetDAO.getAllUserBetsByEventId(eventId);
            List<BetTypeEvent> betTypeEventsForEvent = betTypeEventDAO.getAllBetTypeEventsByEventId(eventId);
            for (UserBet userBet : userBetsForEvent) {
                setBetResult(userBet, betTypeEventsForEvent, winner, result);
            }
        } catch (DAOException e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }

    private void setBetResult(UserBet userBet, List<BetTypeEvent> betTypeEventsForEvent, int winner, String result) throws DAOException {
        if (userBet.getScore() != null && !userBet.getScore().isEmpty()) {
            BetTypeEvent betTypeEvent = betTypeEventDAO.getById(userBet.getBetTypeEventID());
            double winningAmount = betTypeEvent.getCoefficient() * userBet.getBetAmount();
            if (!userBet.getScore().equals(result)) {
                winningAmount = -userBet.getBetAmount();
            }
            userBetDAO.updateUserBet(userBet.getDate(), userBet.getBetAmount(), winningAmount, userBet.getScore(), userBet.getUserID(), userBet.getBetTypeEventID(), userBet.getId());
        } else {
            BetTypeEvent currBetTypeEvent = null;
            for (BetTypeEvent betTypeEvent : betTypeEventsForEvent) {
                if (betTypeEvent.getBetTypeEventId() == userBet.getBetTypeEventID()) {
                    currBetTypeEvent = betTypeEvent;
                    break;
                }
            }
            double winningAmount = currBetTypeEvent.getCoefficient() * userBet.getBetAmount();
            if (currBetTypeEvent.getBetTypeId() != winner) {
                winningAmount = -userBet.getBetAmount();
            }
            userBetDAO.updateUserBet(userBet.getDate(), userBet.getBetAmount(), winningAmount, "", userBet.getUserID(), userBet.getBetTypeEventID(), userBet.getId());
        }


    }

    private int parseAndCompare(String inputStr) {
        String[] parts = inputStr.split("-");

        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);

        if (num1 > num2) {
            return 1;
        } else if (num2 > num1) {
            return 2;
        } else {
            return 3;
        }
    }
}
