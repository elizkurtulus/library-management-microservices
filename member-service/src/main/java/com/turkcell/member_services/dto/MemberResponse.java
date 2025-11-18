package com.turkcell.member_services.dto;

import java.time.LocalDateTime;

// Üye bilgilerini döndürmek için response DTO
public class MemberResponse {
    public Long id;
    public String memberNumber;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}

