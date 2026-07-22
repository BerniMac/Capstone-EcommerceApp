package controller;
/*

Author:isheanesu chowuraya 223182192
date:19 july 2026
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ca.cput.commerce.controller.CardController;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.service.CardService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CardController.class)
class CardControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CardService cardService;

    private Card sampleCard() {
        return new Card.Builder()
                .setCardId("card-1")
                .setCardHolderName("Anesu M")
                .setCardType("VISA")
                .setCardNumber("4111111111111111")
                .setCardExpiry("12/28")
                .setCardCVV("123")
                .build();
    }

    @Test
    void givenCards_whenGetAllCards_thenReturnJsonArray() throws Exception {
        given(cardService.findAll()).willReturn(List.of(sampleCard()));

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].cardId", org.hamcrest.Matchers.is("card-1")));
    }

    @Test
    void givenValidId_whenGetCardById_thenReturnCard() throws Exception {
        given(cardService.findById("card-1")).willReturn(sampleCard());

        mockMvc.perform(get("/api/cards/{id}", "card-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardType", org.hamcrest.Matchers.is("VISA")));
    }

    @Test
    void givenInvalidId_whenGetCardById_thenReturn404() throws Exception {
        given(cardService.findById("bad-id"))
                .willThrow(new EntityNotFoundException("Card not found with id: bad-id"));

        mockMvc.perform(get("/api/cards/{id}", "bad-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidCard_whenCreateCard_thenReturn201() throws Exception {
        Card card = sampleCard();
        given(cardService.save(any(Card.class))).willReturn(card);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardId", org.hamcrest.Matchers.is("card-1")));
    }

    @Test
    void givenValidId_whenDeleteCard_thenReturn204() throws Exception {
        mockMvc.perform(delete("/api/cards/{id}", "card-1"))
                .andExpect(status().isNoContent());
    }
}
