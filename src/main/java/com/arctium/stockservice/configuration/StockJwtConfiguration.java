package com.arctium.stockservice.configuration;import lombok.Data;import org.springframework.boot.context.properties.ConfigurationProperties;import org.springframework.context.annotation.Configuration;@Configuration@ConfigurationProperties(prefix = "stock.jwt")@Datapublic class StockJwtConfiguration {    private String key;}