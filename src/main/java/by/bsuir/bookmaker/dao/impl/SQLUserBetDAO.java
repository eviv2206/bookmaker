package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.beans.UserBet;
import by.bsuir.bookmaker.dao.IBetTypeEventDAO;
import by.bsuir.bookmaker.dao.IUserBetDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.factory.DAOFactory;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eviv2206
 * @version 1.0
 */
public class SQLUserBetDAO implements IUserBetDAO {
    private static final Logger log = Logger.getLogger(SQLUserBetDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of adding user bet
     * @param date
     * @param betAmount
     * @param winAmount
     * @param score
     * @param userId
     * @param betTypeEventId
     * @throws DAOException
     */
    @Override
    public void addUserBet(LocalDateTime date, int betAmount, double winAmount, String score, int userId, int betTypeEventId) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user_bet (u_b_date, u_b_bet_amount, u_b_winning_amount, u_b_score, u_b_b_t_e_id, u_b_u_id) VALUES(?,?,?,?,?,?)");
            statement.setObject(1, date);
            statement.setInt(2, betAmount);
            statement.setDouble(3, winAmount);
            statement.setString(4, score);
            statement.setInt(5, betTypeEventId);
            statement.setInt(6, userId);
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
     * Method of updating user bet
     * @param date
     * @param betAmount
     * @param winAmount
     * @param result
     * @param userId
     * @param betTypeEventId
     * @param id
     * @throws DAOException
     */
    @Override
    public void updateUserBet(LocalDateTime date, int betAmount, double winAmount, String result, int userId, int betTypeEventId, int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE user_bet SET u_b_date=?, u_b_bet_amount=?, u_b_winning_amount=?, u_b_score=?, u_b_b_t_e_id=?, u_b_u_id=? WHERE u_b_id=?");
            statement.setObject(1, date);
            statement.setInt(2, betAmount);
            statement.setDouble(3, winAmount);
            statement.setString(4, result);
            statement.setInt(5, betTypeEventId);
            statement.setInt(6, userId);
            statement.setInt(7, id);
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
     * Method of getting user bet by id
     * @param id
     * @return UserBet
     * @throws DAOException
     */
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
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Method of getting all user bets
     * @throws DAOException
     */
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
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Method of getting all user bets by user id
     * @param userId
     * @throws DAOException
     */
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
            log.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Method of getting all user bets by event id
     * @param eventId
     * @throws DAOException
     */
    @Override
    public List<UserBet> getAllUserBetsByEventId(int eventId) throws DAOException {
        IBetTypeEventDAO betTypeEventDAO = DAOFactory.getInstance().getBetTypeEventDAO();
        ResultSet set = null;
        try (Connection connection = connectionPool.getConnection();
        ) {
            List<BetTypeEvent> betTypeEvents = betTypeEventDAO.getAllBetTypeEventsByEventId(eventId);
            List<Integer> betTypeEventsIDS = new ArrayList<>();
            for (BetTypeEvent betTypeEvent : betTypeEvents) {
                betTypeEventsIDS.add(betTypeEvent.getBetTypeEventId());
            }
            String betTypeEventsIDSString = betTypeEventsIDS.stream().map(String::valueOf).collect(Collectors.joining(","));
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_bet WHERE u_b_b_t_e_id IN (" + betTypeEventsIDSString + ")");
            set = statement.executeQuery();
            List<UserBet> userBets = new java.util.ArrayList<>();
            while (set.next()) {
                userBets.add(extractUserBetFromResultSet(set));
            }
            return userBets;
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

    private UserBet extractUserBetFromResultSet(ResultSet set) throws SQLException {
        final int id = set.getInt("u_b_id");
        final LocalDateTime date = set.getObject("u_b_date", LocalDateTime.class);
        final int betAmount = set.getInt("u_b_bet_amount");
        final String score = set.getString("u_b_score");
        final double winAmount = set.getDouble("u_b_winning_amount");
        final int betTypeEventId = set.getInt("u_b_b_t_e_id");
        final int userId = set.getInt("u_b_u_id");
        return new UserBet(id, date, betAmount, winAmount, score, betTypeEventId, userId);
    }
}
