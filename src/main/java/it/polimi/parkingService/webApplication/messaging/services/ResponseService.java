package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ResponseRepository;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import org.springframework.stereotype.Service;

@Service
public class ResponseService implements IResponseService {

    private ResponseRepository responseRepository;

    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public void save(Response response) {
        responseRepository.save(response);
    }
}
