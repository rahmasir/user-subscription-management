package com.subscription.notification;

public interface Notification {
    void send(String recipient, String message);
}
