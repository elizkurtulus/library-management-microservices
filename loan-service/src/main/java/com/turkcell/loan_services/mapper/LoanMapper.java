package com.turkcell.loan_services.mapper;

import com.turkcell.loan_services.dto.request.CreateLoanRequest;
import com.turkcell.loan_services.dto.response.CreatedLoanResponse;
import com.turkcell.loan_services.dto.response.GetAllLoanResponse;
import com.turkcell.loan_services.dto.response.GetByIdLoanResponse;
import com.turkcell.loan_services.entity.Loan;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan createLoanRequestToLoan(CreateLoanRequest createLoanRequest);

    CreatedLoanResponse loanToCreatedLoanResponse(Loan loan);

    GetByIdLoanResponse loanToGetByIdLoanResponse(Loan loan);

    List<GetAllLoanResponse> loanListToGetAllLoanResponseList(List<Loan> loans);
}
