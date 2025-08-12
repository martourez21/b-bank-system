package com.bbanksystems.loan_system.loan.repository;

import com.bbanksystems.loan_system.loan.model.LoanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<LoanEntity, Long> {

}
