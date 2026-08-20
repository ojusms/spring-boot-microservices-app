package com.DemoBank.Cards.Service.Impl;

import com.DemoBank.Cards.Constants.CardsConstants;
import com.DemoBank.Cards.Entity.Cards;
import com.DemoBank.Cards.Repository.CardsRepository;
import com.DemoBank.Cards.Service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()) {
            throw new RuntimeException("Card already exists for this mobile number");
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     *  helper method to create a new Card for a given mobile number
     * @param mobileNumber input mobile number received
     * @return Cards object with details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        newCard.setMobileNumber(mobileNumber);
        // generate a random card number of 12 digits
        long randomCardNumber = 10000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(randomCardNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAmountUsed(CardsConstants.NEW_CARD_LIMIT);
        newCard.setCreatedAt(LocalDateTime.now());
        newCard.setCreatedBy("Anonymous");
        return newCard;
    }
}
