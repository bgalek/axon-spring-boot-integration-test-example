package com.example.demo;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RedeemCommand(@TargetAggregateIdentifier String id, int amount){}
