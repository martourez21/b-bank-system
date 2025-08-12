package com.bbanksystems.loan_system.custsomer.repository;

import com.bbanksystems.loan_system.custsomer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomersById(Long id);
}
