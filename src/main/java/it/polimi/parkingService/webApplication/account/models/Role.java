package it.polimi.parkingService.webApplication.account.models;

import it.polimi.parkingService.webApplication.account.enums.RoleTypes;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="account_id")
    private Account account;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private RoleTypes role;

    public Role(Account account, RoleTypes role) {
        this.account = account;
        this.role = role;
    }

    public Role(){}

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public RoleTypes getRole() {
        return role;
    }

    public void setRole(RoleTypes role) {
        this.role = role;
    }
}
