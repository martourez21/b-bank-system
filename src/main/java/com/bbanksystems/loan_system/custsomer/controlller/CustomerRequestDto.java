package com.bbanksystems.loan_system.custsomer.controlller;


public class CustomerRequestDto {

    private String firstName;
    private String lastName;
    private Double balance;
    private String email;
    private Integer creditScore;

    public CustomerRequestDto() {
    }

    public CustomerRequestDto(String firstName, String lastName, Double balance, String email, Integer creditScore) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.email = email;
        this.creditScore = creditScore;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public String toString() {
        return "CustomerRequestDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                ", creditScore=" + creditScore +
                '}';
    }
}
