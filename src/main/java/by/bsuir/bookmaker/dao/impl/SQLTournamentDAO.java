package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.Tournament;
import by.bsuir.bookmaker.dao.ITournamentDAO;
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
public class SQLTournamentDAO implements ITournamentDAO {
    private static final Logger log = Logger.getLogger(SQLTournamentDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of adding tournament
     * @param name
     * @param description
     * @param sportTypeID
     * @throws DAOException
     */
    @Override
    public void addTournament(String name, String description, int sportTypeID) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tournament (t_name, t_description, t_s_t_id) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, String.valueOf(sportTypeID));
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
     * Method of getting tournament
     * @param tournamentID
     * @return Tournament
     * @throws DAOException
     */
    @Override
    public Tournament getTournament(int tournamentID) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tournament WHERE t_id=?");
            statement.setString(1, String.valueOf(tournamentID));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return extractTournamentFromResultSet(res);
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
     * Method of deleting tournament
     * @param tournamentID
     * @throws DAOException
     */
    @Override
    public void deleteTournament(int tournamentID) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tournament WHERE t_id=?");
            statement.setString(1, String.valueOf(tournamentID));
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
     * Method of updating tournament
     * @param name
     * @param description
     * @param sportTypeID
     * @param tournamentID
     * @throws DAOException
     */
    @Override
    public void updateTournament(String name, String description, int sportTypeID, int tournamentID) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE tournament SET t_name=?, t_description=?, t_s_t_id=? WHERE t_id=?");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, String.valueOf(sportTypeID));
            statement.setString(4, String.valueOf(tournamentID));
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
     * Method of getting all tournaments
     * @return ArrayList<Tournament>
     * @throws DAOException
     */
    @Override
    public ArrayList<Tournament> getAllTournaments() throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tournament ORDER BY t_name DESC");
            ResultSet res = statement.executeQuery();
            ArrayList<Tournament> tournaments = new ArrayList<>();
            while (res.next()){
                tournaments.add(extractTournamentFromResultSet(res));
            }
            return tournaments;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    private Tournament extractTournamentFromResultSet(ResultSet res) throws SQLException {
        final int id = res.getInt("t_id");
        final String name = res.getString("t_name");
        final String description = res.getString("t_description");
        final int sportTypeID = res.getInt("t_s_t_id");
        return new Tournament(id, name, description, sportTypeID);
    }
}
