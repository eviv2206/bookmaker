package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of SQLBetTypeEventDAO
 * @author eviv2206
 * @version 1.0
 */
public class SQLBetTypeEventDAO implements IBetTypeEventDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

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
        final int betTypeId = res.getInt("b_t_id");
        final int eventId = res.getInt("e_id");
        final double coefficient = res.getDouble("b_t_e_coefficient");
        return new BetTypeEvent(betTypeEventId, betTypeId, eventId, coefficient);
    }
}
