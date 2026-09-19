package com.santhosh.platform.customer;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }

    @GetMapping
    public List<CustomerResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable UUID id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse customer = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/customers/" + customer.id())).body(customer);
    }
}