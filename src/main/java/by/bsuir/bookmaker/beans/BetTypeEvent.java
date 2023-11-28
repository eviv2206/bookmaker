package by.bsuir.bookmaker.beans;

public class BetTypeEvent {
    private int betTypeEventId;
    private int betTypeId;
    private int eventId;
    private double coefficient;

    public BetTypeEvent(int betTypeEventId, int betTypeId, int eventId, double coefficient) {
        this.betTypeEventId = betTypeEventId;
        this.betTypeId = betTypeId;
        this.eventId = eventId;
        this.coefficient = coefficient;
    }


    public int getBetTypeEventId() {
        return betTypeEventId;
    }

    public int getBetTypeId() {
        return betTypeId;
    }

    public int getEventId() {
        return eventId;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
