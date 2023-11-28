package by.bsuir.bookmaker.service.impl.tournament;

import by.bsuir.bookmaker.beans.SportType;
import by.bsuir.bookmaker.beans.Tournament;
import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ISportTypeDAO;
import by.bsuir.bookmaker.dao.ITournamentDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import by.bsuir.bookmaker.service.impl.sportType.GetAllSportTypeCommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class GetAllTournamentCommand implements ICommand {
    private final ITournamentDAO tournamentDAO = DAOFactory.getInstance().getTournamentDAO();
    private final ISportTypeDAO sportTypeDAO = DAOFactory.getInstance().getSportTypeDAO();

    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        try {
            List<Tournament> tournaments = tournamentDAO.getAllTournaments();
            req.setAttribute("tournaments", tournaments);
            List<SportType> sportTypes = new ArrayList<>();
            for (Tournament tournament : tournaments) {
                sportTypes.add(sportTypeDAO.getSportType(tournament.getSportTypeId()));
            }
            req.setAttribute("currSportTypes", sportTypes);
            if (user.isPrivileges()) {
                new GetAllSportTypeCommand().execute(req);
            }
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
