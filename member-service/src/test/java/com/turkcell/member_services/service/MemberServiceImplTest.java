package com.turkcell.member_services.service;

import com.turkcell.member_services.dto.MemberRequest;
import com.turkcell.member_services.dto.MemberResponse;
import com.turkcell.member_services.entity.Member;
import com.turkcell.member_services.entity.Member.MemberStatus;
import com.turkcell.member_services.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private MemberRequest memberRequest;
    private Member member;

    @BeforeEach
    void setUp() {
        memberRequest = new MemberRequest();
        memberRequest.memberNumber = "MEM001";
        memberRequest.firstName = "Ahmet";
        memberRequest.lastName = "Yılmaz";
        memberRequest.email = "ahmet@example.com";
        memberRequest.phoneNumber = "5551234567";
        memberRequest.status = "ACTIVE";

        member = new Member();
        member.setId(1L);
        member.setMemberNumber("MEM001");
        member.setFirstName("Ahmet");
        member.setLastName("Yılmaz");
        member.setEmail("ahmet@example.com");
        member.setPhoneNumber("5551234567");
        member.setStatus(MemberStatus.ACTIVE);
        member.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createMember_Success() {
        // Given
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(memberRepository.existsByMemberNumber(anyString())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        MemberResponse response = memberService.createMember(memberRequest);

        // Then
        assertNotNull(response);
        assertEquals("MEM001", response.memberNumber);
        assertEquals("Ahmet", response.firstName);
        assertEquals("ahmet@example.com", response.email);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void createMember_DuplicateEmail_ThrowsException() {
        // Given
        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> memberService.createMember(memberRequest));
        assertEquals("Bu e-posta adresine sahip üye zaten mevcut!", exception.getMessage());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void getMemberById_Success() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // When
        MemberResponse response = memberService.getMemberById(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.id);
        assertEquals("MEM001", response.memberNumber);
    }

    @Test
    void getMemberById_NotFound_ThrowsException() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.getMemberById(1L));
        assertEquals("Üye bulunamadı!", exception.getMessage());
    }

    @Test
    void deleteMember_Success() {
        // Given
        when(memberRepository.existsById(1L)).thenReturn(true);
        doNothing().when(memberRepository).deleteById(1L);

        // When
        memberService.deleteMember(1L);

        // Then
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteMember_NotFound_ThrowsException() {
        // Given
        when(memberRepository.existsById(1L)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.deleteMember(1L));
        assertEquals("Üye bulunamadı!", exception.getMessage());
        verify(memberRepository, never()).deleteById(any());
    }
}

