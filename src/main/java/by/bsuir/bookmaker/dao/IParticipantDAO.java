package by.bsuir.bookmaker.dao;

import by.bsuir.bookmaker.beans.Participant;
import by.bsuir.bookmaker.dao.exception.DAOException;

import java.util.ArrayList;

public interface IParticipantDAO {
    void addParticipant(String name) throws DAOException;
    Participant getParticipant(int id) throws DAOException;
    void deleteParticipant(int id) throws DAOException;
    void updateParticipant(String name, int id) throws DAOException;
    ArrayList<Participant> getAllParticipants() throws DAOException;

}
