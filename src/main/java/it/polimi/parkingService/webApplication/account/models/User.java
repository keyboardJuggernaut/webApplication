package it.polimi.parkingService.webApplication.account.models;

import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public class User extends BaseEntity {

    @Column(name="username")
    private String username;

    @Column(name="password")

    private String password;

    @Column(name="status_account")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(String username, String password, UserStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public User(){}

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
