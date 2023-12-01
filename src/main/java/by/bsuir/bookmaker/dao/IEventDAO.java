package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.Event;
import by.bsuir.bookmaker.beans.Participant;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IEventDAO {
    void addEvent(String name, String description, LocalDateTime date, String result, int tournamentID, List<Integer> participants) throws DAOException;
    Event getEvent(int id) throws DAOException;
    void updateEvent(String name, String description, LocalDateTime date, String result, int tournamentID, List<Integer> participants, int winnerID, int id) throws DAOException;
    void deleteEvent(int id) throws DAOException;
    ArrayList<Event> getAllEvents() throws DAOException;
}
