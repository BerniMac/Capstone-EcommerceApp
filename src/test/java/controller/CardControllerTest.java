package controller;
/*
cardControllerTest.java
author:isheanesu chowuraya 223182192
date 19 july 2026
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Card;
import factory.CardFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import service.CardService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CardController.class, useDefaultFilters = false)
@ContextConfiguration(classes = {CardController.class, CardControllerTest.MockServiceConfig.class})
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockServiceConfig {

        @Bean
        CardService cardService() {
            return Mockito.mock(CardService.class);
        }

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Test
    void createCard() throws Exception {

        Card card = new Card.Builder()
                .setCardHolderName("John Doe")
                .setCardType("Visa")
                .setCardNumber("4111111111111111")
                .setCardExpiry("12/30")
                .setCardCVV("123")
                .build();

        when(cardService.save(any(Card.class))).thenReturn(card);

        mockMvc.perform(post("/cards")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardHolderName").value("John Doe"))
                .andExpect(jsonPath("$.cardType").value("Visa"));

        verify(cardService).save(any(Card.class));
    }

    @Test
    void getCardById() throws Exception {

        Card card = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John Doe")
                .setCardType("Visa")
                .build();

        when(cardService.findById("1")).thenReturn(Optional.of(card));

        mockMvc.perform(get("/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value("1"))
                .andExpect(jsonPath("$.cardHolderName").value("John Doe"));

        verify(cardService).findById("1");
    }

    @Test
    void getAllCards() throws Exception {

        Card card = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John Doe")
                .build();

        when(cardService.findAll()).thenReturn(List.of(card));

        mockMvc.perform(get("/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cardHolderName").value("John Doe"));

        verify(cardService).findAll();
    }

    @Test
    void updateCard() throws Exception {

        Card card = new Card.Builder()
                .setCardId("1")
                .setCardHolderName("Jane Doe")
                .setCardType("MasterCard")
                .build();

        when(cardService.update(eq("1"), any(Card.class))).thenReturn(card);

        mockMvc.perform(put("/cards/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardHolderName").value("Jane Doe"));

        verify(cardService).update(eq("1"), any(Card.class));
    }

    @Test
    void deleteCard() throws Exception {

        doNothing().when(cardService).delete("1");

        mockMvc.perform(delete("/cards/1"))
                .andExpect(status().isNoContent());

        verify(cardService).delete("1");
    }
}