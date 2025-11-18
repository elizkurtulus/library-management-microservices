package com.turkcell.fine_services.dto.response;

import java.util.UUID;

public class GetByIdFineResponse {

    private UUID id;

    private double amount;

    private boolean paid;

    private UUID memberId;

    private UUID staffId;

    public GetByIdFineResponse() {
    }

    public GetByIdFineResponse(UUID id, double amount, boolean paid, UUID memberId, UUID staffId) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
        this.memberId = memberId;
        this.staffId = staffId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public UUID getStaffId() {
        return staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }
}

