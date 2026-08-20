package com.DemoBank.Cards.Mapper;

import com.DemoBank.Cards.DTO.CardsDTO;
import com.DemoBank.Cards.Entity.Cards;

/**
 * Mapper class with methods to map data between Entity class and DTO class
 */
public class CardsMapper {
    /**
     * take values from input Cards object and populate into CardsDTO object
     * @param cards input Cards object
     * @param cardsDTO input CardsDTO object to map values and return
     * @return CardsDTO Object mapped with values from input Cards Object
     */
    private static CardsDTO mapToCardsDTO(Cards cards, CardsDTO cardsDTO) {
        cardsDTO.setCardNumber(cards.getCardNumber());
        cardsDTO.setCardType(cards.getCardType());
        cardsDTO.setMobileNumber(cards.getMobileNumber());
        cardsDTO.setTotalLimit(cards.getTotalLimit());
        cardsDTO.setAmountUsed(cards.getAmountUsed());
        cardsDTO.setAvailableAmount(cards.getAvailableAmount());
        return cardsDTO;
    }

    /**
     * take values from input CardsDTO object and populate into Cards object
     * @param cardsDTO input CardsDTO object
     * @param cards input Cards object to map values and return
     * @return Cards Object mapped with values from input CardsDTO object
     */
    private static Cards mapToCards(CardsDTO cardsDTO, Cards cards) {
        cards.setMobileNumber(cardsDTO.getMobileNumber());
        cards.setCardNumber(cardsDTO.getCardNumber());
        cards.setCardType(cardsDTO.getCardType());
        cards.setTotalLimit(cardsDTO.getTotalLimit());
        cards.setAmountUsed(cardsDTO.getAmountUsed());
        cards.setAvailableAmount(cardsDTO.getAvailableAmount());
        return cards;
    }
}
