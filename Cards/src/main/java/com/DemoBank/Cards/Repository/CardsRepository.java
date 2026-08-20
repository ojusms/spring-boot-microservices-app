package com.DemoBank.Cards.Repository;

import com.DemoBank.Cards.Entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Integer> {
}
