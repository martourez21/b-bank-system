package com.bbanksystems.loan_system.loan.service;

import com.bbanksystems.loan_system.custsomer.model.Customer;
import com.bbanksystems.loan_system.custsomer.repository.CustomerRepository;
import com.bbanksystems.loan_system.loan.model.LoanEntity;
import com.bbanksystems.loan_system.loan.repository.LoanRepository;
import com.drools.accountmanagement.loan.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanService.class);

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final KieServicesClient kieServicesClient;

    @Value("${kie.server.containerId}")
    private String containerId;

    @Value("${kie.server.ksessionName}")
    private String ksessionName;

    public LoanEntity processLoanApplication(Long customerId, Double requestedAmount, String loanType) {
        log.info("Starting loan process for customerId={}, amount={}, loanType={}", customerId, requestedAmount, loanType);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer with id {} not found", customerId);
                    return new RuntimeException("Customer not found");
                });

        log.debug("Customer found: {}", customer);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicantName(customer.getFirstName() + " " + customer.getLastName());
        loanApplication.setCreditScore(customer.getCreditScore());
        loanApplication.setIncome(customer.getBalance());
        loanApplication.setLoanAmount(requestedAmount);

        log.debug("LoanApplication fact before rules: {}", loanApplication);

        LoanApplication evaluatedLoan = evaluateLoan(loanApplication);

        log.debug("LoanApplication after Drools evaluation: {}", evaluatedLoan);

        LoanEntity loanEntity = LoanEntity.builder()
                .amount(requestedAmount)
                .loanType(loanType)
                .customerId(customerId)
                .status(evaluatedLoan.getStatus())
                .decision(evaluatedLoan.getDecision())
                .build();

        LoanEntity savedEntity = loanRepository.save(loanEntity);

        log.info("LoanEntity saved with id={}, status={}, message={}", savedEntity.getId(), savedEntity.getStatus(), savedEntity.getDecision());

        return savedEntity;
    }

    private LoanApplication evaluateLoan(LoanApplication loanFact) {
        log.info("Calling KIE server to evaluate loan fact...");

        RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        List<Command<?>> commands = new ArrayList<>();
        commands.add(CommandFactory.newInsert(loanFact, "loanFactOut"));
        commands.add(CommandFactory.newFireAllRules());

        BatchExecutionCommand batchCommand = CommandFactory.newBatchExecution(commands, ksessionName);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(containerId, batchCommand);

        if (response.getType() != ServiceResponse.ResponseType.SUCCESS) {
            log.error("KIE server returned error: {}", response.getMsg());
            throw new RuntimeException("Error executing rules: " + response.getMsg());
        }

        LoanApplication result = (LoanApplication) response.getResult().getValue("loanFactOut");

        if (result == null) {
            log.error("No LoanApplication returned from KIE server");
            throw new RuntimeException("No LoanApplication returned from KIE server");
        }

        log.info("Received evaluated LoanApplication from KIE server: {}", result);

        return result;
    }
}
