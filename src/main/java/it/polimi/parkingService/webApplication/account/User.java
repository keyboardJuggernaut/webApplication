package it.polimi.parkingService.webApplication.account;

public class User {
    private int id;
    private String username;
    private String password;

    private UserStatus status;

    public User(String username, String password, UserStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
