package by.bsuir.bookmaker.beans;

public class Tournament {
    private int id;
    private String name;
    private String description;
    private int sportTypeId;

    public Tournament(int id, String name, String description, int sportTypeId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sportTypeId = sportTypeId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSportTypeId() {
        return sportTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
