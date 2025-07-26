package com.subscription.model;

import java.util.UUID;

/**
 * @param id      The unique identifier for the customer
 * @param name    The full name of the customer
 * @param email   The unique email address of the customer
 */
public record Customer(UUID id, String name, String email) {
}