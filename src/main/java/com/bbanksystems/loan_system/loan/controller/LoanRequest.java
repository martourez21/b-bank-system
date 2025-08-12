package com.bbanksystems.loan_system.loan.controller;

public class LoanRequest {

    private Double amount;
    private String loanType;

    public LoanRequest() {
    }

    public LoanRequest(Double amount, String loanType) {
        this.amount = amount;
        this.loanType = loanType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
}
