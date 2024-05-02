package it.polimi.parkingService.webApplication.account.service;

import it.polimi.parkingService.webApplication.account.dao.RoleDao;
import it.polimi.parkingService.webApplication.account.dao.UserDao;
import it.polimi.parkingService.webApplication.account.models.Role;
import it.polimi.parkingService.webApplication.account.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {

    private UserDao userDao;

    private RoleDao roleDao;

    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        // check the database if the user already exists
        return userDao.findByUserName(userName);
    }

    @Override
    public void save(User webUser) {
        User user = new User();

        // assign user details to the user object
        user.setUserName(webUser.getUserName());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEnabled(true);
        user.setLicensePlate(webUser.getLicensePlate());
        user.setIsDisabled(webUser.getIsDisabled());
        user.setIsPregnant(webUser.getIsPregnant());
        user.setPaymentMethod(webUser.getPaymentMethod());

        // give user default role of "employee"
        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_CUSTOMER")));

        // save user in the database
        userDao.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}