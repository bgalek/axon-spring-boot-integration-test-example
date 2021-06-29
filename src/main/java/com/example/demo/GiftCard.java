package com.example.demo;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.annotation.Profile;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Profile("command")
@Aggregate(cache = "giftCardCache")
public class GiftCard {

    @AggregateIdentifier
    private String giftCardId;
    private int remainingValue;

    @CommandHandler
    public GiftCard(IssueCommand command) {
        if (command.amount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        apply(new IssuedEvent(command.id(), command.amount()));
    }

    @CommandHandler
    public void handle(RedeemCommand command) {
        if (command.amount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        if (command.amount() > remainingValue) {
            throw new IllegalStateException("amount > remaining value");
        }
        apply(new RedeemedEvent(giftCardId, command.amount()));
    }

    @CommandHandler
    public void handle(CancelCommand command) {
        apply(new CancelEvent(giftCardId));
    }

    @EventSourcingHandler
    public void on(IssuedEvent event) {
        giftCardId = event.id();
        remainingValue = event.amount();
    }

    @EventSourcingHandler
    public void on(RedeemedEvent event) {
        remainingValue -= event.amount();
    }

    @EventSourcingHandler
    public void on(CancelEvent event) {
        remainingValue = 0;
    }

    public GiftCard() {
        // Required by Axon to construct an empty instance to initiate Event Sourcing.
    }
}

