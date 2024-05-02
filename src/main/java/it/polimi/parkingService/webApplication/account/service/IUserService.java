package it.polimi.parkingService.webApplication.account.service;



import it.polimi.parkingService.webApplication.account.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    public User findByUserName(String userName);
    void save(User webUser);

}
