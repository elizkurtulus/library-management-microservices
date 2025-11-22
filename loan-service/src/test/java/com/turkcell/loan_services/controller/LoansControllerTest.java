package com.turkcell.loan_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.loan_services.dto.request.CreateLoanRequest;
import com.turkcell.loan_services.dto.response.CreatedLoanResponse;
import com.turkcell.loan_services.dto.response.GetAllLoanResponse;
import com.turkcell.loan_services.dto.response.GetByIdLoanResponse;
import com.turkcell.loan_services.enums.LoanStatus;
import com.turkcell.loan_services.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoansController.class)
class LoansControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void add_Success() throws Exception {
        // Given
        UUID memberId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        CreateLoanRequest request = new CreateLoanRequest();
        request.setMemberId(memberId);
        request.setBookId(bookId);
        request.setDueDate(new Date(System.currentTimeMillis() + 86400000));

        CreatedLoanResponse response = new CreatedLoanResponse();
        response.setId(loanId);
        response.setMemberId(memberId);
        response.setBookId(bookId);
        response.setStatus(LoanStatus.OPEN);

        when(loanService.add(any(CreateLoanRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(loanId.toString()))
                .andExpect(jsonPath("$.memberId").value(memberId.toString()));
    }

    @Test
    void getById_Success() throws Exception {
        // Given
        UUID loanId = UUID.randomUUID();
        GetByIdLoanResponse response = new GetByIdLoanResponse();
        response.setId(loanId);

        when(loanService.getById(loanId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/loans/" + loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loanId.toString()));
    }

    @Test
    void getAll_Success() throws Exception {
        // Given
        when(loanService.getAll()).thenReturn(List.of(new GetAllLoanResponse()));

        // When & Then
        mockMvc.perform(get("/api/v1/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void delete_Success() throws Exception {
        // Given
        UUID loanId = UUID.randomUUID();
        doNothing().when(loanService).delete(loanId);

        // When & Then
        mockMvc.perform(delete("/api/v1/loans/" + loanId))
                .andExpect(status().isNoContent());
    }
}

