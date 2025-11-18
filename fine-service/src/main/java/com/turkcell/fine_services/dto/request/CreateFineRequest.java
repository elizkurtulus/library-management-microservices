package com.turkcell.fine_services.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateFineRequest {
    @NotNull
    @DecimalMin(value = "0.01", message = "Ceza miktarı 0'dan büyük olmalıdır")
    private double amount;

    @NotNull
    private UUID memberId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }
}
