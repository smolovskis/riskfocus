package com.riskfocus.currencyconverter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.dto.CurrencyConversionRequest;
import com.riskfocus.currencyconverter.model.dto.UpdateConversionRateRequest;
import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = BEFORE_CLASS)
class CurrencyConversionControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CurrencyRepository repository;

    @Test
    @Order(1)
    void shouldAddCurrencyConversionRate() throws Exception {
        repository.save(currency("USD"));
        repository.save(currency("RUB"));

        mvc.perform(post("/api/v1/currency-conversion/add")
                        .content(asJsonString(UpdateConversionRateRequest.builder().from("USD").to("RUB").rate(new BigDecimal("1.1111")).build()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.from.code", is("USD")))
                .andExpect(jsonPath("$.to.code", is("RUB")))
                .andExpect(jsonPath("$.rate", is(1.1111)));
    }

    @Test
    @Order(2)
    void shouldConvertCurrency() throws Exception {
        mvc.perform(post("/api/v1/currency-conversion/convert")
                        .content(asJsonString(CurrencyConversionRequest.builder().from("USD").to("RUB").amount(new BigDecimal("0.5")).build()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", is(0.56)));
    }

    @Test
    void shouldReturnErrorWhenCurrencyIsNotSupported() throws Exception {
        mvc.perform(post("/api/v1/currency-conversion/add")
                        .content(asJsonString(UpdateConversionRateRequest.builder().from("GBP").to("RUB").rate(new BigDecimal("1.1111")).build()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    private String asJsonString(Object object) {
        return mapper.writeValueAsString(object);
    }

    private static Currency currency(String usd) {
        return Currency.builder()
                .code(usd)
                .build();
    }
}