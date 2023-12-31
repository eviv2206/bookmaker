package by.bsuir.bookmaker.service.impl.tournament;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ITournamentDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class DeleteTournamentCommand implements ICommand {
    private static final Logger log = Logger.getLogger(DeleteTournamentCommand.class);
    public static final ITournamentDAO tournamentDAO = DAOFactory.getInstance().getTournamentDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null || !user.isPrivileges()) {
            return JspPageName.LOGIN_PAGE;
        }
        String tournamentID = req.getParameter("tournamentID");
        try {
            tournamentDAO.deleteTournament(Integer.parseInt(tournamentID));
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
