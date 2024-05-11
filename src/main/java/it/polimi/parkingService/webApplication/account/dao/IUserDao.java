package it.polimi.parkingService.webApplication.account.dao;


import it.polimi.parkingService.webApplication.account.models.User;

public interface IUserDao {

    User findByUserName(String userName);
    User findById(Long id);

    void save(User theUser);

    
}
