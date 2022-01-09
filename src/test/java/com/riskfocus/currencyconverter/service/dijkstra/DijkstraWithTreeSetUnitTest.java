package com.riskfocus.currencyconverter.service.dijkstra;

import com.google.common.graph.ImmutableValueGraph.Builder;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.riskfocus.currencyconverter.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockitoExtension.class)
class DijkstraWithTreeSetUnitTest {

    private static final Currency USD = new Currency(1L, "USD");
    private static final Currency RUB = new Currency(2L, "RUB");
    private static final Currency EUR = new Currency(3L, "RUB");
    private static final Currency LVL = new Currency(3L, "LVL");

    @InjectMocks
    private ShortestPathCalculator shortestPathCalculator;

    @Test
    void shouldUseDirectConversion() {
        Builder<Currency, BigDecimal> graph = ValueGraphBuilder.directed().immutable();
        graph.putEdgeValue(USD, LVL, new BigDecimal("1.1111"));
        ValueGraph<Currency, BigDecimal> sampleGraph = graph.build();

        BigDecimal conversionRate = shortestPathCalculator.getBestConversionRate(sampleGraph, USD, LVL);

        assertThat(conversionRate, is(new BigDecimal("1.1111")));
    }

    @Test
    void shouldNotSupportReverseConversion() {
        Builder<Currency, BigDecimal> graph = ValueGraphBuilder.directed().immutable();
        graph.putEdgeValue(USD, LVL, new BigDecimal("1.1111"));
        ValueGraph<Currency, BigDecimal> sampleGraph = graph.build();

        BigDecimal conversionRate = shortestPathCalculator.getBestConversionRate(sampleGraph, LVL, USD);

        assertThat(conversionRate, is(nullValue()));
    }


    @Test
    void shouldConvertWhenDirectConversionIsNotPresent() {
        Builder<Currency, BigDecimal> graph = ValueGraphBuilder.directed().immutable();
        graph.putEdgeValue(USD, RUB, new BigDecimal("0.5"));
        graph.putEdgeValue(RUB, EUR, new BigDecimal("0.5"));
        ValueGraph<Currency, BigDecimal> sampleGraph = graph.build();

        BigDecimal conversionRate = shortestPathCalculator.getBestConversionRate(sampleGraph, USD, EUR);

        assertThat(conversionRate, is(new BigDecimal("0.25")));
    }

    @Test
    void shouldReturnNullWhenPathIsNotFound() {
        Builder<Currency, BigDecimal> graph = ValueGraphBuilder.directed().immutable();
        graph.putEdgeValue(USD, RUB, new BigDecimal("0.5"));
        ValueGraph<Currency, BigDecimal> sampleGraph = graph.build();

        BigDecimal conversionRate = shortestPathCalculator.getBestConversionRate(sampleGraph, USD, EUR);

        assertThat(conversionRate, is(nullValue()));
    }

    @Test
    void shouldOptimizeForBestConversionRate() {
        Builder<Currency, BigDecimal> graph = ValueGraphBuilder.directed().immutable();
        graph.putEdgeValue(USD, RUB, new BigDecimal("0.5"));
        graph.putEdgeValue(RUB, EUR, new BigDecimal("0.5"));
        graph.putEdgeValue(USD, EUR, new BigDecimal("0.1"));
        ValueGraph<Currency, BigDecimal> sampleGraph = graph.build();

        BigDecimal conversionRate = shortestPathCalculator.getBestConversionRate(sampleGraph, USD, EUR);

        assertThat(conversionRate, is(new BigDecimal("0.25")));
    }
}