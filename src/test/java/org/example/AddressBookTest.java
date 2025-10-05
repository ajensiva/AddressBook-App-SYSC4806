package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddressBookTest {
    @Test
    public void testAddressBook() {
        AddressBook book = new AddressBook();
        BuddyInfo buddy1 = new BuddyInfo("Alice", "123-123-1234", "Ottawa, ON");
        BuddyInfo buddy2 = new BuddyInfo("Bob", "456-456-4567", "Toronto, ON");

        book.addBuddy(buddy1);
        book.addBuddy(buddy2);

        assertTrue(book.getBuddies().contains(buddy1));
        assertTrue(book.getBuddies().contains(buddy2));
    }
}