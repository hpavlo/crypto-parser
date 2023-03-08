package com.example.cryptoparser.service.impl;

import com.example.cryptoparser.model.Price;
import com.example.cryptoparser.repository.PriceRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DataMongoTest
class RequestApiServiceImplTest {
    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private PriceRepository priceRepository;
    private RequestApiServiceImpl requestApiService;

    @BeforeEach
    void beforeEach() {
        requestApiService = new RequestApiServiceImpl(priceRepository);
        ReflectionTestUtils.setField(requestApiService, "externalApiUrl", "https://cex.io/");
        requestApiService.generateCexioService();
    }

    @Test
    void loadExternalPricesToDbContainer() {
        requestApiService.loadExternalPrices();
        List<Price> prices = priceRepository.findAll();
        prices.forEach(price -> {
            Assertions.assertNotNull(price.getLastPrice());
            Assertions.assertNotNull(price.getFirstCurrency());
            Assertions.assertNotNull(price.getSecondCurrency());
            Assertions.assertNotNull(price.getTime());
        });
    }
}