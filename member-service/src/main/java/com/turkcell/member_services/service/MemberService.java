package com.turkcell.member_services.service;

import com.turkcell.member_services.dto.MemberRequest;
import com.turkcell.member_services.dto.MemberResponse;

import java.util.List;

// Üye işlemleri için service interface'i
public interface MemberService {
    // Yeni üye oluştur
    MemberResponse createMember(MemberRequest req);

    // Üye bilgilerini güncelle
    MemberResponse updateMember(Long id, MemberRequest req);

    // ID'ye göre üye getir
    MemberResponse getMemberById(Long id);

    // Üye numarasına göre üye getir
    MemberResponse getMemberByMemberNumber(String memberNumber);

    // Tüm üyeleri listele
    List<MemberResponse> getAllMembers();

    // Üyeyi sil
    void deleteMember(Long id);
}

