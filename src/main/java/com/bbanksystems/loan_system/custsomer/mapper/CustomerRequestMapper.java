package com.bbanksystems.loan_system.custsomer.mapper;

import com.bbanksystems.loan_system.custsomer.controlller.CustomerRequestDto;
import com.bbanksystems.loan_system.custsomer.model.Customer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerRequestMapper implements Function<CustomerRequestDto, Customer> {

    @Override
    public Customer apply(CustomerRequestDto customerRequestDto) {
        return Customer.builder()
                .balance(customerRequestDto.getBalance())
                .creditScore(customerRequestDto.getCreditScore())
                .email(customerRequestDto.getEmail())
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .build();
    }
}
