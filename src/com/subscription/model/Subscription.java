package com.subscription.model;

import java.time.LocalDate;
import java.util.UUID;

public final class Subscription {
    private final UUID id;
    private final UUID customerId;
    private final UUID serviceId;
    private final LocalDate startDate;
    private SubscriptionStatus status; // Mutable state

    public Subscription(UUID customerId, UUID serviceId) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.startDate = LocalDate.now();
        this.status = SubscriptionStatus.ACTIVE; // Default status is active
    }

    // --- Public Getters for Read-Only Access ---
    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public boolean isActive() {
        return this.status == SubscriptionStatus.ACTIVE;
    }

    public void cancel() {
        this.status = SubscriptionStatus.INACTIVE;
    }

    @Override
    public String toString() {
        return "Subscription[" +
                "id=" + id +
                ", customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", startDate=" + startDate +
                ", status=" + status +
                ']';
    }
}