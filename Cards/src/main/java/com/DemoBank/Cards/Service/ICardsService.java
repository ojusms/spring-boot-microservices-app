package com.DemoBank.Cards.Service;

import com.DemoBank.Cards.DTO.CardsDTO;

public interface ICardsService {

    /**
     *
     * @param mobileNumber input mobile number of customer
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber input mobile number of customer
     * @return card details for given mobileNumber
     */
    CardsDTO fetchCard(String mobileNumber);

    /**
     *
     * @param cardsDTO input card details
     * @return boolean value for success or fail
     */
    boolean updateCardDetails(CardsDTO cardsDTO);
}
