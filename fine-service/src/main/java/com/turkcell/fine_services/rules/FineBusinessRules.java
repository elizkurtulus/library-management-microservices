package com.turkcell.fine_services.rules;

import com.turkcell.fine_services.entity.Fine;
import com.turkcell.fine_services.repository.FineRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FineBusinessRules {
    private final FineRepository fineRepository;

    public FineBusinessRules(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    /**
     * Ceza miktarının pozitif olması kuralı.
     */
    public void fineAmountMustBePositive(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Ceza miktarı pozitif olmalıdır!");
    }

    /**
     * Ceza kimliğine göre cezanın bulunması kuralı
     */
    public Fine fineMustExist(UUID fineId) {
        return fineRepository
                .findById(fineId)
                .orElseThrow(() -> new IllegalArgumentException("Ceza " + fineId + " bulunamadı!"));
    }

    /**
     * Ceza ödendiğinde güncellenemez kuralı
     */
    public void fineMustNotBePaidToUpdate(UUID fineId) {
        Fine fine = fineRepository
                .findById(fineId)
                .orElseThrow(() -> new IllegalArgumentException("Ceza " + fineId + " bulunamadı!"));

        if (fine.isPaid())
            throw new IllegalStateException("Ödenmiş ceza güncellenemez!");
    }

    /**
     * Üyenin ödenmemiş cezası olup olmadığını kontrol eder
     *
     * @param memberId Kontrol edilecek üye
     * @return true eğer ödenmemiş ceza varsa, aksi halde false
     */
    public boolean hasUnpaidFines(UUID memberId) {
        List<Fine> fines = fineRepository.findByMemberId(memberId);
        return fines.stream().anyMatch(fine -> !fine.isPaid());
    }

    /**
     * Üyenin ödenmemiş cezası varsa hata fırlatır
     *
     * @param memberId Kontrol edilecek üye
     */
    public void memberMustNotHaveUnpaidFines(UUID memberId) {
        if (hasUnpaidFines(memberId)) {
            throw new IllegalStateException("Üyenin ödenmemiş cezası bulunduğu için işlem yapılamaz!");
        }
    }
}
