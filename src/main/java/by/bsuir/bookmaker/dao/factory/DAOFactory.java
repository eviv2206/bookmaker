package by.bsuir.bookmaker.dao.factory;

import by.bsuir.bookmaker.dao.*;
import by.bsuir.bookmaker.dao.impl.*;

/**
 * The type Dao factory.
 * @author eviv2206
 * @version 1.0
 */
public class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();
    private static final IAuthDAO AUTH_DAO = new SQLAuthDAO();
    private static final ISportTypeDAO SPORT_TYPE_DAO = new SQLSportTypeDAO();
    private static final ITournamentDAO TOURNAMENT_DAO = new SQLTournamentDAO();
    private static final IParticipantDAO PARTICIPANT_DAO = new SQLParticipantDAO();
    private static final IEventDAO EVENT_DAO = new SQLEventDAO();
    private static final IBetTypeDAO BET_TYPE_DAO = new SQLBetTypeDAO();
    private static final IUserBetDAO USER_BET_DAO = new SQLUserBetDAO();
    private static final IBetTypeEventDAO BET_TYPE_EVENT_DAO = new SQLBetTypeEventDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    public IAuthDAO getAuthDAO() {
        return AUTH_DAO;
    }

    public ISportTypeDAO getSportTypeDAO() {
        return SPORT_TYPE_DAO;
    }

    public ITournamentDAO getTournamentDAO() {
        return TOURNAMENT_DAO;
    }

    public IParticipantDAO getParticipantDAO() {
        return PARTICIPANT_DAO;
    }

    public IEventDAO getEventDAO() {
        return EVENT_DAO;
    }

    public IBetTypeDAO getBetTypeDAO() {
        return BET_TYPE_DAO;
    }

    public IUserBetDAO getUserBetDAO() {
        return USER_BET_DAO;
    }

    public IBetTypeEventDAO getBetTypeEventDAO() {
        return BET_TYPE_EVENT_DAO;
    }
}
