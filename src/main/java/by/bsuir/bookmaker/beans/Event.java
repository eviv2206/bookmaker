package by.bsuir.bookmaker.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private String description;
    private Date start_time;
    private String result;
    private int tournamentID;
    private int winnerID;
    private List<Integer> participantsIDs;

    public Event(int id, String name, String description, Date start_time, String result, int tournamentID, int winnerID, List<Integer> participantsIDs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_time = start_time;
        this.result = result;
        this.tournamentID = tournamentID;
        this.winnerID = winnerID;
        this.participantsIDs = participantsIDs;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart_time() {
        return start_time;
    }

    public String getResult() {
        return result;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public List<Integer> getParticipantsIDs() {
        return participantsIDs;
    }
}
