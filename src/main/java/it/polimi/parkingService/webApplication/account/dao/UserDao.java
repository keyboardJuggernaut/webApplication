package it.polimi.parkingService.webApplication.account.dao;


import it.polimi.parkingService.webApplication.account.models.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);

    
}
