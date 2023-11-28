package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.User;
import by.bsuir.bookmaker.dao.exception.DAOException;

public interface IAuthDAO {
    User getUser(String login, String password);

    void createUser(String login, String password, String email) throws DAOException;
    User getUserById(int id) throws DAOException;
}
