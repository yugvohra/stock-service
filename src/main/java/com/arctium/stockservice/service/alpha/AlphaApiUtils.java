package com.arctium.stockservice.service.alpha;

import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@UtilityClass
public class AlphaApiUtils {
  static String getQueryURL(final String aSymbol, final String apiKey) {
    return UriComponentsBuilder.fromPath(AlphaApiConstants.QUERY_PATH)
        .queryParam("function", AlphaApiConstants.QUOTE_FUNCTION)
        .queryParam(AlphaApiConstants.SYMBOL_PARAM, aSymbol)
        .queryParam(AlphaApiConstants.API_KEY_PARAM, apiKey)
        .build().toUriString();
  }

  static WebClient buildClient(final String url) {
    return WebClient.builder().baseUrl(url).build();
  }
}
