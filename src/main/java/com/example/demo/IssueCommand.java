package com.example.demo;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

record IssueCommand(@TargetAggregateIdentifier String id, int amount) {
}