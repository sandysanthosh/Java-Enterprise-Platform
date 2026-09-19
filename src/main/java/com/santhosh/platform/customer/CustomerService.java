package com.santhosh.platform.customer;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository repository;
    public CustomerService(CustomerRepository repository) { this.repository = repository; }

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(CustomerResponse::from).toList();
    }
    public CustomerResponse findById(UUID id) {
        return repository.findById(id).map(CustomerResponse::from)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateCustomerException(request.email());
        }
        return CustomerResponse.from(repository.save(new Customer(request.fullName(), request.email())));
    }
}