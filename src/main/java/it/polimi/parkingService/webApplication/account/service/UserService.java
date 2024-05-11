package it.polimi.parkingService.webApplication.account.service;

import it.polimi.parkingService.webApplication.account.dao.IRoleDao;
import it.polimi.parkingService.webApplication.account.dao.IUserDao;
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
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The {@code UserService} handles any user related business logic
 */
@Service
public class UserService implements IUserService {

    private final IUserDao IUserDao;

    private final IRoleDao IRoleDao;

    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(IUserDao IUserDao, IRoleDao IRoleDao, BCryptPasswordEncoder passwordEncoder) {
        this.IUserDao = IUserDao;
        this.IRoleDao = IRoleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return IUserDao.findByUserName(userName);
    }

    @Override
    public User findById(Long id) {
        return IUserDao.findById(id);
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

        // give user default role of "customer"
        user.setRoles(Collections.singletonList(IRoleDao.findRoleByName("ROLE_CUSTOMER")));

        IUserDao.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = IUserDao.findByUserName(userName);
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