package za.ca.cput.commerce.factory;
/*
cardFactory.java
Author:isheanesu chowurya 223182192
date:26March 2026
 */
import za.ca.cput.commerce.domain.Card;

public class CardFactory {

    public static Card createCard(String cardHolderName,
                                  String cardType,
                                  String cardNumber,
                                  String cardExpiry,
                                  String cardCVV) {

        return new Card.Builder()
                .setCardHolderName(cardHolderName)
                .setCardType(cardType)
                .setCardNumber(cardNumber)
                .setCardExpiry(cardExpiry)
                .setCardCVV(cardCVV)
                .build();
    }
}
