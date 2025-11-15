package com.turkcell.reservation_services.service;

import com.turkcell.reservation_services.dto.ReservationRequest;
import com.turkcell.reservation_services.dto.ReservationResponse;
import com.turkcell.reservation_services.entity.Reservation;
import com.turkcell.reservation_services.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest req) {
        boolean alreadyWaiting = !reservationRepository
                .findByBookIdAndStatus(req.bookId, Reservation.Status.WAITING)
                .stream()
                .filter(r -> r.getMemberId().equals(req.memberId))
                .toList().isEmpty();
        if (alreadyWaiting)
            throw new IllegalStateException("Bu kitap için zaten aktif rezervasyonunuz mevcut.");
        Reservation reservation = new Reservation();
        reservation.setBookId(req.bookId);
        reservation.setMemberId(req.memberId);
        return mapToResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponse updateStatus(Long reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Rezervasyon bulunamadı!"));
        reservation.setStatus(Reservation.Status.valueOf(status));
        return mapToResponse(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationResponse> getWaitingReservationsByBook(Long bookId) {
        return reservationRepository.findByBookIdAndStatus(bookId, Reservation.Status.WAITING)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getActiveReservationsByMember(Long memberId) {
        return reservationRepository.findByMemberIdAndStatus(memberId, Reservation.Status.WAITING)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private ReservationResponse mapToResponse(Reservation r) {
        ReservationResponse res = new ReservationResponse();
        res.id = r.getId();
        res.bookId = r.getBookId();
        res.memberId = r.getMemberId();
        res.reservationDate = r.getReservationDate();
        res.status = r.getStatus() != null ? r.getStatus().name() : null;
        return res;
    }
}
