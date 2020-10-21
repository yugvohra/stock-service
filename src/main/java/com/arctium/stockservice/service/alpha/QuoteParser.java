package com.arctium.stockservice.service.alpha;

import static com.arctium.stockservice.service.alpha.AlphaApiConstants.QUOTE_HIGH_KEY;
import static com.arctium.stockservice.service.alpha.AlphaApiConstants.QUOTE_KEY;
import static com.arctium.stockservice.service.alpha.AlphaApiConstants.QUOTE_LOW_KEY;
import static com.arctium.stockservice.service.alpha.AlphaApiConstants.QUOTE_PREV_CLOSE_KEY;
import static com.arctium.stockservice.service.alpha.AlphaApiConstants.QUOTE_PRICE_KEY;
import static com.arctium.stockservice.service.alpha.AlphaApiConstants.QUOTE_SYMBOL_KEY;


import com.arctium.stockservice.entities.QuoteData;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class QuoteParser {

  /**
   * Generates QuoteData from JSON response
   * @param aJsonResponse
   * @return QuoteData
   */
  public QuoteData getStockDataFrom(final JsonNode aJsonResponse) {
    JsonNode quoteNode = aJsonResponse.get(QUOTE_KEY);
    return QuoteData.builder().dayHigh(quoteNode.get(QUOTE_HIGH_KEY).asDouble())
        .dayLow(quoteNode.get(QUOTE_LOW_KEY).asDouble())
        .previousClose(quoteNode.get(QUOTE_PREV_CLOSE_KEY).asDouble())
        .price(quoteNode.get(QUOTE_PRICE_KEY).asDouble())
        .name(quoteNode.get(QUOTE_SYMBOL_KEY).asText())
        .build();
  }
}
