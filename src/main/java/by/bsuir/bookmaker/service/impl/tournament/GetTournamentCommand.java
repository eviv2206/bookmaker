package by.bsuir.bookmaker.service.impl.tournament;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ITournamentDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import by.bsuir.bookmaker.service.impl.sportType.GetSportTypeCommand;
import jakarta.servlet.http.HttpServletRequest;

public class GetTournamentCommand implements ICommand {

    private final ITournamentDAO tournamentDAO = DAOFactory.getInstance().getTournamentDAO();

    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        int tournamentID = Integer.parseInt(req.getParameter("tournamentID"));
        try {
            req.setAttribute("tournament", tournamentDAO.getTournament(tournamentID));
            return new GetSportTypeCommand().execute(req);
        } catch (DAOException e){
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
    }
}
