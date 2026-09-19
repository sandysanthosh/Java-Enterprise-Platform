package com.santhosh.platform.customer;
public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException(String email) { super("Customer email already exists: " + email); }
}