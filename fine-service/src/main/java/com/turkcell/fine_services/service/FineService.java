package com.turkcell.fine_services.service;

import com.turkcell.fine_services.dto.request.CreateFineRequest;
import com.turkcell.fine_services.dto.request.UpdateFineRequest;
import com.turkcell.fine_services.dto.response.CreatedFineResponse;
import com.turkcell.fine_services.dto.response.GetAllFineResponse;
import com.turkcell.fine_services.dto.response.GetByIdFineResponse;
import com.turkcell.fine_services.entity.Fine;
import com.turkcell.fine_services.mapper.FineMapper;
import com.turkcell.fine_services.repository.FineRepository;
import com.turkcell.fine_services.rules.FineBusinessRules;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Service layer for managing {@link Fine} entities.
 */
@Service
@Validated
public class FineService {
    private final FineRepository fineRepository;
    private final FineBusinessRules fineBusinessRules;
    private final FineMapper fineMapper;

    public FineService(FineRepository fineRepository, FineBusinessRules fineBusinessRules, FineMapper fineMapper) {
        this.fineRepository = fineRepository;
        this.fineBusinessRules = fineBusinessRules;
        this.fineMapper = fineMapper;
    }

    /**
     * Creates a new fine for a member.
     *
     * @param createFineRequest the request containing fine details
     * @return a {@link CreatedFineResponse} with details of the created fine
     */
    public CreatedFineResponse add(@Valid CreateFineRequest createFineRequest) {
        Fine fine = fineMapper.createFineRequestToFine(createFineRequest);
        fine = fineRepository.save(fine);

        return fineMapper.fineToCreatedFineResponse(fine);
    }

    /**
     * Updates an existing fine.
     *
     * @param updateFineRequest the request containing updated fine details
     */
    public void update(@Valid UpdateFineRequest updateFineRequest) {
        Fine fine = fineRepository.findById(updateFineRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ceza " + updateFineRequest.getId() + " bulunamadı!"));
        fine.setAmount(updateFineRequest.getAmount());
        fine.setPaid(updateFineRequest.isPaid());

        if (updateFineRequest.getMemberId() != null) {
            fine.setMemberId(updateFineRequest.getMemberId());
        }

        fineRepository.save(fine);
    }

    /**
     * Retrieves a fine by its ID.
     *
     * @param id the unique identifier of the fine
     * @return a {@link GetByIdFineResponse} containing details of the found fine
     */
    public GetByIdFineResponse getById(UUID id) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ceza " + id + " bulunamadı!"));

        return fineMapper.fineToGetByIdFineResponse(fine);
    }

    /**
     * Retrieves all fines in the system.
     *
     * @return a list of {@link GetAllFineResponse} objects representing all fines
     */
    public List<GetAllFineResponse> getAll() {
        List<Fine> fineList = fineRepository.findAll();
        return fineMapper.fineListToGetAllFineResponseList(fineList);
    }

    /**
     * Deletes a fine by its ID.
     *
     * @param id the unique identifier of the fine to delete
     */
    public void delete(UUID id) {
        fineRepository.deleteById(id);
    }

    /**
     * Retrieves all fines belonging to a specific member.
     *
     * @param memberId the unique identifier of the member
     * @return a list of {@link GetAllFineResponse} objects for the given member
     */
    public List<GetAllFineResponse> getFinesByMemberId(UUID memberId) {
        List<Fine> fineList = fineRepository.findByMemberId(memberId);
        return fineMapper.fineListToGetAllFineResponseList(fineList);
    }

    /**
     * Marks a fine as paid.
     *
     * @param fineId the unique identifier of the fine
     */
    public void payFine(UUID fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new IllegalArgumentException("Ceza " + fineId + " bulunamadı!"));

        fine.setPaid(true);
        fineRepository.save(fine);
    }

    /**
     * Creates a fine with a fixed amount (e.g., for lost or damaged books).
     *
     * @param memberId    the unique identifier of the member
     * @param fixedAmount the fixed fine amount (must be positive)
     * @param description an optional description of the fine (e.g., reason)
     * @return a {@link CreatedFineResponse} with details of the created fine
     */
    public CreatedFineResponse createFixedFine(UUID memberId, double fixedAmount, String description) {
        // Üye kontrolü

        // Ceza miktarı pozitif olmalı
        fineBusinessRules.fineAmountMustBePositive(fixedAmount);

        // Personel bulma (ilk personeli al)

        // Ceza oluşturma
        Fine fine = new Fine();
        fine.setAmount(fixedAmount);
        fine.setPaid(false);
        fine.setMemberId(memberId);
        // Staff ID ve description alanı eklemek için ileride genişletilebilir

        fine = fineRepository.save(fine);

        return fineMapper.fineToCreatedFineResponse(fine);
    }

    /**
     * Checks whether a member has unpaid fines.
     *
     * @param memberId the unique identifier of the member
     * @return {@code true} if the member has unpaid fines, otherwise {@code false}
     */
    public boolean hasUnpaidFines(UUID memberId) {
        // Member member = memberBusinessRules.memberShouldExistWithGivenId(memberId);
        return fineBusinessRules.hasUnpaidFines(memberId);
    }
}
