package it.polimi.parkingService.webApplication.account.models;


import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.payment.models.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserTest {
    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUserName());
        assertNull(user.getPassword());
        assertFalse(user.isEnabled());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getLicensePlate());
        assertFalse(user.getIsDisabled());
        assertFalse(user.getIsPregnant());
        assertNull(user.getRoles());
        assertNull(user.getPaymentMethod());
        assertNull(user.getParkings());
        assertNull(user.getBookings());
    }

    @Test
    public void testAllArgsConstructor() {
        User user = new User("testUser", "testPassword", true);
        assertNull(user.getId());
        assertEquals("testUser", user.getUserName());
        assertEquals("testPassword", user.getPassword());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("testPassword");
        user.setEnabled(true);
        user.setFirstName("Mario");
        user.setLastName("Rossi");
        user.setLicensePlate("123");
        user.setIsDisabled(false);
        user.setIsPregnant(true);
        user.setRoles(List.of(new Role("ROLE_USER")));
        user.setPaymentMethod(mock(PaymentMethod.class));
        user.setParkings(Collections.singletonList(mock(Parking.class)));
        user.setBookings(Collections.singletonList(mock(Booking.class)));

        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("testUser", user.getUserName());
        assertEquals("testPassword", user.getPassword());
        assertTrue(user.isEnabled());
        assertEquals("Mario", user.getFirstName());
        assertEquals("Rossi", user.getLastName());
        assertEquals("123", user.getLicensePlate());
        assertFalse(user.getIsDisabled());
        assertTrue(user.getIsPregnant());
        assertEquals(1, user.getRoles().size());
        assertEquals(1, user.getParkings().size());
        assertEquals(1, user.getBookings().size());
    }

    @Test
    public void testEquals() {
        User user1 = new User("testUser", "testPassword", true);
        user1.setId(1L);
        User user2 = new User("testUser", "testPassword", true);
        user2.setId(1L);
        User user3 = new User("anotherUser", "testPassword", true);
        user3.setId(2L);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }

    @Test
    public void testHashCode() {
        User user1 = new User("testUser", "testPassword", true);
        user1.setId(1L);
        User user2 = new User("testUser", "testPassword", true);
        user2.setId(1L);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

}

