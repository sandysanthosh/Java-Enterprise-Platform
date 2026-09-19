package com.santhosh.platform.customer;
class DuplicateCustomerException extends RuntimeException {
    DuplicateCustomerException(String email) { super("Customer email already exists: " + email); }
}