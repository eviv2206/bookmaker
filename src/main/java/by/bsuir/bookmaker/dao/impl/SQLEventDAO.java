package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.Event;
import by.bsuir.bookmaker.dao.IEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;
import com.mysql.cj.exceptions.ConnectionIsClosedException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Implementation of SQLEventDAO
 * @author eviv2206
 * @version 1.0
 */
public class SQLEventDAO implements IEventDAO {
    private static final Logger log = Logger.getLogger(SQLEventDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of adding event
     * @param name
     * @param description
     * @param date
     * @param result
     * @param tournamentID
     * @param participants
     * @throws DAOException
     */
    @Override
    public void addEvent(String name, String description, LocalDateTime date, String result, int tournamentID, List<Integer> participants) throws DAOException {
        ResultSet res = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO event(e_name, e_description, e_start_time, e_result, e_t_id, e_p_id_winner) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setObject(3, date);
            statement.setString(4, result);
            statement.setString(5, String.valueOf(tournamentID));
            statement.setString(6, null);
            statement.executeUpdate();
            res = statement.getGeneratedKeys();
            if (res.next()){
                int generatedEventId = res.getInt(1);
                for (Integer participantId : participants) {
                    PreparedStatement participantStatement = connection.prepareStatement("INSERT INTO event_m2m_participant (e_p_e_id, e_p_p_id) VALUES (?, ?)");
                    participantStatement.setInt(1, generatedEventId);
                    participantStatement.setInt(2, participantId);
                    participantStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * Method of getting event by id
     * @param id
     * @return Event
     * @throws DAOException
     */
    @Override
    public Event getEvent(int id) throws DAOException {
        ResultSet set = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM event\n" +
                     "JOIN event_m2m_participant ON event.e_id = event_m2m_participant.e_p_e_id\n" +
                     "WHERE event.e_id = ?;\n");
             ) {
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                return extractEventFromResultSet(connection, set);
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (set != null) {
                try {
                    set.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * Method of updating event
     * @param name
     * @param description
     * @param date
     * @param result
     * @param tournamentID
     * @param participants
     * @param winnerID
     * @param id
     * @throws DAOException
     */
    @Override
    public void updateEvent(String name, String description, LocalDateTime date, String result, int tournamentID, List<Integer> participants, int winnerID, int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE event SET e_name=?, e_description=?, e_start_time=?, e_result=?, e_t_id=?, e_p_id_winner=? WHERE e_id=?", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setObject(3, date);
            statement.setString(4, result);
            statement.setString(5, String.valueOf(tournamentID));
            statement.setString(6, String.valueOf(winnerID));
            statement.setInt(7, id);
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            if (res.next()){
                for (Integer participantId : participants) {
                    PreparedStatement participantStatement = connection.prepareStatement("INSERT INTO event_m2m_participant (e_p_e_id, e_p_p_id) VALUES (?, ?)");
                    participantStatement.setString(1, String.valueOf(res.getString("e_id")));
                    participantStatement.setInt(2, participantId);
                    participantStatement.executeUpdate();
                }
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

    /**
     * Method of deleting event
     * @param id
     * @throws DAOException
     */
    @Override
    public void deleteEvent(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM event WHERE e_id=?");
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
     * Method of getting all events
     * @return array list of events
     * @throws DAOException
     */
    @Override
    public ArrayList<Event> getAllEvents() throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM event");
            ResultSet res = statement.executeQuery();
            ArrayList<Event> events = new ArrayList<>();
            while (res.next()){
                events.add(extractEventFromResultSet(connection, res));
            }
            return events;
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
     * Method of extracting event from result set
     * @param set
     * @return Event
     * @throws SQLException
     */
    private Event extractEventFromResultSet(Connection con, ResultSet set) throws SQLException {
        PreparedStatement statement = con.prepareStatement("SELECT * FROM event_m2m_participant WHERE e_p_e_id=?");
        final int id = set.getInt("e_id");
        final String name = set.getString("e_name");
        final String description = set.getString("e_description");
        final LocalDateTime start_date = set.getObject("e_start_time", LocalDateTime.class);
        final String result = set.getString("e_result");
        final int tournamentID = set.getInt("e_t_id");
        final int winnerID = set.getInt("e_p_id_winner");
        final List<Integer> participantIDs = new ArrayList<>();
        statement.setInt(1, id);
        ResultSet resM2M = statement.executeQuery();
        while (resM2M.next()) {
            participantIDs.add(resM2M.getInt("e_p_p_id"));
        }
        return new Event(id, name, description, start_date, result, tournamentID, winnerID, participantIDs);
    }
}
