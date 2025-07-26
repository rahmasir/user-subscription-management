package com.subscription.model;

import java.util.UUID;

/**
 * @param id          The unique identifier for the service
 * @param name        The name of the service (e.g., "Premium Video Streaming")
 * @param description A brief description of the service
 */
public record Service(UUID id, String name, String description) {}