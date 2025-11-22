package com.turkcell.loan_services.service;

import com.turkcell.loan_services.dto.request.CreateLoanRequest;
import com.turkcell.loan_services.dto.response.CreatedLoanResponse;
import com.turkcell.loan_services.dto.response.GetAllLoanResponse;
import com.turkcell.loan_services.dto.response.GetByIdLoanResponse;
import com.turkcell.loan_services.entity.Loan;
import com.turkcell.loan_services.enums.LoanStatus;
import com.turkcell.loan_services.mapper.LoanMapper;
import com.turkcell.loan_services.repository.LoanRepository;
import com.turkcell.loan_services.rules.LoanBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private LoanBusinessRules loanBusinessRules;

    @InjectMocks
    private LoanService loanService;

    private CreateLoanRequest createLoanRequest;
    private Loan loan;
    private CreatedLoanResponse createdLoanResponse;

    @BeforeEach
    void setUp() {
        UUID memberId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        createLoanRequest = new CreateLoanRequest();
        createLoanRequest.setMemberId(memberId);
        createLoanRequest.setBookId(bookId);
        createLoanRequest.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow

        loan = new Loan();
        loan.setId(loanId);
        loan.setMemberId(memberId);
        loan.setBookId(bookId);
        loan.setLoanDate(new Date());
        loan.setDueDate(createLoanRequest.getDueDate());
        loan.setStatus(LoanStatus.OPEN);

        createdLoanResponse = new CreatedLoanResponse();
        createdLoanResponse.setId(loanId);
        createdLoanResponse.setMemberId(memberId);
        createdLoanResponse.setBookId(bookId);
    }

    @Test
    void add_Success() throws Exception {
        // Given
        when(loanMapper.createLoanRequestToLoan(any(CreateLoanRequest.class))).thenReturn(loan);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.loanToCreatedLoanResponse(any(Loan.class))).thenReturn(createdLoanResponse);
        doNothing().when(loanBusinessRules).bookMustBeAvailable(any(UUID.class));
        doNothing().when(loanBusinessRules).memberMustNotHaveActiveLoanForBook(any(UUID.class), any(UUID.class));
        doNothing().when(loanBusinessRules).dueDateMustBeInFuture(any(Date.class));

        // When
        CreatedLoanResponse response = loanService.add(createLoanRequest);

        // Then
        assertNotNull(response);
        assertEquals(loan.getId(), response.getId());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void getById_Success() {
        // Given
        UUID loanId = UUID.randomUUID();
        GetByIdLoanResponse response = new GetByIdLoanResponse();
        when(loanBusinessRules.loanMustExist(loanId)).thenReturn(loan);
        when(loanMapper.loanToGetByIdLoanResponse(loan)).thenReturn(response);

        // When
        GetByIdLoanResponse result = loanService.getById(loanId);

        // Then
        assertNotNull(result);
        verify(loanBusinessRules, times(1)).loanMustExist(loanId);
    }

    @Test
    void getAll_Success() {
        // Given
        when(loanRepository.findAll()).thenReturn(List.of(loan));
        when(loanMapper.loanListToGetAllLoanResponseList(anyList())).thenReturn(List.of(new GetAllLoanResponse()));

        // When
        List<GetAllLoanResponse> responses = loanService.getAll();

        // Then
        assertNotNull(responses);
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void delete_Success() {
        // Given
        UUID loanId = UUID.randomUUID();
        doNothing().when(loanRepository).deleteById(loanId);

        // When
        loanService.delete(loanId);

        // Then
        verify(loanRepository, times(1)).deleteById(loanId);
    }
}

