package it.polimi.parkingService.webApplication.common;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationFacadeTest {
    @Test
    public void testGetAuthenticatedUsername_Authenticated() throws AuthenticationFacadeUserUnauthenticated {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthenticationFacade authenticationFacade = new AuthenticationFacade();
        String username = authenticationFacade.getAuthenticatedUsername();

        assertEquals("username", username);
    }

    @Test
    public void testGetAuthenticatedUsername_Unauthenticated() {
        AnonymousAuthenticationToken anonymousAuthenticationToken = mock(AnonymousAuthenticationToken.class);
        SecurityContextHolder.getContext().setAuthentication(anonymousAuthenticationToken);

        AuthenticationFacade authenticationFacade = new AuthenticationFacade();

        assertThrows(AuthenticationFacadeUserUnauthenticated.class, authenticationFacade::getAuthenticatedUsername);
    }
}
