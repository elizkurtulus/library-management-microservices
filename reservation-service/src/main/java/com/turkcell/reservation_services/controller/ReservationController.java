package com.turkcell.reservation_services.controller;

import com.turkcell.reservation_services.dto.ReservationRequest;
import com.turkcell.reservation_services.dto.ReservationResponse;
import com.turkcell.reservation_services.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Yeni rezervasyon oluştur
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest req) {
        ReservationResponse response = reservationService.createReservation(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Rezervasyon durumunu değiştir
    @PutMapping("/{reservationId}/status")
    public ResponseEntity<ReservationResponse> updateStatus(@PathVariable Long reservationId,
                                                            @RequestParam String status) {
        return ResponseEntity.ok(reservationService.updateStatus(reservationId, status));
    }

    // Kitabın bekleyen rezervasyonları
    @GetMapping("/queue/{bookId}")
    public List<ReservationResponse> getWaitingReservationsByBook(@PathVariable Long bookId) {
        return reservationService.getWaitingReservationsByBook(bookId);
    }

    // Üyenin bekleyen rezervasyonları
    @GetMapping("/active/member/{memberId}")
    public List<ReservationResponse> getActiveReservationsByMember(@PathVariable Long memberId) {
        return reservationService.getActiveReservationsByMember(memberId);
    }
}

