package it.polimi.parkingService.webApplication.account.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDaoTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        reset(entityManager);
    }

    @Test
    public void testFindByUserName_UserFound() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new User());

        User user = userDao.findByUserName("testUser");

        verify(entityManager).createQuery(anyString(), eq(User.class));
        verify(query).setParameter(anyString(), anyString());
        verify(query).getSingleResult();

        assertEquals(User.class, user.getClass());
    }

    @Test
    public void testFindByUserName_UserNotFound() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);

        User user = userDao.findByUserName("nonExistingUser");

        verify(entityManager).createQuery(anyString(), eq(User.class));
        verify(query).setParameter(anyString(), anyString());
        verify(query).getSingleResult();

        assertNull(user);
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setId(1L);
        when(entityManager.find(User.class, 1L)).thenReturn(user);

        User foundUser = userDao.findById(1L);

        verify(entityManager).find(User.class, 1L);
        assertEquals(user, foundUser);
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setId(1L);
        userDao.save(user);

        verify(entityManager).merge(user);
    }
}
