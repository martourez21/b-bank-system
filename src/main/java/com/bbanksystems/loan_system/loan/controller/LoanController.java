package com.bbanksystems.loan_system.loan.controller;

import com.bbanksystems.loan_system.loan.model.LoanEntity;
import com.bbanksystems.loan_system.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/{customerId}/loans")
    public ResponseEntity<LoanEntity> requestLoan(@PathVariable Long customerId, @RequestBody LoanRequest request) {
            LoanEntity savedLoan = loanService.processLoanApplication
                    (customerId, request.getAmount(), request.getLoanType());
            return ResponseEntity.ok(savedLoan);
        }

}
