package com.subscription.notification;

// concrete class
public class EmailNotification implements Notification {

    @Override
    public void send(String recipient, String message) {
        // in a real application here must be our application integrate with a email service
        System.out.printf("Sending EMAIL to %s: \"%s\"\n", recipient, message);
    }
}
