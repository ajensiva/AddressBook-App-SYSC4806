package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AddressBookJpaTest {

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setup() {
        java.util.Map<String, Object> props = new java.util.HashMap<>();
        // create or drop-and-create for a clean slate
        props.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
        // keep your existing JDBC URL/driver coming from persistence.xml

        emf = jakarta.persistence.Persistence.createEntityManagerFactory("jpa-test", props);
    }

    @AfterClass
    public static void teardown() {
        if (emf != null) emf.close();
    }

    @Test
    public void canPersistAddressBookWithBuddies() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        AddressBook book = new AddressBook();
        BuddyInfo a = new BuddyInfo("Alice", "555-1234");
        BuddyInfo b = new BuddyInfo("Bob", "555-5678");
        book.addBuddy(a);
        book.addBuddy(b);

        tx.begin();
        em.persist(book);
        tx.commit();

        AddressBook fromDb = em.find(AddressBook.class, book.getId());
        assertNotNull(fromDb);
        assertEquals(2, fromDb.getBuddies().size());

        List<BuddyInfo> buddies = em.createQuery("SELECT bi FROM BuddyInfo bi", BuddyInfo.class)
                .getResultList();
        assertTrue(buddies.size() >= 2);

        em.close();
    }
}
