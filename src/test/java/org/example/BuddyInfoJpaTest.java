package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BuddyInfoJpaTest {

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setup() {
        Map<String, Object> props = new HashMap<>();
        props.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
        emf = Persistence.createEntityManagerFactory("jpa-test", props);
    }

    @AfterClass
    public static void teardown() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void canPersistAndQueryBuddyInfo() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        BuddyInfo b1 = new BuddyInfo("Alice", "555-1234");
        BuddyInfo b2 = new BuddyInfo("Bob",   "555-5678");

        tx.begin();
        em.persist(b1);
        em.persist(b2);
        tx.commit();

        List<BuddyInfo> all = em.createQuery(
                        "SELECT b FROM BuddyInfo b ORDER BY b.name", BuddyInfo.class)
                .getResultList();

        assertEquals(2, all.size());
        assertNotNull(all.get(0).getId());
        assertNotNull(all.get(1).getId());

        BuddyInfo alice = em.createQuery(
                        "SELECT b FROM BuddyInfo b WHERE b.name = :name", BuddyInfo.class)
                .setParameter("name", "Alice")
                .getSingleResult();

        assertEquals("Alice", alice.getName());
        assertEquals("555-1234", alice.getNumber());

        em.close();
    }
}
