package com.turkcell.member_services.controller;

import com.turkcell.member_services.dto.MemberRequest;
import com.turkcell.member_services.dto.MemberResponse;
import com.turkcell.member_services.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Üye işlemleri için REST API endpoint'leri
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // Yeni üye oluştur
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest req) {
        MemberResponse response = memberService.createMember(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Üye bilgilerini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id,
            @Valid @RequestBody MemberRequest req) {
        return ResponseEntity.ok(memberService.updateMember(id, req));
    }

    // ID'ye göre üye getir
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    // Üye numarasına göre üye getir
    @GetMapping("/by-member-number/{memberNumber}")
    public ResponseEntity<MemberResponse> getMemberByMemberNumber(@PathVariable String memberNumber) {
        return ResponseEntity.ok(memberService.getMemberByMemberNumber(memberNumber));
    }

    // Tüm üyeleri listele
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // Üyeyi sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}

