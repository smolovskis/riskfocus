package com.riskfocus.currencyconverter.web;

import com.riskfocus.currencyconverter.CurrencyConverterApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@SpringBootTest(webEnvironment = MOCK, classes = CurrencyConverterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:integration-test.properties")
public class BaseIntegrationTest {
}
