package it.polimi.parkingService.webApplication.common;

import it.polimi.parkingService.webApplication.common.AuthenticationFacadeUserUnauthenticated;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    String getAuthenticatedUsername() throws AuthenticationFacadeUserUnauthenticated;
}

