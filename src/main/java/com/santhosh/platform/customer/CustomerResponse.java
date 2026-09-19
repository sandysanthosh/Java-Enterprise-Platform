package com.santhosh.platform.customer;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse(UUID id, String fullName, String email, Instant createdAt) {
    static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFullName(),
                customer.getEmail(), customer.getCreatedAt());
    }
}