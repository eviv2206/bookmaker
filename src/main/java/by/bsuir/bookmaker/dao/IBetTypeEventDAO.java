package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.dao.exception.DAOException;

public interface IBetTypeEventDAO {
    BetTypeEvent getById(int id) throws DAOException;
}
