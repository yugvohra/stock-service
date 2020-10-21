package com.arctium.stockservice.service.alpha;

import static com.arctium.stockservice.service.alpha.AlphaApiUtils.buildClient;
import static com.arctium.stockservice.service.alpha.AlphaApiUtils.getQueryURL;


import com.arctium.stockservice.configuration.StockApiConfiguration;
import com.arctium.stockservice.entities.QuoteData;
import com.arctium.stockservice.service.StockExchangeService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AlphaStockExchangeService implements StockExchangeService {
  private final StockApiConfiguration stockApiConfiguration;
  private final QuoteParser quoteParser;

  @Autowired
  public AlphaStockExchangeService(final StockApiConfiguration stockApiConfiguration,
                                   final QuoteParser quoteParser) {
    this.stockApiConfiguration = stockApiConfiguration;
    this.quoteParser = quoteParser;
  }

  /**
   * uses alphavintage global service to
   * fetch QuoteData for a stock symbol
   * @param aStockSymbol
   * @return QuoteData
   */
  @Override
  public QuoteData getQuoteDataOf(final String aStockSymbol) {
    WebClient webClient = buildClient(stockApiConfiguration.getUrl());
    // uses blocking http call
    JsonNode response =
        webClient.get()
            .uri(getQueryURL(aStockSymbol, stockApiConfiguration.getKey()))
            .retrieve()
            .bodyToMono(JsonNode.class).block();
    return quoteParser.getStockDataFrom(response);
  }
}
