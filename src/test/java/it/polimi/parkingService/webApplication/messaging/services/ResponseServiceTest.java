package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ResponseRepository;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ResponseServiceTest {

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private ResponseService responseService;


    private Response response;

    @BeforeEach
    public void setUp() {
        response = new Response();
        response.setId(1L);
    }

    @Test
    public void testFindAll() {
        assertNull(responseService.findAll());
    }

    @Test
    public void testFindById() {
        assertNull(responseService.findById(1L));
    }

    @Test
    public void testSave() {
        when(responseRepository.save(response)).thenReturn(response);

        Response savedResponse = responseService.save(response);

        assertEquals(response, savedResponse);
        verify(responseRepository).save(response);
    }

    @Test
    public void testDeleteById() {
        responseService.deleteById(1L);
        verify(responseRepository).deleteById(1L);
    }
}
