package com.arctium.stockservice.service;

import com.arctium.stockservice.entities.QuoteData;

public interface StockExchangeService {
    /**
     * get QuoteData for a stock symbol
     * @param aStockSymbol
     * @return QuoteData
     */
    QuoteData getQuoteDataOf(String aStockSymbol);
}
