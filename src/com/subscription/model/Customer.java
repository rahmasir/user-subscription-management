package com.subscription.model;

import java.util.UUID;

/**
 * This class uses the Builder Design Pattern for object creation.
 * The Builder pattern is ideal for classes with many attributes, some of which may be optional.
 * It improves readability and allows for the creation of immutable objects.
 */
public final class Customer {

    private final UUID id;
    private final String name;
    private final String email;
    private final String phone; // Optional field
    private final String address; // Optional field

    // Private constructor to be called by the Builder
    private Customer(Builder builder) {
        this.id = UUID.randomUUID();
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    // --- Getters ---
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "Customer[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ']';
    }

    // --- Static nested Builder class ---
    public static class Builder {
        // Required parameters
        private final String name;
        private final String email;

        // Optional parameters - initialized to default values
        private String phone = "";
        private String address = "";

        public Builder(String name, String email) {
            if (name == null || email == null) {
                throw new IllegalArgumentException("Name and email are required.");
            }
            this.name = name;
            this.email = email;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this; // Return the builder for method chaining
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this; // Return the builder for method chaining
        }

        public Customer build() {
            // Creates the final Customer object
            return new Customer(this);
        }
    }
}
