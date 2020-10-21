package com.arctium.stockservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stock.api")
@Data
public class StockApiConfiguration {
  private String url;
  private String key;
}
