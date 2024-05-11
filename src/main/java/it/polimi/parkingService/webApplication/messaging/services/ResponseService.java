package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ResponseRepository;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code ResponseService} handles any response related business logic
 */
@Service
public class ResponseService implements IResponseService {

    private final ResponseRepository responseRepository;

    /**
     * Constructs the service
     * @param responseRepository the repository handling response persistence logic
     */
    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Response> findAll() {
        return null;
    }

    @Override
    public Response findById(long id) {
        return null;
    }

    @Override
    public Response save(Response response) {
        return responseRepository.save(response);
    }

    @Override
    public void deleteById(long id) {
        responseRepository.deleteById(id);
    }
}
