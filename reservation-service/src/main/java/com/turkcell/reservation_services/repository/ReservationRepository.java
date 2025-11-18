package com.turkcell.reservation_services.repository;

import com.turkcell.reservation_services.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Rezervasyon işlemleri için CRUD ve özel sorgulu repository interface'i
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Verilen kitap için tüm bekleyen rezervasyonlar
    List<Reservation> findByBookIdAndStatus(Long bookId, Reservation.Status status);

    // Üyenin aktif rezervasyonları
    List<Reservation> findByMemberIdAndStatus(Long memberId, Reservation.Status status);
}
