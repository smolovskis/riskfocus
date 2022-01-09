package com.riskfocus.currencyconverter.service.dijkstra;

import com.google.common.graph.ValueGraph;
import com.riskfocus.currencyconverter.model.Currency;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
@SuppressWarnings("UnstableApiUsage")
public class ShortestPathCalculator {

    public BigDecimal getBestConversionRate(ValueGraph<Currency, BigDecimal> graph, Currency source, Currency target) {
        CumulativeConversionRateHolder conversionRateHolder = new CumulativeConversionRateHolder(source);
        Map<Currency, CumulativeConversionRateHolder> nodeWrappers = createCumulativeCurrencyConversionState(source, conversionRateHolder);
        Queue<CumulativeConversionRateHolder> queue = createProcessingQueue(conversionRateHolder);

        while (!queue.isEmpty()) {
            CumulativeConversionRateHolder currentNode = queue.poll();

            // To support reverse conversion use graph.predecessors and graph.adjacentNodes
            for (Currency neighbor : graph.successors(currentNode.getNode())) {
                BigDecimal totalConversionRate = calculateCumulativeConversionRateForCurrentNode(graph, currentNode, neighbor);
                CumulativeConversionRateHolder currentCumulativeNode = nodeWrappers.get(neighbor);

                if (currentCumulativeNode == null) {
                    currentCumulativeNode = new CumulativeConversionRateHolder(neighbor, totalConversionRate, currentNode);
                    nodeWrappers.put(neighbor, currentCumulativeNode);
                    queue.add(currentCumulativeNode);
                } else if (totalConversionRate.compareTo(currentCumulativeNode.getTotalConversionRate()) > 0) {
                    currentCumulativeNode.setTotalConversionRate(totalConversionRate);
                    currentCumulativeNode.setPredecessor(currentNode);
                }
            }
        }

        return nodeWrappers.containsKey(target) ? nodeWrappers.get(target).getTotalConversionRate() : null;
    }

    private Map<Currency, CumulativeConversionRateHolder> createCumulativeCurrencyConversionState(Currency source,
                CumulativeConversionRateHolder sourceWrapper) {
        Map<Currency, CumulativeConversionRateHolder> nodeWrappers = new HashMap<>();
        nodeWrappers.put(source, sourceWrapper);
        return nodeWrappers;
    }

    private Queue<CumulativeConversionRateHolder> createProcessingQueue(CumulativeConversionRateHolder sourceWrapper) {
        Queue<CumulativeConversionRateHolder> queue = new PriorityQueue<>();
        queue.add(sourceWrapper);
        return queue;
    }

    private BigDecimal calculateCumulativeConversionRateForCurrentNode(ValueGraph<Currency, BigDecimal> graph,
                CumulativeConversionRateHolder nodeWrapper, Currency neighbor) {
        return graph.edgeValue(nodeWrapper.getNode(), neighbor)
                .map(nodeWrapper.getTotalConversionRate()::multiply)
                .orElseThrow(IllegalStateException::new);
    }
}

