package com.arctium.stockservice.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteData {
  private final String name;
  private final double price;
  private final double previousClose;
  private final double dayHigh;
  private final double dayLow;
}
