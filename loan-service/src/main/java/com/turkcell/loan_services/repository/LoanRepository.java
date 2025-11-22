package com.turkcell.loan_services.repository;

import com.turkcell.loan_services.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByMemberId(UUID memberId);

    boolean existsByBookIdAndReturnedFalse(UUID bookId);

    List<Loan> findByMemberIdAndReturnedFalse(UUID memberId);
}
