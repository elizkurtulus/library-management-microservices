package com.turkcell.reservation_services.dto;

import jakarta.validation.constraints.NotNull;

// Reservation POST işlemleri için sıralanmış DTO
public class ReservationRequest {
    @NotNull(message = "Kitap ID gereklidir")
    public Long bookId;
    @NotNull(message = "Üye ID gereklidir")
    public Long memberId;
}
