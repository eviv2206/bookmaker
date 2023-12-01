
import by.bsuir.bookmaker.beans.SportType;
import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.controller.JspPageName;
import by.bsuir.bookmaker.dao.ISportTypeDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.service.impl.sportType.GetSportTypeCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetSportTypeCommandTest {

    private HttpServletRequest request;
    private HttpSession session;
    private GetSportTypeCommand getSportTypeCommand;

    @BeforeEach
    public void setUp() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        getSportTypeCommand = new GetSportTypeCommand();
    }

    @Test
    public void testGetSportTypeCommand() throws DAOException {
        User user = new User(1, "user", "12345", "password", false);
        when(session.getAttribute("curruser")).thenReturn(user);

        String sportTypeID = "1";
        when(request.getParameter("sportTypeID")).thenReturn(sportTypeID);

        ISportTypeDAO sportTypeDAO = mock(ISportTypeDAO.class);

        when(sportTypeDAO.getSportType(anyInt())).thenReturn(new SportType(0, "name", "desc"));

        String result = getSportTypeCommand.execute(request);


        assertEquals(JspPageName.ADMIN_PAGE, result);
    }
}
