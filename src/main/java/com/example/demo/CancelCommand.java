package com.example.demo;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

record CancelCommand(@TargetAggregateIdentifier String id, int amount) {
}