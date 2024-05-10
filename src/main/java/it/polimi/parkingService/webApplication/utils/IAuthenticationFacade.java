package it.polimi.parkingService.webApplication.utils;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    String getAuthenticatedUsername() throws AuthenticationFacadeUserUnauthenticated;
}

