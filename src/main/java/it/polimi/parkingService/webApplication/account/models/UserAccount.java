package it.polimi.parkingService.webApplication.account.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserAccount extends User{
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public abstract UserAccount getDetails();

    public UserAccount(String username, String password, UserStatus status, String firstName, String lastName) {
        super(username, password, status);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserAccount() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
