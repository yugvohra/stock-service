package com.arctium.stockservice.controller;

import com.arctium.stockservice.service.StockDataAggregatorService;
import com.arctium.stockservice.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private final StockDataAggregatorService stockDataAggregatorService;

    /**
     * Spring injects the @StockDataAggregatorService
     * @param stockDataAggregatorService
     */
    @Autowired
    public StockController(
        final StockDataAggregatorService stockDataAggregatorService) {
        this.stockDataAggregatorService = stockDataAggregatorService;
    }

    /**
     * fetches a stock
     * @param symbol
     * @return Stock
     */
    @GetMapping("/stockservice/{symbol}")
    public Stock getStock(@PathVariable final String symbol) {
        return stockDataAggregatorService.getStockFor(symbol);
    }

}
