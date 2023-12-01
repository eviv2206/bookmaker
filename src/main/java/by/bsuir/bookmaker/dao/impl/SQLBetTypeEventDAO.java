package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of SQLBetTypeEventDAO
 * @author eviv2206
 * @version 1.0
 */
public class SQLBetTypeEventDAO implements IBetTypeEventDAO {
    private static final Logger log = Logger.getLogger(SQLBetTypeEventDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void add(int betTypeEventEventId, int betTypeEventBetTypeId, double betTypeEventCoefficient) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bet_type_m2m_event(b_t_e_b_t_id, b_t_e_e_id, b_t_e_coefficient) VALUES(?,?,?)");
            statement.setInt(1, betTypeEventBetTypeId);
            statement.setInt(2, betTypeEventEventId);
            statement.setDouble(3, betTypeEventCoefficient);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM bet_type_m2m_event WHERE b_t_e_id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Method of getting betTypeEvent by id
     * @param id
     * @return BetTypeEvent
     * @throws DAOException
     */
    @Override
    public BetTypeEvent getById(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bet_type_m2m_event WHERE b_t_e_id=?");
            statement.setString(1, String.valueOf(id));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return extractBetTypeEventFromResultSet(res);
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<BetTypeEvent> getAllBetTypeEventsByEventId(int eventId) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bet_type_m2m_event WHERE b_t_e_e_id=?");
            statement.setString(1, String.valueOf(eventId));
            ResultSet res = statement.executeQuery();
            List<BetTypeEvent> betTypeEvents = new ArrayList<>();
            while (res.next()) {
                betTypeEvents.add(extractBetTypeEventFromResultSet(res));
            }
            return betTypeEvents;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Method of getting all betTypeEvents
     * @return List<BetTypeEvent>
     * @throws DAOException
     */
    @Override
    public List<BetTypeEvent> getAllBetTypeEvents() throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bet_type_m2m_event");
            ResultSet res = statement.executeQuery();
            List<BetTypeEvent> betTypeEvents = new ArrayList<>();
            while (res.next()) {
                betTypeEvents.add(extractBetTypeEventFromResultSet(res));
            }
            return betTypeEvents;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }


    /**
     * Method of getting BetTypeEvent from result set
     * @param res
     * @return BetTypeEvent
     * @throws SQLException
     */
    private BetTypeEvent extractBetTypeEventFromResultSet(ResultSet res) throws SQLException {
        final int betTypeEventId = res.getInt("b_t_e_id");
        final int betTypeId = res.getInt("b_t_e_b_t_id");
        final int eventId = res.getInt("b_t_e_e_id");
        final double coefficient = res.getDouble("b_t_e_coefficient");
        return new BetTypeEvent(betTypeEventId, betTypeId, eventId, coefficient);
    }
}
