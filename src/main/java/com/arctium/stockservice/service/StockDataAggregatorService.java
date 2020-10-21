package com.arctium.stockservice.service;

import com.arctium.stockservice.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockDataAggregatorService {
  private final StockExchangeService stockExchangeService;

  @Autowired
  public StockDataAggregatorService(final StockExchangeService stockExchangeService) {
    this.stockExchangeService = stockExchangeService;
  }

    /**
     * fetches stock for a stock symbol
     * @param aSymbol
     * @return Stock
     */
  public Stock getStockFor(final String aSymbol) {
    return Stock.from(stockExchangeService.getQuoteDataOf(aSymbol));
  }
}
