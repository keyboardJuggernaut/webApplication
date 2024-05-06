package it.polimi.parkingService.webApplication.messaging.services;


import it.polimi.parkingService.webApplication.messaging.models.Forum;

public interface IForumService  {

    public Forum findById(String name);
    public void save(Forum forum);
}
