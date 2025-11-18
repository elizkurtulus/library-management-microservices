package com.turkcell.reservation_services.service;

import com.turkcell.reservation_services.dto.ReservationRequest;
import com.turkcell.reservation_services.dto.ReservationResponse;
import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest req);

    ReservationResponse updateStatus(Long reservationId, String status);

    List<ReservationResponse> getWaitingReservationsByBook(Long bookId);

    List<ReservationResponse> getActiveReservationsByMember(Long memberId);
}
