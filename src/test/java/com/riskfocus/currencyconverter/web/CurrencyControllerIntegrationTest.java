package com.riskfocus.currencyconverter.web;

import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = BEFORE_CLASS)
class CurrencyControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private CurrencyRepository repository;

    @Test
    void shouldReturnUsdSupportedCurrency() throws Exception {
        repository.save(currency("USD"));

        mvc.perform(get("/api/v1/currency").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].code", hasItem("USD")));
    }

    @Test
    void shouldAddEurSupportedCurrency() throws Exception {
        mvc.perform(post("/api/v1/currency/EUR").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("EUR")));

        Currency usd = repository.findCurrencyByCode("EUR").orElseThrow();
        assertThat(usd.getCode(), is("EUR"));
    }

    @Test
    void shouldDeleteEurSupportedCurrency() throws Exception {
        Currency entity = repository.save(currency("GBP"));

        mvc.perform(delete("/api/v1/currency/" + entity.getId()).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Currency> gbp = repository.findCurrencyByCode("GBP");
        assertThat(gbp.isPresent(), is(false));
    }

    private static Currency currency(String code) {
        return Currency.builder()
                .code(code)
                .build();
    }
}