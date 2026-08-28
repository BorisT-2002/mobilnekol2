package com.example.kolokvijum2;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEvenIdFilter() {
        Role role1 = new Role(1, "Admin", "Administrator role");
        Role role2 = new Role(2, "User", "Regular user role");
        Role role3 = new Role(3, "Guest", "Guest role");

        assertTrue("ID 2 should be even", role2.id % 2 == 0);
        assertFalse("ID 1 should not be even", role1.id % 2 == 0);
        assertFalse("ID 3 should not be even", role3.id % 2 == 0);
    }
}
