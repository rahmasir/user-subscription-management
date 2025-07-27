package com.subscription.service.impl;

import com.subscription.model.*;
import com.subscription.notification.Notification;
import com.subscription.notification.NotificationFactory;
import com.subscription.notification.NotificationType;
import com.subscription.service.ISubscriptionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core class of the system that manages all data and business logic.
 * Design Pattern: Singleton Pattern
 */
public final class SubscriptionManager implements ISubscriptionManager {

    private static final SubscriptionManager INSTANCE = new SubscriptionManager();

    private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();
    private final Map<UUID, Service> services = new ConcurrentHashMap<>();
    private final Map<UUID, Subscription> subscriptions = new ConcurrentHashMap<>();
    private final Map<UUID, Invoice> invoices = new ConcurrentHashMap<>();
    private final Map<UUID, Payment> payments = new ConcurrentHashMap<>();

    private SubscriptionManager() {}

    public static SubscriptionManager getInstance() {
        return INSTANCE;
    }

    @Override
    public Customer createCustomer(Customer.Builder customerBuilder) {
        var newCustomer = customerBuilder.build();
        customers.put(newCustomer.getId(), newCustomer);
        System.out.println("Customer created: " + newCustomer.getName());
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
        Customer customer = customers.get(customerId);
        Service service = services.get(serviceId);
        if (customer == null || service == null) {
            throw new IllegalArgumentException("Invalid customer or service ID.");
        }

        var newSubscription = new Subscription(customerId, serviceId);
        subscriptions.put(newSubscription.getId(), newSubscription);
        System.out.printf("Subscription created for Customer %s to Service %s\n", customer.getName(), service.name());

        // Send notification
        Notification emailNotifier = NotificationFactory.createNotification(NotificationType.EMAIL);
        if (emailNotifier != null) {
            emailNotifier.send(customer.getEmail(), "Welcome! You have successfully subscribed to " + service.name() + ".");
        }
        return newSubscription;
    }

    @Override
    public void cancelSubscription(UUID subscriptionId) {
        var subscription = subscriptions.get(subscriptionId);
        if (subscription != null && subscription.isActive()) {
            subscription.cancel();
            Customer customer = customers.get(subscription.getCustomerId());
            Service service = services.get(subscription.getServiceId());
            System.out.printf("Subscription for %s to %s has been canceled.\n", customer.getName(), service.name());

            // Send notification
            Notification emailNotifier = NotificationFactory.createNotification(NotificationType.EMAIL);
            if (emailNotifier != null) {
                emailNotifier.send(customer.getEmail(), "Your subscription to " + service.name() + " has been canceled.");
            }
        } else {
            System.out.println("⚠Subscription not found or already inactive: " + subscriptionId);
        }
    }

    @Override
    public Invoice createInvoiceForSubscription(UUID subscriptionId, BigDecimal amount) {
        Subscription subscription = subscriptions.get(subscriptionId);
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription not found.");
        }
        Customer customer = customers.get(subscription.getCustomerId());

        var invoice = new Invoice(subscription.getCustomerId(), amount, LocalDate.now());
        invoices.put(invoice.getId(), invoice);
        System.out.printf("Invoice %s created for %s with amount %.2f\n", invoice.getId(), customer.getName(), amount);

        // Send notification
        Notification emailNotifier = NotificationFactory.createNotification(NotificationType.EMAIL);
        if (emailNotifier != null) {
            emailNotifier.send(customer.getEmail(), "A new invoice for your subscription is ready. Amount due: $" + amount);
        }
        return invoice;
    }

    @Override
    public void processPayment(UUID invoiceId, BigDecimal amount, String paymentMethod) {
        Invoice invoice = invoices.get(invoiceId);
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found.");
        }
        Customer customer = customers.get(invoice.getCustomerId());

        var payment = new Payment();
        payment.setInvoiceId(invoiceId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payments.put(payment.getId(), payment);
        System.out.printf("Payment of %.2f for invoice %s received from %s via %s.\n", amount, invoiceId, customer.getName(), paymentMethod);

        // Send notification
        Notification smsNotifier = NotificationFactory.createNotification(NotificationType.SMS);
        if (smsNotifier != null && customer.getPhone() != null && !customer.getPhone().isEmpty()) {
            smsNotifier.send(customer.getPhone(), "Your payment of $" + amount + " was successful. Thank you!");
        }
    }

    @Override
    public List<Subscription> getAllActiveSubscriptions() {
        return subscriptions.values().stream().filter(Subscription::isActive).toList();
    }

    @Override
    public List<Service> getActiveServicesForCustomer(UUID customerId) {
        return subscriptions.values().stream()
                .filter(sub -> sub.getCustomerId().equals(customerId) && sub.isActive())
                .map(sub -> services.get(sub.getServiceId()))
                .filter(Objects::nonNull).toList();
    }

    @Override
    public List<Customer> getCustomersSubscribedToService(UUID serviceId) {
        return subscriptions.values().stream()
                .filter(sub -> sub.getServiceId().equals(serviceId) && sub.isActive())
                .map(sub -> customers.get(sub.getCustomerId()))
                .filter(Objects::nonNull).toList();
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return List.copyOf(subscriptions.values());
    }
}
