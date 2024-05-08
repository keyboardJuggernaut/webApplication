package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ForumRepository;
import it.polimi.parkingService.webApplication.messaging.models.Forum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForumService implements IForumService{
    
    private ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public Forum findById(String name) {
        Optional<Forum> result = forumRepository.findById(name);

        Forum forum = null;

        if (result.isPresent()) {
            forum = result.get();
            return forum;
        }
        return forum;
    }

    @Override
    @Transactional
    public void save(Forum forum) {
        forumRepository.save(forum);
    }
}
