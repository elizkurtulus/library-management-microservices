package com.turkcell.member_services.repository;

import com.turkcell.member_services.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Üye işlemleri için CRUD ve özel sorgulu repository interface'i
public interface MemberRepository extends JpaRepository<Member, Long> {
    // E-posta adresine göre üye getir
    Optional<Member> findByEmail(String email);

    // Üye numarasına göre üye getir
    Optional<Member> findByMemberNumber(String memberNumber);

    // E-posta adresinin varlığını kontrol et
    boolean existsByEmail(String email);

    // Üye numarasının varlığını kontrol et
    boolean existsByMemberNumber(String memberNumber);
}

