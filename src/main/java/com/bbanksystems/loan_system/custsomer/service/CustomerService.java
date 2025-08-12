package com.bbanksystems.loan_system.custsomer.service;

import com.bbanksystems.loan_system.custsomer.controlller.CustomerRequestDto;
import com.bbanksystems.loan_system.custsomer.mapper.CustomerRequestMapper;
import com.bbanksystems.loan_system.custsomer.model.Customer;
import com.bbanksystems.loan_system.custsomer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerRequestMapper mapper;

    public Customer create(CustomerRequestDto requestDto) {
        Customer newCustomer = mapper.apply(requestDto);
        return customerRepository.save(newCustomer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    public List<Customer> getListOfCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    public Customer updateCustomerById(Long id, CustomerRequestDto requestDto) {
        Customer customer = getCustomerById(id);

        if (requestDto.getBalance() != null) customer.setBalance(requestDto.getBalance());
        if (requestDto.getCreditScore() != null) customer.setCreditScore(requestDto.getCreditScore());
        if (requestDto.getEmail() != null) customer.setEmail(requestDto.getEmail());
        if (requestDto.getFirstName() != null) customer.setFirstName(requestDto.getFirstName());
        if (requestDto.getLastName() != null) customer.setLastName(requestDto.getLastName());

        return customerRepository.save(customer);
    }
}
