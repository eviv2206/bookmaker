package by.bsuir.bookmaker.service.impl.userBet;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.IUserBetDAO;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddUserBetCommand implements ICommand {
    private static final Logger logger = Logger.getLogger(AddUserBetCommand.class);
    private static final IUserBetDAO userBetDAO = DAOFactory.getInstance().getUserBetDAO();
    private static final IBetTypeEventDAO betTypeEventDAO = DAOFactory.getInstance().getBetTypeEventDAO();
    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("curruser");
        if (user == null) {
            return JspPageName.LOGIN_PAGE;
        }
        int userId = user.getId();
        int betAmount = Integer.parseInt(req.getParameter("betAmount"));
        int betTypeEventId = Integer.parseInt(req.getParameter("betTypeEventId"));
        String score = req.getParameter("score");
        try {
            userBetDAO.addUserBet(LocalDateTime.now(), betAmount, 0, score, userId, betTypeEventId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.BOOKMAKER_PAGE;
    }
}
