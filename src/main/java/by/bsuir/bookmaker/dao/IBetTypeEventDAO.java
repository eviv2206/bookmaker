package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.BetTypeEvent;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.util.List;

public interface IBetTypeEventDAO {
    void add(int betTypeEventEventId, int betTypeEventBetTypeId, double betTypeEventCoefficient) throws DAOException;
    void delete(int id) throws DAOException;
    BetTypeEvent getById(int id) throws DAOException;

    List<BetTypeEvent> getAllBetTypeEventsByEventId(int eventId) throws DAOException;
    List<BetTypeEvent> getAllBetTypeEvents() throws DAOException;
}
