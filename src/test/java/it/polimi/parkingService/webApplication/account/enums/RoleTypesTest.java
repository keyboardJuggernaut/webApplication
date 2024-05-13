package it.polimi.parkingService.webApplication.account.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTypesTest {
    @Test
    public void testRoleTypes() {
        assertEquals(RoleTypes.ROLE_USER, RoleTypes.valueOf("ROLE_USER"));
        assertEquals(RoleTypes.ROLE_GUARDIAN, RoleTypes.valueOf("ROLE_GUARDIAN"));
        assertEquals(RoleTypes.ROLE_ADMIN, RoleTypes.valueOf("ROLE_ADMIN"));
    }
}
