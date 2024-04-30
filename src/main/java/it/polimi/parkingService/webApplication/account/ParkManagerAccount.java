package it.polimi.parkingService.webApplication.account;

public class ParkManagerAccount extends UserAccount{
    private boolean isAdmin;
    @Override
    public UserAccount getDetails() {
        return this;
    }

    public ParkManagerAccount(String username, String password, UserStatus status, String firstName, String lastName, boolean isAdmin) {
        super(username, password, status, firstName, lastName);
        this.isAdmin = isAdmin;
    }

    public ParkManagerAccount() {}

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
