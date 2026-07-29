package repository;
/*
cardReposiotory.java
author:isheanesu chowuraya 223182192
date 25 june 2025
 */
import domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {

}
