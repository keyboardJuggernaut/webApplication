package it.polimi.parkingService.webApplication.account.services;



import it.polimi.parkingService.webApplication.account.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User findByUserName(String userName);
    User findById(Long id);

    void save(User webUser);

}
