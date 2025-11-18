package com.turkcell.member_services.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

// Kütüphane üyesi bilgilerini tutan entity modeli
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Üye numarası gereklidir")
    @Column(unique = true, nullable = false)
    private String memberNumber; // Benzersiz üye numarası

    @NotBlank(message = "Ad gereklidir")
    @Column(nullable = false)
    private String firstName; // Üyenin adı

    @NotBlank(message = "Soyad gereklidir")
    @Column(nullable = false)
    private String lastName; // Üyenin soyadı

    @NotBlank(message = "E-posta gereklidir")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Column(unique = true, nullable = false)
    private String email; // Üyenin e-posta adresi

    private String phoneNumber; // Üyenin telefon numarası

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status = MemberStatus.ACTIVE; // Üyenin durumu

    private LocalDateTime createdAt; // Üyenin kayıt tarihi

    private LocalDateTime updatedAt; // Üyenin güncellenme tarihi

    // Kayıt oluşturulurken tarih atanır
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();
        if (this.status == null)
            this.status = MemberStatus.ACTIVE;
    }

    // Kayıt güncellenirken tarih güncellenir
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- GETTER ve SETTER metodları ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Üye durumu enum'u
    public enum MemberStatus {
        ACTIVE,    // Aktif üye
        INACTIVE,  // Pasif üye
        BLOCKED    // Engellenmiş üye
    }
}

