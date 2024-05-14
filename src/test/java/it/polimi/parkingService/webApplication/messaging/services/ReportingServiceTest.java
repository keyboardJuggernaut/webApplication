package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.messaging.dao.ReportingRepository;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import it.polimi.parkingService.webApplication.messaging.exceptions.InvalidThreadStatus;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReportingServiceTest {
    @Mock
    private ReportingRepository reportingRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private ReportingService reportingService;

    private Reporting reporting;
    private User user;
    private Response response;

    @BeforeEach
    public void setUp() {
        reporting = new Reporting();
        reporting.setId(1L);
        user = new User();
        user.setUserName("testUsername");
        response = new Response();
    }

    @Test
    public void testCompleteReporting() {
        String username = "testUsername";
        Reporting reporting = new Reporting();
        User user = new User();
        user.setUserName(username);

        when(userService.findByUserName(username)).thenReturn(user);
        when(reportingRepository.save(any(Reporting.class))).thenReturn(reporting);

        Reporting result = reportingService.completeReporting(new Reporting(), username);

        assertNotNull(result);
        verify(userService).findByUserName(username);
        verify(reportingRepository).save(any(Reporting.class));
    }

    @Test
    public void testUpdateThread_whenThreadIsOpen() {
        Reporting report = new Reporting();
        report.setOpen(true);

        when(reportingRepository.findById(anyLong())).thenReturn(Optional.of(report));
        when(reportingRepository.save(any(Reporting.class))).thenReturn(report);

        assertDoesNotThrow(() -> reportingService.updateThread(1L));

        assertFalse(report.isOpen());
        verify(reportingRepository).findById(anyLong());
        verify(reportingRepository).save(any(Reporting.class));
    }

    @Test
    public void testUpdateThread_whenThreadIsClosed() {
        Reporting report = new Reporting();
        report.setOpen(false);

        when(reportingRepository.findById(anyLong())).thenReturn(Optional.of(report));

        assertThrows(InvalidThreadStatus.class, () -> reportingService.updateThread(1L));

        assertFalse(report.isOpen());
        verify(reportingRepository).findById(anyLong());
        verifyNoMoreInteractions(reportingRepository);
    }

    @Test
    public void testAddResponseToReporting_Success() throws AddingResponseForbidden {
        when(userService.findByUserName("testUsername")).thenReturn(user);
        when(reportingRepository.findById(1L)).thenReturn(Optional.of(reporting));

        reportingService.addResponseToReporting(1L, response, "testUsername");

        assertEquals(user, response.getAuthor());
        assertNotNull(response.getTimestamp());
        verify(reportingRepository).save(reporting);
    }

    @Test
    public void testAddResponseToReporting_UserNotFound() {
        when(userService.findByUserName("testUsername")).thenReturn(null);

        assertThrows(ResourceNotFound.class, () -> {
            reportingService.addResponseToReporting(1L, response, "testUsername");
        });
    }

    @Test
    public void testAddResponseToReporting_ReportingNotFound() {
        when(userService.findByUserName("testUsername")).thenReturn(user);
        when(reportingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reportingService.addResponseToReporting(1L, response, "testUsername");
        });
    }

    @Test
    public void testFindAll() {
        reportingService.findAll();
        verify(reportingRepository).findAll();
    }

    @Test
    public void testFindById_Success() {
        when(reportingRepository.findById(1L)).thenReturn(Optional.of(reporting));

        Reporting foundReporting = reportingService.findById(1L);

        assertEquals(reporting, foundReporting);
    }

    @Test
    public void testFindById_NotFound() {
        when(reportingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reportingService.findById(1L);
        });
    }

    @Test
    public void testSave() {
        when(reportingRepository.save(reporting)).thenReturn(reporting);

        Reporting savedReporting = reportingService.save(reporting);

        assertEquals(reporting, savedReporting);
        verify(reportingRepository).save(reporting);
    }

    @Test
    public void testDeleteById() {
        reportingService.deleteById(1L);
        verify(reportingRepository).deleteById(1L);
    }

}
