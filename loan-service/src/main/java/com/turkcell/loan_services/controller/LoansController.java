package com.turkcell.loan_services.controller;

import com.turkcell.loan_services.dto.request.CreateLoanRequest;
import com.turkcell.loan_services.dto.request.UpdateLoanRequest;
import com.turkcell.loan_services.dto.response.CreatedLoanResponse;
import com.turkcell.loan_services.dto.response.GetAllLoanResponse;
import com.turkcell.loan_services.dto.response.GetByIdLoanResponse;
import com.turkcell.loan_services.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/loans")
@RestController
public class LoansController {

    private final LoanService loanService;

    public LoansController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedLoanResponse add(@Valid @RequestBody CreateLoanRequest request) {
        return loanService.add(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id, @Valid @RequestBody UpdateLoanRequest request) {
        loanService.update(id, request);
    }

    @GetMapping("/{id}")
    public GetByIdLoanResponse getById(@PathVariable UUID id) {
        return loanService.getById(id);
    }

    @GetMapping
    public List<GetAllLoanResponse> getAll() {
        return loanService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        loanService.delete(id);
    }

    @GetMapping("/members/{memberId}")
    public List<GetAllLoanResponse> getMemberLoans(@PathVariable UUID memberId) {
        return loanService.getMemberLoans(memberId);
    }

    @PostMapping("/{id}/return")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAsReturned(@PathVariable UUID id) {
        loanService.markAsReturned(id);
    }
}
