package com.turkcell.loan_services.dto.request;

import com.turkcell.loan_services.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class UpdateLoanRequest {

    @NotNull
    private UUID id;

    private UUID memberId;

    private UUID bookId;

    private Date loanDate;

    @NotNull
    private Date dueDate;

    private Date returnDate;

    @NotNull
    private Boolean returned;

    @NotNull
    private LoanStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
