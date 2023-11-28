package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.UserBet;
import by.bsuir.bookmaker.dao.IUserBetDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SQLUserBetDAO implements IUserBetDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    @Override
    public void addUserBet(Date date, int betAmount, double winAmount, int userId, int betTypeEventId) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user_bet (u_b_date, u_b_bet_amount, u_b_winning_amount, u_b_b_t_e_id, u_b_u_id) VALUES(?,?,?,?,?)");
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, betAmount);
            statement.setDouble(3, winAmount);
            statement.setInt(4, betTypeEventId);
            statement.setInt(5, userId);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public UserBet getUserBetById(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_bet WHERE u_b_id=?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return extractUserBetFromResultSet(set);
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

    @Override
    public List<UserBet> getAllUsersBets() throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_bet");
            ResultSet set = statement.executeQuery();
            List<UserBet> userBets = new java.util.ArrayList<>();
            while (set.next()) {
                userBets.add(extractUserBetFromResultSet(set));
            }
            return userBets;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<UserBet> getAllUserBetsByUserId(int userId) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_bet WHERE u_b_u_id=?");
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            List<UserBet> userBets = new java.util.ArrayList<>();
            while (set.next()) {
                userBets.add(extractUserBetFromResultSet(set));
            }
            return userBets;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private UserBet extractUserBetFromResultSet(ResultSet set) throws SQLException {
        final int id = set.getInt("u_b_id");
        final Date date = set.getDate("u_b_date");
        final int betAmount = set.getInt("u_b_bet_amount");
        final double winAmount = set.getDouble("u_b_winning_amount");
        final int betTypeEventId = set.getInt("u_b_b_t_e_id");
        final int userId = set.getInt("u_b_u_id");
        return new UserBet(id, date, betAmount, winAmount, betTypeEventId, userId);
    }
}
