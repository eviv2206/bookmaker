package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.Participant;
import by.bsuir.bookmaker.dao.IParticipantDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author eviv2206
 * @version 1.0
 */
public class SQLParticipantDAO implements IParticipantDAO {
    private static final Logger log = Logger.getLogger(SQLParticipantDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of adding participant
     * @param name
     * @throws DAOException
     */
    @Override
    public void addParticipant(String name) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO participant (p_name) VALUES (?)");
            statement.setString(1, name);
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
     * Method of getting participant
     * @param id
     * @return Participant
     * @throws DAOException
     */
    @Override
    public Participant getParticipant(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM participant WHERE p_id=?");
            statement.setString(1, String.valueOf(id));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return extractParticipantFromResultSet(res);
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

    /**
     * Method of deleting participant
     * @param id
     * @throws DAOException
     */
    @Override
    public void deleteParticipant(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM participant WHERE p_id=?");
            statement.setString(1, String.valueOf(id));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No rows affected");
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
     * Method of updating participant
     * @param name
     * @param id
     * @throws DAOException
     */
    @Override
    public void updateParticipant(String name, int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE participant SET p_name=? WHERE p_id=?");
            statement.setString(1, name);
            statement.setString(2, String.valueOf(id));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No rows affected");
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
     * Method of getting all participants
     * @return ArrayList<Participant>
     * @throws DAOException
     */
    @Override
    public ArrayList<Participant> getAllParticipants() throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM participant ORDER BY p_name DESC");
            ResultSet res = statement.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (res.next()) {
                participants.add(extractParticipantFromResultSet(res));
            }
            return participants;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    private Participant extractParticipantFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("p_id");
        String name = resultSet.getString("p_name");
        return new Participant(id, name);
    }
}
