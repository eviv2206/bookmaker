package by.bsuir.bookmaker.service.impl.event;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.IEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AddEventCommand implements ICommand {

    private static final Logger log = Logger.getLogger(AddEventCommand.class);
    private static final IEventDAO eventDAO = DAOFactory.getInstance().getEventDAO();

    @Override
    public String execute(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !user.isPrivileges()) {
            return JspPageName.LOGIN_PAGE;
        }
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Date date = transformToDateTime(req.getParameter("date"));
        String result = req.getParameter("result");
        int tournamentID = Integer.parseInt(req.getParameter("tournamentID"));
        int winnerID = Integer.parseInt(req.getParameter("winnerID"));
        Integer[] participants = convertStringArrayToIntegerArray(req.getParameterValues("participants"));
        try {
            eventDAO.addEvent(name, description, date, result, tournamentID, Arrays.asList(participants), winnerID);
        } catch (DAOException e) {
            log.error(e.getMessage());
            req.setAttribute("error", e.getMessage());
            return JspPageName.ERROR_PAGE;
        }
        return JspPageName.ADMIN_PAGE;
    }

    private Date transformToDateTime(String str) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateTimeFormat.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private Integer[] convertStringArrayToIntegerArray(String[] stringArray) {
        if (stringArray != null) {
            Integer[] integerArray = new Integer[stringArray.length];

            for (int i = 0; i < stringArray.length; i++) {
                try {
                    integerArray[i] = Integer.parseInt(stringArray[i]);
                } catch (NumberFormatException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }

            return integerArray;
        } else {
            return null;
        }
    }

}
