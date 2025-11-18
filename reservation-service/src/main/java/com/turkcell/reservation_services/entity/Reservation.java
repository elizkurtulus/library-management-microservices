package com.turkcell.reservation_services.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

// Kitap rezervasyonu işlemlerinin entity modeli
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Kitap ID gereklidir")
    @Column(nullable = false)
    private Long bookId; // Rezervasyonu yapılan kitap ID'si

    @NotNull(message = "Üye ID gereklidir")
    @Column(nullable = false)
    private Long memberId; // Rezervasyon yapan üye ID'si

    private LocalDateTime reservationDate; // Rezervasyonun yapıldığı tarih

    @Enumerated(EnumType.STRING)
    private Status status; // WAITING, NOTIFIED, CANCELLED

    // Rezervasyon durumları
    public enum Status {
        WAITING, // Henüz kitap hazır değil
        NOTIFIED, // Kitap geldi, üyeye bildirildi
        CANCELLED // Rezervasyon iptal edildi
    }

    // Otomatik tarih, varsayılan durum atanır
    @PrePersist
    public void prePersist() {
        if (this.reservationDate == null)
            this.reservationDate = LocalDateTime.now();
        if (this.status == null)
            this.status = Status.WAITING;
    }

    // --- GETTER ve SETTER metodları ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

