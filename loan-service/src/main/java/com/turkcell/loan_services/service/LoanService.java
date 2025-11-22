package com.turkcell.loan_services.service;

import com.turkcell.loan_services.dto.request.CreateLoanRequest;
import com.turkcell.loan_services.dto.request.UpdateLoanRequest;
import com.turkcell.loan_services.dto.response.CreatedLoanResponse;
import com.turkcell.loan_services.dto.response.GetAllLoanResponse;
import com.turkcell.loan_services.dto.response.GetByIdLoanResponse;
import com.turkcell.loan_services.entity.Loan;
import com.turkcell.loan_services.enums.LoanStatus;
import com.turkcell.loan_services.mapper.LoanMapper;
import com.turkcell.loan_services.repository.LoanRepository;
import com.turkcell.loan_services.rules.LoanBusinessRules;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Validated
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanBusinessRules loanBusinessRules;

    public LoanService(LoanRepository loanRepository,
            LoanMapper loanMapper,
            LoanBusinessRules loanBusinessRules) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.loanBusinessRules = loanBusinessRules;
    }

    public CreatedLoanResponse add(@Valid CreateLoanRequest createLoanRequest) {
        loanBusinessRules.bookMustBeAvailable(createLoanRequest.getBookId());
        loanBusinessRules.memberMustNotHaveActiveLoanForBook(createLoanRequest.getMemberId(),
                createLoanRequest.getBookId());
        loanBusinessRules.dueDateMustBeInFuture(createLoanRequest.getDueDate());

        Loan loan = loanMapper.createLoanRequestToLoan(createLoanRequest);
        loan.setLoanDate(createLoanRequest.getLoanDate() != null ? createLoanRequest.getLoanDate() : new Date());
        loan.setReturnDate(createLoanRequest.getReturnDate());
        loan.setReturned(Boolean.TRUE.equals(createLoanRequest.getReturned()));
        loan.setStatus(createLoanRequest.getStatus() != null ? createLoanRequest.getStatus() : LoanStatus.OPEN);

        Loan saved = loanRepository.save(loan);
        return loanMapper.loanToCreatedLoanResponse(saved);
    }

    public void update(UUID id, @Valid UpdateLoanRequest updateLoanRequest) {
        Loan loan = loanBusinessRules.loanMustExist(id);
        loanBusinessRules.loanMustNotBeReturned(loan);

        if (updateLoanRequest.getMemberId() != null) {
            loan.setMemberId(updateLoanRequest.getMemberId());
        }

        if (updateLoanRequest.getBookId() != null) {
            loanBusinessRules.bookMustBeAvailable(updateLoanRequest.getBookId());
            loan.setBookId(updateLoanRequest.getBookId());
        }

        if (updateLoanRequest.getLoanDate() != null) {
            loan.setLoanDate(updateLoanRequest.getLoanDate());
        }

        if (updateLoanRequest.getDueDate() != null) {
            loanBusinessRules.dueDateMustBeInFuture(updateLoanRequest.getDueDate());
            loan.setDueDate(updateLoanRequest.getDueDate());
        }

        if (updateLoanRequest.getReturnDate() != null) {
            loan.setReturnDate(updateLoanRequest.getReturnDate());
        }

        if (updateLoanRequest.getReturned() != null) {
            loan.setReturned(updateLoanRequest.getReturned());
        }

        if (updateLoanRequest.getStatus() != null) {
            loan.setStatus(updateLoanRequest.getStatus());
        }

        if (Boolean.TRUE.equals(updateLoanRequest.getReturned())) {
            if (updateLoanRequest.getReturnDate() == null) {
                loan.setReturnDate(new Date());
            }
            loan.setStatus(calculateReturnStatus(loan));
        }

        loanRepository.save(loan);
    }

    public GetByIdLoanResponse getById(UUID id) {
        Loan loan = loanBusinessRules.loanMustExist(id);
        return loanMapper.loanToGetByIdLoanResponse(loan);
    }

    public List<GetAllLoanResponse> getAll() {
        List<Loan> loans = loanRepository.findAll();
        return loanMapper.loanListToGetAllLoanResponseList(loans);
    }

    public void delete(UUID id) {
        loanRepository.deleteById(id);
    }

    public List<GetAllLoanResponse> getMemberLoans(UUID memberId) {
        List<Loan> loans = loanRepository.findByMemberId(memberId);
        return loanMapper.loanListToGetAllLoanResponseList(loans);
    }

    public void markAsReturned(UUID id) {
        Loan loan = loanBusinessRules.loanMustExist(id);
        loan.setReturned(true);
        loan.setReturnDate(new Date());
        loan.setStatus(calculateReturnStatus(loan));

        loanRepository.save(loan);
    }

    public boolean memberHasActiveLoans(UUID memberId) {
        return !loanRepository.findByMemberIdAndReturnedFalse(memberId).isEmpty();
    }

    private LoanStatus calculateReturnStatus(Loan loan) {
        if (loan.getReturnDate() != null && loan.getDueDate() != null &&
                loan.getReturnDate().after(loan.getDueDate())) {
            return LoanStatus.LATE;
        }
        return LoanStatus.CLOSED;
    }
}
