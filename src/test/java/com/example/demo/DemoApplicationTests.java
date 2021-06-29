package com.example.demo;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    private FixtureConfiguration<GiftCard> fixture;

    @BeforeEach
    void setUp() {
        this.fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    void passingRedeemCardCommand() {
        fixture.given(new IssuedEvent("cardId", 100))
                .when(new RedeemCommand("cardId", 20))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new RedeemedEvent("cardId", 20));
    }

    @Test
    void failingRedeemCardCommand() {
        fixture.given(new IssuedEvent("cardId", 100))
                .when(new RedeemCommand("cardId", 20))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new RedeemedEvent("cardId", 10));
    }

}
