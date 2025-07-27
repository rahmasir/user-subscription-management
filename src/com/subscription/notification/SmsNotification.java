package com.subscription.notification;

// Concrete class
public class SmsNotification implements Notification {

    @Override
    public void send(String recipient, String message) {
        // In a real application, this would integrate with an SMS gateway.
        System.out.printf("Sending SMS to %s: \"%s\"\n", recipient, message);
    }
}
