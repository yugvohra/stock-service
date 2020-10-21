package com.arctium.stockservice.service.alpha;

import com.arctium.stockservice.entities.QuoteData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuoteParserTest {

    @Test
    public void should_parse_response_into_quoteData() throws JsonProcessingException {
        //Arrange
        QuoteParser quoteParser = new QuoteParser();
        ObjectMapper objectMapper = new ObjectMapper();

        QuoteData expectedQuoteData = QuoteData.builder().name("IBM").price(125.93).dayHigh(126.43).dayLow(124.6550).previousClose(124.89).build();
        String jsonString = "{\n" +
                "    \"Global Quote\": {\n" +
                "        \"01. symbol\": \"IBM\",\n" +
                "        \"02. open\": \"125.1700\",\n" +
                "        \"03. high\": \"126.4300\",\n" +
                "        \"04. low\": \"124.6550\",\n" +
                "        \"05. price\": \"125.9300\",\n" +
                "        \"06. volume\": \"4714320\",\n" +
                "        \"07. latest trading day\": \"2020-10-16\",\n" +
                "        \"08. previous close\": \"124.8900\",\n" +
                "        \"09. change\": \"1.0400\",\n" +
                "        \"10. change percent\": \"0.8327%\"\n" +
                "    }\n" +
                "}";
        JsonNode response = objectMapper.readTree(jsonString);

        //Act & Assert
        assertEquals(quoteParser.getStockDataFrom(response), expectedQuoteData);
    }

}