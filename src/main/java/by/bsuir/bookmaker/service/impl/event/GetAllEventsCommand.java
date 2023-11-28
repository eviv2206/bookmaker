package by.bsuir.bookmaker.service.impl.event;

import by.bsuir.bookmaker.beans.Event;
import by.bsuir.bookmaker.beans.Participant;
import by.bsuir.bookmaker.beans.Tournament;
import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IEventDAO;
import by.bsuir.bookmaker.dao.IParticipantDAO;
import by.bsuir.bookmaker.dao.ITournamentDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetAllEventsCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetAllEventsCommand.class);
    private static final IEventDAO eventDAO = DAOFactory.getInstance().getEventDAO();
    private static final IParticipantDAO participantDAO = DAOFactory.getInstance().getParticipantDAO();
    private static final ITournamentDAO tournamentDAO = DAOFactory.getInstance().getTournamentDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        try {
            List<Event> events = eventDAO.getAllEvents();
            req.setAttribute("events", events);
            List<Tournament> tournaments = new ArrayList<>();
            List<List<Participant>> participants = new ArrayList<>();
            List<Participant> winners = new ArrayList<>();
            for (Event event : events) {
                List<Participant> tempParticipant = new ArrayList<>();
                for (int id : event.getParticipantsIDs()) {
                    tempParticipant.add(participantDAO.getParticipant(id));
                }
                participants.add(tempParticipant);
                tournaments.add(tournamentDAO.getTournament(event.getTournamentID()));
                winners.add(participantDAO.getParticipant(event.getWinnerID()));
            }
            req.setAttribute("allParticipants", participantDAO.getAllParticipants());
            req.setAttribute("participants", participants);
            req.setAttribute("tournaments", tournaments);
            req.setAttribute("winners", winners);
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
