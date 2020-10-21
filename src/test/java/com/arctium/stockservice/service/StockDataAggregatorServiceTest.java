package com.arctium.stockservice.service;

import com.arctium.stockservice.entities.QuoteData;
import com.arctium.stockservice.entities.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class StockDataAggregatorServiceTest {

    @MockBean
    private StockExchangeService stockExchangeService;

    @Autowired
    private StockDataAggregatorService subject;

    private final QuoteData testQuoteData = QuoteData.builder().name("IBM").price(125.93).dayHigh(126.43).dayLow(124.6550).previousClose(124.89).build();

    @Test
    public void should_fetch_stock(){
        //Arrange
        when(stockExchangeService.getQuoteDataOf(anyString())).thenReturn(testQuoteData);
        Stock expectedStock = new Stock("IBM", 125.93,124.89,126.43,124.6550);

        //Act
        Stock resultantStock = subject.getStockFor("IBM");

        // Assert
        assertEquals(expectedStock,resultantStock);
    }
}