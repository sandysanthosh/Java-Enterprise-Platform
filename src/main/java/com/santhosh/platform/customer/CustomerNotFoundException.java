package com.santhosh.platform.customer;
import java.util.UUID;
class CustomerNotFoundException extends RuntimeException {
    CustomerNotFoundException(UUID id) { super("Customer not found: " + id); }
}