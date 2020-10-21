package com.arctium.stockservice.service.alpha;

import com.arctium.stockservice.entities.QuoteData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.client.WebClient.*;


@SpringBootTest
class AlphaStockExchangeServiceTest {
    @MockBean
    private QuoteParser quoteParser;

    @InjectMocks
    @Autowired
    private AlphaStockExchangeService alphaStockExchangeService;

    private final RequestHeadersUriSpec requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
    private final RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
    private final ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
    private final QuoteData expectedQuoteData = QuoteData.builder().name("IBM").price(125.93).dayHigh(126.43).dayLow(124.6550).previousClose(124.89).build();

    @BeforeEach
    public void setUp() {
        when(quoteParser.getStockDataFrom(any())).thenReturn(expectedQuoteData);
    }

    @Test
    public void should_fetch_quote_data_from_alpha() {

        try (MockedStatic<AlphaApiUtils> mocked = mockStatic(AlphaApiUtils.class)) {
            // Arrange
            QuoteData expectedQuoteData = QuoteData.builder().name("IBM").price(125.93).dayHigh(126.43).dayLow(124.6550).previousClose(124.89).build();
            mocked.when(() -> AlphaApiUtils.getQueryURL(anyString(), anyString())).thenReturn("/query");
            ObjectMapper objectMapper = new ObjectMapper();
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
            WebClient mockedClient = mock(WebClient.class);
            when(mockedClient.get())
                    .thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(anyString()))
                    .thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve())
                    .thenReturn(responseSpec);
            when(responseSpec.bodyToMono(JsonNode.class))
                    .thenReturn(Mono.just(response));

            mocked.when(() -> AlphaApiUtils.buildClient(anyString())).thenReturn(mockedClient);

            // Act
            QuoteData resultantQuote = alphaStockExchangeService.getQuoteDataOf("IBM");

            // Assert
            assertEquals(expectedQuoteData, resultantQuote);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}