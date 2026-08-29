package za.ac.cput.ecommerce.controller;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService service;

    @Autowired
    private ObjectMapper mapper;

    private Card getCard() {

        return new Card.Builder()
                .setCardId("1")
                .setCardHolderName("John Smith")
                .setCardType("VISA")
                .setCardNumber("4111111111111111")
                .setCardExpiry("12/28")
                .setCardCVV("123")
                .build();
    }

    @Test
    void create() throws Exception {

        Card card = getCard();

        when(service.create(any(Card.class))).thenReturn(card);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(card)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value("1"))
                .andExpect(jsonPath("$.cardHolderName").value("John Smith"));
    }

    @Test
    void read() throws Exception {

        when(service.read("1")).thenReturn(getCard());

        mockMvc.perform(get("/api/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardType").value("VISA"));
    }

    @Test
    void getAll() throws Exception {

        when(service.getAll()).thenReturn(List.of(getCard()));

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void update() throws Exception {

        Card card = getCard();

        when(service.update(any(Card.class))).thenReturn(card);

        mockMvc.perform(put("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(card)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardHolderName").value("John Smith"));
    }

    @Test
    void deleteCard() throws Exception {

        mockMvc.perform(delete("/api/cards/1"))
                .andExpect(status().isNoContent());
    }
}