package com.subscription.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

// normal constructure
public final class Invoice {

    private final UUID id;
    private final UUID customerId;
    private final BigDecimal amount;
    private final LocalDate issueDate;
    private final LocalDate dueDate;

    /**
     * @param customerId The ID of the customer being billed
     * @param amount The amount due
     * @param issueDate The date the invoice was issued
     */
    public Invoice(UUID customerId, BigDecimal amount, LocalDate issueDate) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(14); // Due date is 14 days after issue
    }

    // --- Getters ---
    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return "Invoice[" +
                "id=" + id +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ']';
    }
}
