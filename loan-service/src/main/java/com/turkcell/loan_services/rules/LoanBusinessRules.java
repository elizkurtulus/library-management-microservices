package com.turkcell.loan_services.rules;

import com.turkcell.loan_services.entity.Loan;
import com.turkcell.loan_services.repository.LoanRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class LoanBusinessRules {

    private final LoanRepository loanRepository;

    public LoanBusinessRules(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan loanMustExist(UUID loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Ödünç kaydı " + loanId + " bulunamadı!"));
    }

    public void memberMustNotHaveActiveLoanForBook(UUID memberId, UUID bookId) throws Exception {
        boolean hasActive = loanRepository.findByMemberId(memberId).stream()
                .anyMatch(loan -> !loan.isReturned() && loan.getBookId().equals(bookId));
        if (hasActive) {
            throw new Exception("Üyenin bu kitap için devam eden bir ödünç kaydı var.");
        }
    }

    public void bookMustBeAvailable(UUID bookId) throws Exception {
        if (loanRepository.existsByBookIdAndReturnedFalse(bookId)) {
            throw new Exception("Kitap şu anda ödünç verilemiyor.");
        }
    }

    public void dueDateMustBeInFuture(Date dueDate) throws Exception {
        if (dueDate == null || dueDate.before(new Date())) {
            throw new Exception("İade tarihi gelecekte olmalıdır.");
        }
    }

    public void loanMustNotBeReturned(Loan loan) throws Exception {
        if (loan.isReturned()) {
            throw new Exception("Tamamlanan ödünç işlemi güncellenemez.");
        }
    }
}
