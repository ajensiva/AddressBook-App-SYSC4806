package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    @Test
    public void testBuddyInfo() {
        BuddyInfo buddy1 = new BuddyInfo("Alice", "123-123-1234", "Ottawa, ON");
        BuddyInfo buddy2 = new BuddyInfo("Bob", "456-456-4567", "Toronto, ON");

        assertEquals(buddy1.getName(), "Alice");
        assertEquals(buddy2.getName(), "Bob");
        assertEquals(buddy1.getNumber(), "123-123-1234");
        assertEquals(buddy2.getNumber(), "456-456-4567");
        assertEquals(buddy1.getAddress(), "Ottawa, ON");
        assertEquals(buddy2.getAddress(), "Toronto, ON");
    }

}