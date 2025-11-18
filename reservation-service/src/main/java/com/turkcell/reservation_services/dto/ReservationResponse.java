package com.turkcell.reservation_services.dto;

import java.time.LocalDateTime;

public class ReservationResponse {
    public Long id;
    public Long bookId;
    public Long memberId;
    public LocalDateTime reservationDate;
    public String status;
}
