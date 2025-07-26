package com.subscription.service.impl;

import com.subscription.model.Customer;
import com.subscription.model.Service;
import com.subscription.model.Subscription;
import com.subscription.service.ISubscriptionManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class SubscriptionManager implements ISubscriptionManager {

    // Singleton instance
    private static final SubscriptionManager INSTANCE = new SubscriptionManager();

    // In-memory data storage. Using ConcurrentHashMap for thread safety.
    private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();
    private final Map<UUID, Service> services = new ConcurrentHashMap<>();
    private final Map<UUID, Subscription> subscriptions = new ConcurrentHashMap<>();

    // Private constructor to prevent instantiation
    private SubscriptionManager() {}

    // singleton instance
    public static SubscriptionManager getInstance() {
        return INSTANCE;
    }

    @Override
    public Customer createCustomer(String name, String email) {
        var newCustomer = new Customer(UUID.randomUUID(), name, email);
        customers.put(newCustomer.id(), newCustomer);
        System.out.println("Customer created: " + newCustomer.name());
        return newCustomer;
    }

    @Override
    public Service createService(String name, String description) {
        var newService = new Service(UUID.randomUUID(), name, description);
        services.put(newService.id(), newService);
        System.out.println("Service created: " + newService.name());
        return newService;
    }

    @Override
    public Subscription createSubscription(UUID customerId, UUID serviceId) {
        if (!customers.containsKey(customerId) || !services.containsKey(serviceId)) {
            throw new IllegalArgumentException("Invalid customer or service ID.");
        }
        var newSubscription = new Subscription(customerId, serviceId);
        subscriptions.put(newSubscription.getId(), newSubscription);
        System.out.printf("Subscription created for Customer %s to Service %s\n",
                customers.get(customerId).name(), services.get(serviceId).name());
        return newSubscription;
    }

    @Override
    public void cancelSubscription(UUID subscriptionId) {
        var subscription = subscriptions.get(subscriptionId);
        if (subscription != null) {
            subscription.cancel();
            System.out.println("Subscription canceled: " + subscriptionId);
        } else {
            System.out.println("Subscription not found: " + subscriptionId);
        }
    }

    /**
     * .values(): Gets a collection of all subscriptions.
     * .toList(): Collects the results into an immutable list
     */
    @Override
    public List<Subscription> getAllActiveSubscriptions() {
        return subscriptions.values()
                .stream()
                .filter(Subscription::isActive)
                .toList();
    }


    @Override
    public List<Service> getActiveServicesForCustomer(UUID customerId) {
        return subscriptions.values()
                .stream()
                .filter(sub -> sub.getCustomerId().equals(customerId) && sub.isActive())
                .map(sub -> services.get(sub.getServiceId())) // Transform Subscription to Service
                .filter(Objects::nonNull) // Ensure service exists
                .toList();
    }


    @Override
    public List<Customer> getCustomersSubscribedToService(UUID serviceId) {
        return subscriptions.values()
                .stream()
                .filter(sub -> sub.getServiceId().equals(serviceId) && sub.isActive())
                .map(sub -> customers.get(sub.getCustomerId()))
                .filter(Objects::nonNull)
                .toList();
    }

    // read-only
    @Override
    public List<Subscription> getSubscriptions() {
        return List.copyOf(subscriptions.values());
    }
}