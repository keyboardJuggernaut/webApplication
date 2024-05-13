package it.polimi.parkingService.webApplication.account.services;

import it.polimi.parkingService.webApplication.account.dao.IRoleDao;
import it.polimi.parkingService.webApplication.account.dao.IUserDao;
import it.polimi.parkingService.webApplication.account.models.Role;
import it.polimi.parkingService.webApplication.account.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private IUserDao userDao;

    @Mock
    private IRoleDao roleDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;
    

    @Test
    public void testFindByUserName() {
        User expectedUser = new User("testUsername", "password", true);
        when(userDao.findByUserName("testUsername")).thenReturn(expectedUser);
        User actualUser = userService.findByUserName("testUsername");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindById() {
        User expectedUser = new User();
        when(userDao.findById(1L)).thenReturn(expectedUser);
        User actualUser = userService.findById(1L);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testSave() {
        User newUser = new User("testUsername", "password", true);
        newUser.setUserName("testUsername");
        newUser.setPassword("password");
        when(roleDao.findRoleByName("ROLE_CUSTOMER")).thenReturn(new Role("ROLE_CUSTOMER"));
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        userService.save(newUser);

        User expectedUser = new User();
        expectedUser.setUserName("testUsername");
        expectedUser.setPassword("hashedPassword");
        Collection<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_CUSTOMER"));
        expectedUser.setRoles(roles);
        expectedUser.setEnabled(true);
        
        verify(userDao, times(1)).save(expectedUser);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setUserName("testUsername");
        user.setPassword("hashedPassword");
        Role role = new Role("ROLE_CUSTOMER");
        user.setRoles(Collections.singletonList(role));
        when(userDao.findByUserName("testUsername")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testUsername");

        assertEquals(user.getUserName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(role.getName())));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userDao.findByUserName("testUsername")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("testUsername");
        });
    }
}
