package com.turkcell.fine_services.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UpdateFineRequest {

    @NotNull(message = "Fine ID is required")
    private UUID id;

    @NotNull
    @DecimalMin(value = "0.01", message = "Ceza miktarı 0'dan büyük olmalıdır")
    private double amount;

    @NotNull(message = "Ceza ödeme durumu boş olamaz!")
    private boolean paid;

    @NotNull
    private UUID memberId;

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
}
