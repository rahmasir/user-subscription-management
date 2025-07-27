package com.subscription;

import com.subscription.model.Customer;
import com.subscription.model.Service;
import com.subscription.service.ISubscriptionManager;
import com.subscription.service.impl.SubscriptionManager;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Integrated Subscription Management System...");
        ISubscriptionManager manager = SubscriptionManager.getInstance();

        // --- Step 1: Create a Customer using the Builder Pattern ---
        System.out.println("\n--- 1. Onboarding a new customer ---");
        var customerBuilder = new Customer.Builder("Rahmat Ansari", "rahmat.ansari.dev@gmail.com")
                .withPhone("+98-903-085-9525")
                .withAddress("123 Tech Lane, Silicon Valley");
        var customer = manager.createCustomer(customerBuilder);

        // --- Step 2: Define a Service ---
        System.out.println("\n--- 2. Defining available services ---");
        Service videoService = manager.createService("Premium Video", "Ad-free 4K video streaming.");
        Service musicService = manager.createService("Music Unlimited", "Lossless audio streaming.");


        // --- Step 3: Customer Subscribes to Services ---
        System.out.println("\n--- 3. Customer subscribes to services ---");
        var videoSubscription = manager.createSubscription(customer.getId(), videoService.id());
        var musicSubscription = manager.createSubscription(customer.getId(), musicService.id());


        // --- Step 4: Generate an Invoice for a Subscription ---
        System.out.println("\n--- 4. Generating a monthly invoice ---");
        var invoice = manager.createInvoiceForSubscription(videoSubscription.getId(), new BigDecimal("15.99"));


        // --- Step 5: Process a Payment for the Invoice ---
        System.out.println("\n--- 5. Processing a customer payment ---");
        manager.processPayment(invoice.getId(), new BigDecimal("15.99"), "Credit Card");


        // --- Step 6: Customer Cancels One Subscription ---
        System.out.println("\n--- 6. Customer cancels their video subscription ---");
        manager.cancelSubscription(videoSubscription.getId());


        // --- Step 7: Verify customer's active services ---
        System.out.println("\n--- 7. Verifying customer's active services ---");
        var activeServices = manager.getActiveServicesForCustomer(customer.getId());
        System.out.printf("%s now has %d active subscription(s).\n", customer.getName(), activeServices.size());
        activeServices.forEach(service -> System.out.println(" -> Active Service: " + service.name()));

        System.out.println("\nSystem workflow demonstration complete!");
    }
}
