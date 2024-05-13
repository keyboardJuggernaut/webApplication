package it.polimi.parkingService.webApplication.account.dao;

import it.polimi.parkingService.webApplication.account.models.Role;
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
public class RoleDaoTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RoleDao roleDao;

    @BeforeEach
    public void setUp() {
        reset(entityManager);
    }

    @Test
    public void testFindRoleByName_RoleFound() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        TypedQuery<Role> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Role.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(role);

        Role foundRole = roleDao.findRoleByName("ROLE_ADMIN");

        verify(entityManager).createQuery(anyString(), eq(Role.class));
        verify(query).setParameter(anyString(), anyString());
        verify(query).getSingleResult();
        assertEquals(role, foundRole);
    }

    @Test
    public void testFindRoleByName_RoleNotFound() {
        TypedQuery<Role> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Role.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);

        Role foundRole = roleDao.findRoleByName("NON_EXISTING_ROLE");

        verify(entityManager).createQuery(anyString(), eq(Role.class));
        verify(query).setParameter(anyString(), anyString());
        verify(query).getSingleResult();
        assertNull(foundRole);
    }
}
