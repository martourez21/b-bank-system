package com.bbanksystems.loan_system.loan.mapper;

import com.bbanksystems.loan_system.loan.controller.LoanRequest;
import com.bbanksystems.loan_system.loan.model.LoanEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LoanMapper implements Function<LoanRequest, LoanEntity> {

    @Override
    public LoanEntity apply(LoanRequest loan) {
        return LoanEntity.builder()
                .amount(loan.getAmount())
                .decision(loan.getLoanType())
                .build();
    }
}
