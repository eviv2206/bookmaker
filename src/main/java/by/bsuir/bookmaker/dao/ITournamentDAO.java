package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.Tournament;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.util.ArrayList;

public interface ITournamentDAO {
    void addTournament(String name, String description, int sportTypeID) throws DAOException;
    Tournament getTournament(int tournamentID) throws DAOException;
    void deleteTournament(int tournamentID) throws DAOException;
    void updateTournament(String name, String description, int sportTypeID, int tournamentID) throws DAOException;
    ArrayList<Tournament> getAllTournaments() throws DAOException;
}
