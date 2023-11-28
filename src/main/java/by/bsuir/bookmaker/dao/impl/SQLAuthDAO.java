package by.bsuir.bookmaker.dao.impl;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.dao.IAuthDAO;
import by.bsuir.bookmaker.dao.exception.DAOException;
import by.bsuir.bookmaker.dao.pool.impl.ConnectionPool;

import java.sql.*;

/**
 * Implementation of IAuthDAO
 * @author eviv2206
 * @version 1.0
 */
public class SQLAuthDAO implements IAuthDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Method of getting user
     * @param login
     * @param password
     * @return User
     */
    @Override
    public User getUser(String login, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        User user = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement("SELECT * FROM user WHERE u_login=?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (User.createPasswordHash(password) == resultSet.getInt("u_password")) {
                    final int id = resultSet.getInt("u_id");
                    final String existsLogin = resultSet.getString("u_login");
                    final String existsPassword = resultSet.getString("u_password");
                    final String email = resultSet.getString("u_email");
                    final boolean privileges = resultSet.getBoolean("u_privileges");
                    user = new User(id, existsLogin, existsPassword, email, privileges);
                } else {
                    throw new DAOException("Password doesn't match");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return user;
    }

    /**
     * Method of creating user
     * @param login
     * @param password
     * @param email
     * @throws DAOException
     */
    @Override
    public void createUser(String login, String password, String email) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (u_login, u_password, u_email, u_privileges) VALUES (?, ?, ?, ?)");
            statement.setString(1, login);
            statement.setInt(2, User.createPasswordHash(password));
            statement.setString(3, email);
            statement.setBoolean(4, false);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Email or login is already used");
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    /**
     * Method of getting user by id
     * @param id
     * @return User
     * @throws DAOException
     */
    @Override
    public User getUserById(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE u_id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                final int userId = resultSet.getInt("u_id");
                final String existsLogin = resultSet.getString("u_login");
                final String email = resultSet.getString("u_email");
                return new User(userId, existsLogin, null, email, false);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DAOException("User with such id doesn't exist");
        } finally {
            if (connectionPool != null) {
                connectionPool.releaseConnection(connection);
        }}
    }
}
