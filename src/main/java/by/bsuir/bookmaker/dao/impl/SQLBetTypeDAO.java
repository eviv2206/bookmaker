package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.BetType;
import by.bsuir.bookmaker.dao.IBetTypeDAO;
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
 * Implementation of IBetTypeDAO
 * @author eviv2206
 * @version 1.0
 */
public class SQLBetTypeDAO implements IBetTypeDAO {
    private static final Logger log = Logger.getLogger(SQLBetTypeDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of getting bet type by id
     * @param betTypeID
     * @return BetType
     * @throws DAOException
     */
    @Override
    public BetType getBetType(int betTypeID) throws DAOException {
        ResultSet res = null;
        try(Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM bet_type WHERE b_t_id=?")) {
            statement.setString(1, String.valueOf(betTypeID));
            res = statement.executeQuery();
            if (res.next()) {
                return extractBetTypeFromResultSet(res);
            } else {
                return null;
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
     * Method of getting all bet types
     * @return list of betTypes
     * @throws DAOException
     */
    @Override
    public List<BetType> getAllBetTypes() throws DAOException {
        ResultSet res = null;
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement("select * from bet_type ORDER BY b_t_id DESC")) {
            res = statement.executeQuery();
            ArrayList<BetType> betTypes = new ArrayList<>();
            while (res.next()){
                betTypes.add(extractBetTypeFromResultSet(res));
            }
            return betTypes;
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
     * Method of extracting bet type from result set
     * @param res
     * @return BetType
     * @throws SQLException
     */
    private BetType extractBetTypeFromResultSet(ResultSet res) throws SQLException {
        final int id = res.getInt("b_t_id");
        final String name = res.getString("b_t_name");
        final String description = res.getString("b_t_description");
        return new BetType(id, name, description);
    }
}
