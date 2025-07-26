package com.subscription;

import com.subscription.model.Customer;
import com.subscription.model.Service;
import com.subscription.model.Subscription;
import com.subscription.service.ISubscriptionManager;
import com.subscription.service.impl.SubscriptionManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Subscription Management System...");
        ISubscriptionManager manager = SubscriptionManager.getInstance();

        // --- 1. Define Customers and Services ---
        System.out.println("\n--- Step 1: Creating Customers and Services ---");
        var customer1 = manager.createCustomer("Rahmat Ansari", "rahmat.ansari.dev@gmail.com");
        var customer2 = manager.createCustomer("Zahra Ekramifar", "zahra.ekramifar@gmail.com");

        var service1 = manager.createService("Video Streaming", "Unlimited HD movie streaming.");
        var service2 = manager.createService("Music Streaming", "Ad-free music library.");
        var service3 = manager.createService("Cloud Storage", "1TB of secure cloud storage.");

        // --- 2. Create Subscriptions ---
        System.out.println("\n--- Step 2: Creating Subscriptions ---");
        var sub1 = manager.createSubscription(customer1.id(), service1.id()); // Rahmat -> Video
        var sub2 = manager.createSubscription(customer1.id(), service2.id()); // Rahmat -> Music
        var sub3 = manager.createSubscription(customer2.id(), service1.id()); // Zahra   -> Video
        var sub4 = manager.createSubscription(customer2.id(), service3.id()); // Zahra   -> Cloud

        // --- 3. Reporting: Get All Active Subscriptions ---
        System.out.println("\n--- Step 3: Reporting All Active Subscriptions ---");
        List<Subscription> activeSubscriptions = manager.getAllActiveSubscriptions();
        System.out.printf("Total active subscriptions: %d\n", activeSubscriptions.size());
        activeSubscriptions.forEach(sub -> System.out.println(" -> " + sub));

        // --- 4. Cancel a Subscription ---
        System.out.println("\n--- Step 4: Canceling a Subscription ---");
        manager.cancelSubscription(sub2.getId()); // Cancel Rahmat's Music subscription

        // --- 5. Reporting: Active Services for a Specific Customer ---
        System.out.println("\n--- Step 5: Reporting Active Services for Rahmat ---");
        List<Service> aliceServices = manager.getActiveServicesForCustomer(customer1.id());
        System.out.println("Rahmat's active services:");
        aliceServices.forEach(service -> System.out.println(" -> " + service.name()));

        // --- 6. Reporting: Customers for a Specific Service ---
        System.out.println("\n--- Step 6: Reporting Customers for Video Streaming Service ---");
        List<Customer> videoSubscribers = manager.getCustomersSubscribedToService(service1.id());
        System.out.println("Video Streaming subscribers:");
        videoSubscribers.forEach(customer -> System.out.println(" -> " + customer.name()));

        System.out.println("\nSystem demonstration complete.");
    }
}