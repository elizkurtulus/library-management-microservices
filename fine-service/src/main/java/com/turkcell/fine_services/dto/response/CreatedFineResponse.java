package com.turkcell.fine_services.dto.response;

import java.util.UUID;

public class CreatedFineResponse {
    private UUID id;

    private double amount;

    private boolean paid;

    public CreatedFineResponse() {
    }

    public CreatedFineResponse(UUID id, double amount, boolean paid) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
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
}
