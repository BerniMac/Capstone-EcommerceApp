package repository.impl;
/*
cardRepositoryIMPLTes.java
author:isheanesu chowuraya 223182192
date 26 March 2026


import domain.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardRepositoryImplTest {
    private IRepository<Card, String> repo;

    @BeforeEach
    public void setUp() {
        repo = new CardRepositoryImpl();
    }

    private Card buildCard(String id) {
        return new Card.Builder()
                .setCardId(id)
                .setCardHolderName("john")
                .build();
    }

    @Test
    public void testCreate() {
        assertEquals("id434", repo.create(buildCard("id434")).getCardId());
    }

    @Test
    public void testRead() {
        repo.create(buildCard("id434"));
        assertEquals("id434", repo.read("id434").getCardId());
    }

    @Test
    public void testUpdate() {
        repo.create(buildCard("id434"));
        assertEquals("id434", repo.update(buildCard("id434")).getCardId());
    }

    @Test
    public void testDelete() {
        repo.create(buildCard("id434"));
        assertTrue(repo.delete("id434"));
    }

    @Test
    public void testFindAll() {
        repo.create(buildCard("id434"));
        repo.create(buildCard("id435"));
        assertEquals(2, repo.findAll().size());
    }
}
*/