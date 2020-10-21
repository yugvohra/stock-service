package com.arctium.stockservice.service.alpha;

import lombok.experimental.UtilityClass;

@UtilityClass
// CHECKSTYLE:SUPPRESS:HideUtilityClassConstructor
public class AlphaApiConstants {
  static final String QUOTE_FUNCTION = "GLOBAL_QUOTE";
  static final String SYMBOL_PARAM = "symbol";
  static final String API_KEY_PARAM = "apikey";
  static final String QUERY_PATH = "/query";
  static final String QUOTE_KEY = "Global Quote";
  static final String QUOTE_SYMBOL_KEY = "01. symbol";
  static final String QUOTE_HIGH_KEY = "03. high";
  static final String QUOTE_LOW_KEY = "04. low";
  static final String QUOTE_PRICE_KEY = "05. price";
  static final String QUOTE_PREV_CLOSE_KEY = "08. previous close";

}
