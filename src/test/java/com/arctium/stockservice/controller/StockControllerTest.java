package com.arctium.stockservice.controller;

import com.arctium.stockservice.service.StockDataAggregatorService;
import com.arctium.stockservice.entities.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StockDataAggregatorService mockedService;

    private static final String authHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiZXhwIjoxNjQwOTMxNjE2LCJSb2xlcyI6WyJNYW5hZ2VyIiwiUHJvamVjdCBBZG1pbmlzdHJhdG9yIl19.t6iOMeyryV93jEdkkUJXTbXP6DzBEslJ-IneK5sbxfQ";

    private final Stock aTestStock = new Stock("IBM", 100, 101, 101, 99);

    @Test
    public void should_fetch_stock_for_IBM() throws Exception {
        // Arrange
        when(mockedService.getStockFor("IBM")).thenReturn(aTestStock);

        //Act
        this.mockMvc.perform(get("/stockservice/IBM").header("Authorization",authHeader)).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("IBM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.askPrice").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastSoldPrice").value(101.0));

    }
}