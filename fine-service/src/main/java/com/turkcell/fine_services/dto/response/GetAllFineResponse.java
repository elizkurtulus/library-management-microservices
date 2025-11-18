package com.turkcell.fine_services.dto.response;

import java.util.UUID;

public class GetAllFineResponse {

    private UUID id;

    private double amount;

    private boolean paid;

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

