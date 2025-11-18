package com.turkcell.member_services.service;

import com.turkcell.member_services.dto.MemberRequest;
import com.turkcell.member_services.dto.MemberResponse;
import com.turkcell.member_services.entity.Member;
import com.turkcell.member_services.entity.Member.MemberStatus;
import com.turkcell.member_services.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Üye işlemlerini yürüten service implementasyonu
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public MemberResponse createMember(MemberRequest req) {
        // E-posta adresi zaten varsa hata fırlat
        if (memberRepository.existsByEmail(req.email)) {
            throw new IllegalStateException("Bu e-posta adresine sahip üye zaten mevcut!");
        }

        // Üye numarası zaten varsa hata fırlat
        if (memberRepository.existsByMemberNumber(req.memberNumber)) {
            throw new IllegalStateException("Bu üye numarasına sahip üye zaten mevcut!");
        }

        Member member = new Member();
        member.setMemberNumber(req.memberNumber);
        member.setFirstName(req.firstName);
        member.setLastName(req.lastName);
        member.setEmail(req.email);
        member.setPhoneNumber(req.phoneNumber);
        
        // Status'u parse et, yoksa ACTIVE olarak ayarla
        if (req.status != null && !req.status.isEmpty()) {
            try {
                member.setStatus(MemberStatus.valueOf(req.status.toUpperCase()));
            } catch (IllegalArgumentException e) {
                member.setStatus(MemberStatus.ACTIVE);
            }
        } else {
            member.setStatus(MemberStatus.ACTIVE);
        }

        return mapToResponse(memberRepository.save(member));
    }

    @Override
    @Transactional
    public MemberResponse updateMember(Long id, MemberRequest req) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Üye bulunamadı!"));

        // E-posta güncelleniyorsa kontrol et
        if (req.email != null && !req.email.equals(member.getEmail())) {
            if (memberRepository.existsByEmail(req.email)) {
                throw new IllegalStateException("Bu e-posta adresine sahip başka bir üye zaten mevcut!");
            }
            member.setEmail(req.email);
        }

        // Üye numarası güncelleniyorsa kontrol et
        if (req.memberNumber != null && !req.memberNumber.equals(member.getMemberNumber())) {
            if (memberRepository.existsByMemberNumber(req.memberNumber)) {
                throw new IllegalStateException("Bu üye numarasına sahip başka bir üye zaten mevcut!");
            }
            member.setMemberNumber(req.memberNumber);
        }

        if (req.firstName != null)
            member.setFirstName(req.firstName);
        if (req.lastName != null)
            member.setLastName(req.lastName);
        if (req.phoneNumber != null)
            member.setPhoneNumber(req.phoneNumber);
        if (req.status != null && !req.status.isEmpty()) {
            try {
                member.setStatus(MemberStatus.valueOf(req.status.toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Geçersiz status değeri, mevcut status'u koru
            }
        }

        return mapToResponse(memberRepository.save(member));
    }

    @Override
    public MemberResponse getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Üye bulunamadı!"));
        return mapToResponse(member);
    }

    @Override
    public MemberResponse getMemberByMemberNumber(String memberNumber) {
        Member member = memberRepository.findByMemberNumber(memberNumber)
                .orElseThrow(() -> new IllegalArgumentException("Üye bulunamadı!"));
        return mapToResponse(member);
    }

    @Override
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id))
            throw new IllegalArgumentException("Üye bulunamadı!");
        memberRepository.deleteById(id);
    }

    // Entity'yi Response DTO'ya dönüştür
    private MemberResponse mapToResponse(Member member) {
        MemberResponse res = new MemberResponse();
        res.id = member.getId();
        res.memberNumber = member.getMemberNumber();
        res.firstName = member.getFirstName();
        res.lastName = member.getLastName();
        res.email = member.getEmail();
        res.phoneNumber = member.getPhoneNumber();
        res.status = member.getStatus() != null ? member.getStatus().name() : null;
        res.createdAt = member.getCreatedAt();
        res.updatedAt = member.getUpdatedAt();
        return res;
    }
}

