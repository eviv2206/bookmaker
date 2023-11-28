package by.bsuir.bookmaker.service.impl.tournament;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ITournamentDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class EditTournamentCommand implements ICommand {
    private static final Logger log = Logger.getLogger(EditTournamentCommand.class);
    public static final ITournamentDAO tournamentDAO = DAOFactory.getInstance().getTournamentDAO();

    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !user.isPrivileges()) {
            return JspPageName.LOGIN_PAGE;
        }
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String sportTypeID = req.getParameter("sportTypeID");
        try {
            tournamentDAO.updateTournament(name, description, Integer.parseInt(sportTypeID), Integer.parseInt(req.getParameter("tournamentID")));
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
