package by.bsuir.bookmaker.dao;


import by.bsuir.bookmaker.beans.UserBet;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IUserBetDAO {
    void addUserBet(LocalDateTime date, int betAmount, double winAmount, String score, int userId, int betTypeEventId) throws DAOException;
    void updateUserBet(LocalDateTime date, int betAmount, double winAmount, String result, int userId, int betTypeEventId, int id) throws DAOException;
    UserBet getUserBetById(int id) throws DAOException;
    List<UserBet> getAllUsersBets() throws DAOException;
    List<UserBet> getAllUserBetsByUserId(int userId) throws DAOException;
    List<UserBet> getAllUserBetsByEventId(int eventId) throws DAOException;
}
