package it.polimi.parkingService.webApplication.account.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {
    @Test
    public void testNoArgsConstructor() {
        Role role = new Role();
        assertNull(role.getId());
        assertNull(role.getName());
    }

    @Test
    public void testAllArgsConstructor() {
        Role role = new Role("ROLE_GUARDIAN");
        assertNull(role.getId());
        assertEquals("ROLE_GUARDIAN", role.getName());
    }

    @Test
    public void testGettersAndSetters() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_GUARDIAN");
        assertEquals(Long.valueOf(1L), role.getId());
        assertEquals("ROLE_GUARDIAN", role.getName());
    }

    @Test
    public void testEquals() {
        Role role1 = new Role(1L, "ROLE_GUARDIAN");
        Role role2 = new Role(1L, "ROLE_GUARDIAN");
        Role role3 = new Role(2L, "AnotherRole");

        assertEquals(role1, role2);
        assertNotEquals(role1, role3);
    }

    @Test
    public void testHashCode() {
        Role role1 = new Role(1L, "ROLE_GUARDIAN");
        Role role2 = new Role(1L, "ROLE_GUARDIAN");

        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    public void testToString() {
        Role role = new Role(1L, "ROLE_GUARDIAN");
        assertEquals("Role{id=1, name='ROLE_GUARDIAN'}", role.toString());
    }
}
