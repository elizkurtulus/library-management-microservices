package com.turkcell.member_services.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Üye oluşturma ve güncelleme işlemleri için DTO
public class MemberRequest {
    @NotBlank(message = "Üye numarası gereklidir")
    public String memberNumber;

    @NotBlank(message = "Ad gereklidir")
    public String firstName;

    @NotBlank(message = "Soyad gereklidir")
    public String lastName;

    @NotBlank(message = "E-posta gereklidir")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    public String email;

    public String phoneNumber;

    public String status; // ACTIVE, INACTIVE, BLOCKED
}

