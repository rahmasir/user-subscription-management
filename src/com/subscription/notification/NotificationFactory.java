package com.subscription.notification;

import com.subscription.notification.NotificationType;

/**
 * The Creator (Factory)
 * This class contains the logic to create different types of notifications
 * It decouples the client code from the concrete notification classes
 */
public class NotificationFactory {

    /**
     * The factory method
     * @param type The desired type of notification.
     * @return An instance of a class that implements the Notification interface.
     */
    public static Notification createNotification(NotificationType type) {
        if (type == null) {
            return null;
        }
        return switch (type) {
            case NotificationType.EMAIL -> new EmailNotification();
            case NotificationType.SMS -> new SmsNotification();
            // `default` is not needed because all enum cases are handled.
        };
    }
}
