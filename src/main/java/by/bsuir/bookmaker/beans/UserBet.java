package by.bsuir.bookmaker.beans;

import java.util.Date;

public class UserBet {
    private int id;
    private Date date;
    private int betAmount;
    private double winningAmount;
    private int betTypeEventID;
    private int userID;

    public UserBet(int id, Date date, int betAmount, double winningAmount, int betTypeEventID, int userID) {
        this.id = id;
        this.date = date;
        this.betAmount = betAmount;
        this.winningAmount = winningAmount;
        this.betTypeEventID = betTypeEventID;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(int winningAmount) {
        this.winningAmount = winningAmount;
    }

    public int getBetTypeEventID() {
        return betTypeEventID;
    }

    public void setBetTypeEventID(int betTypeEventID) {
        this.betTypeEventID = betTypeEventID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
