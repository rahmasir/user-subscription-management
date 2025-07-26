package com.subscription.service;

import com.subscription.model.Customer;
import com.subscription.model.Service;
import com.subscription.model.Subscription;

import java.util.List;
import java.util.UUID;

public interface ISubscriptionManager {

    // --- Management Methods ---
    Customer createCustomer(String name, String email);
    Service createService(String name, String description);
    Subscription createSubscription(UUID customerId, UUID serviceId);
    void cancelSubscription(UUID subscriptionId);

    // --- Reporting Methods ---
    List<Subscription> getAllActiveSubscriptions();
    List<Service> getActiveServicesForCustomer(UUID customerId);
    List<Customer> getCustomersSubscribedToService(UUID serviceId);
    List<Subscription> getSubscriptions();
}