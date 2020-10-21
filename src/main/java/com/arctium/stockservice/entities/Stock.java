package com.arctium.stockservice.entities;

import lombok.Data;

@Data
public class Stock {
    private final String name;
    private final double askPrice;
    private final double lastSoldPrice;
    private final double dayMaxPrice;
    private final double dayMinPrice;

    /**
     * creates Stock from QuoteData
     * @param quoteData
     * @return Stock
     */
    public static Stock from(final QuoteData quoteData) {
        return new Stock(quoteData.getName(), quoteData.getPrice(),
            quoteData.getPreviousClose(), quoteData.getDayHigh(),
            quoteData.getDayLow());
    }
}
