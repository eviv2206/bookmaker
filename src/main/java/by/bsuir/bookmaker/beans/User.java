package by.bsuir.bookmaker.beans;

public class User {
    private int id;
    private final String login;
    private final int passwordHash;
    private final String email;

    private final boolean privileges;

    public User(int id, String login, String password, String email, boolean privileges) {
        this.id = id;
        this.login = login;
        this.passwordHash = createPasswordHash(password);
        this.email = email;
        this.privileges = privileges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }


    public int getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPrivileges(){
        return privileges;
    }

    public static int createPasswordHash(String password){
        return password.hashCode();
    }

}
