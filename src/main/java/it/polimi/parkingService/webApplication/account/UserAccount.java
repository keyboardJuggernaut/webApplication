package it.polimi.parkingService.webApplication.account;

public abstract class UserAccount extends User{
    private String firstName;
    private String lastName;

    public abstract UserAccount getDetails();

    public UserAccount(String username, String password, UserStatus status, String firstName, String lastName) {
        super(username, password, status);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserAccount() {}
}
