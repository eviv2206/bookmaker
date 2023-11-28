package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.BetType;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.util.List;

public interface IBetTypeDAO {
    BetType getBetType(int betTypeID) throws DAOException;
    List<BetType> getAllBetTypes() throws DAOException;

}
