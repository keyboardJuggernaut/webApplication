package it.polimi.parkingService.webApplication.security;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The {@code CustomAuthenticationSuccessHandler} handles any custom authentication related business logic
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    
    @Autowired
    public CustomAuthenticationSuccessHandler(UserService theUserService) {
        userService = theUserService;
    }

    /**
     * Defines action to perform for authentication process
     * @param request the http request
     * @param response the http response
     * @param authentication the authentication
     * @throws IOException if IO fails
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {


        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        // place user in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // redirect to home page
        response.sendRedirect(request.getContextPath() + "/");
    }

}