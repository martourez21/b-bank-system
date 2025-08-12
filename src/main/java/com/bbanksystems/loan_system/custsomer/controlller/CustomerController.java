package com.bbanksystems.loan_system.custsomer.controlller;

import com.bbanksystems.loan_system.custsomer.model.Customer;
import com.bbanksystems.loan_system.custsomer.service.CustomerService;
import com.bbanksystems.loan_system.custsomer.controlller.CustomerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bbank/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        Customer createdCustomer = customerService.create(customerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequestDto request) {
        Customer updatedCustomer = customerService.updateCustomerById(customerId, request);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getListOfCustomers() {
        List<Customer> customers = customerService.getListOfCustomers();
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }
}
