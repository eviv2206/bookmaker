package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.SportType;
import by.bsuir.bookmaker.dao.ISportTypeDAO;
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
public class SQLSportTypeDAO implements ISportTypeDAO {
    private static final Logger log = Logger.getLogger(SQLSportTypeDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of adding sport type
     * @param name
     * @param description
     * @throws DAOException
     */
    @Override
    public void addSportType(String name, String description) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sport_type (s_t_name, s_t_description) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, description);
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
     * Method of getting sport type
     * @param sportID
     * @return SportType
     * @throws DAOException
     */
    @Override
    public SportType getSportType(int sportID) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sport_type WHERE s_t_id=?");
            statement.setString(1, String.valueOf(sportID));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return extractSportTypeFromResultSet(res);
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
     * Method of deleting sport type
     * @param sportID
     * @throws DAOException
     */
    @Override
    public void deleteSportType(int sportID) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM sport_type WHERE s_t_id=?");
            statement.setString(1, String.valueOf(sportID));
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
     * Method of getting all sport types
     * @throws DAOException
     */
    @Override
    public ArrayList<SportType> getAllSportTypes() throws DAOException {
        ResultSet res = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from sport_type ORDER BY s_t_name DESC");
        ) {
            res = statement.executeQuery();
            ArrayList<SportType> sportTypes = new ArrayList<>();
            while (res.next()){
                sportTypes.add(extractSportTypeFromResultSet(res));
            }
            return sportTypes;
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
     * Method of updating sport type
     * @param name
     * @param description
     * @param sportID
     * @throws DAOException
     */
    @Override
    public void updateSportType(String name, String description, int sportID) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE sport_type SET s_t_name=?, s_t_description=? WHERE s_t_id=?");
        ){
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, String.valueOf(sportID));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No rows affected");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    private SportType extractSportTypeFromResultSet(ResultSet res) throws SQLException {
        final int id = res.getInt("s_t_id");
        final String name = res.getString("s_t_name");
        final String description = res.getString("s_t_description");
        return new SportType(id, name, description);
    }
}
