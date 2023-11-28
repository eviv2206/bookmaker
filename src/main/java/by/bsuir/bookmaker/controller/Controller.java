package by.bsuir.bookmaker.controller;

import by.bsuir.bookmaker.service.CommandException;
import by.bsuir.bookmaker.service.CommandHelper;
import by.bsuir.bookmaker.service.CommandName;
import by.bsuir.bookmaker.service.ICommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author eviv2206
 * @version 1.0
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String JSP_PATH = "/WEB-INF/jsp/";
    public Controller() {
        super();
    }

    /**
     * Processing get request
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returnPage = request.getParameter("page");
        if (returnPage != null) {
            request.getRequestDispatcher(JSP_PATH + returnPage).forward(request, response);
        } else {
            processCommand(request, response);
        }
    }

    /**
     * Processing post request
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    /**
     * Print error message
     * @param response
     * @throws IOException
     */
    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws
            IOException{
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }

    /**
     * Process command
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName =
                request.getParameter(RequestParameterName.COMMAND_NAME);
        ICommand command = CommandHelper
                .getInstance()
                .getCommand(commandName);
        String page;
        try {
            page = command.execute(request);
        } catch (CommandException | Exception e) {
            page = JspPageName.ERROR_PAGE;
        }
        if (commandName.equals(CommandName.CHANGE_LOCALE_COMMAND.toString())) {
            response.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                errorMessageDirectlyFromResponse(response);
            }
        }
    }



}
