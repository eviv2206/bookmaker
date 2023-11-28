package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.SportType;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.util.ArrayList;

public interface ISportTypeDAO {
    void addSportType(String name, String description) throws DAOException;
    SportType getSportType(int sportID) throws DAOException;
    void deleteSportType(int sportID) throws DAOException;

    ArrayList<SportType> getAllSportTypes() throws DAOException;
    void updateSportType(String name, String description, int sportID) throws DAOException;
}
