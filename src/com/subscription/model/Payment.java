package com.subscription.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

// instance creation by setter
public final class Payment {

    private final UUID id;
    private UUID invoiceId;
    private BigDecimal amount;
    private final LocalDateTime paymentDate;
    private String paymentMethod;

    // No-argument constructor.
    public Payment() {
        this.id = UUID.randomUUID();
        this.paymentDate = LocalDateTime.now();
    }

    // --- Setters ---
    public void setInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // --- Getters ---
    public UUID getId() { return id; }
    public UUID getInvoiceId() { return invoiceId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }

    @Override
    public String toString() {
        return "Payment[" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ']';
    }
}
