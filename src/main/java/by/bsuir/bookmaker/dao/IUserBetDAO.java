package by.bsuir.bookmaker.dao;


import by.bsuir.bookmaker.beans.UserBet;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.util.Date;
import java.util.List;

public interface IUserBetDAO {
    void addUserBet(Date date, int betAmount, double winAmount, int userId, int betTypeEventId) throws DAOException;
    UserBet getUserBetById(int id) throws DAOException;
    List<UserBet> getAllUsersBets() throws DAOException;
    List<UserBet> getAllUserBetsByUserId(int userId) throws DAOException;
}
