package za.ca.cput.commerce.factory;
/*
cardFactory.java
Author:isheanesu chowurya 223182192
date:26March 2026
 */
import za.ca.cput.commerce.domain.Card;

public class CardFactory {
    public static Card createCard(String Id, String name, String type, String number, String expiry, String cvv) {
        return new Card.Builder()
                .setCardId("id434")
                .setCardNumber("99099452")
                .setCardType("standard")
                .setCardHolderName("john")
                .setCardExpiry("24/04/2028")
                .setCardCVV("9099343")
                .build();
    }
}
