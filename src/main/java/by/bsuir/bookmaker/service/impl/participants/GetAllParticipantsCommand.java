package by.bsuir.bookmaker.service.impl.participants;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IParticipantDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class GetAllParticipantsCommand implements ICommand {
    private static final Logger log = Logger.getLogger(GetAllParticipantsCommand.class);
    private static final IParticipantDAO participantsDAO = DAOFactory.getInstance().getParticipantDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        try {
            req.setAttribute("participants", participantsDAO.getAllParticipants());
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }
}
