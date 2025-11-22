package com.turkcell.fine_services.controller;

import com.turkcell.fine_services.dto.request.CreateFineRequest;
import com.turkcell.fine_services.dto.request.UpdateFineRequest;
import com.turkcell.fine_services.dto.response.CreatedFineResponse;
import com.turkcell.fine_services.dto.response.GetAllFineResponse;
import com.turkcell.fine_services.dto.response.GetByIdFineResponse;
import com.turkcell.fine_services.service.FineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fines")
public class FineController {
    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    /**
     * Retrieves all fines belonging to a specific member.
     *
     * @param memberId the unique identifier of the member
     * @return a list of {@link GetAllFineResponse} representing the member's fines
     */
    @GetMapping("/members/{memberId}")
    public List<GetAllFineResponse> getFinesByMemberId(@PathVariable UUID memberId) {
        return fineService.getFinesByMemberId(memberId);
    }

    /**
     * Pays a fine by its ID.
     *
     * @param id the unique identifier of the fine to be paid
     */
    @PostMapping("/{id}/pay")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payFine(@PathVariable UUID id) {
        fineService.payFine(id);
    }

    /**
     * Creates a new fine.
     *
     * @param request the request body containing fine details
     * @return a {@link CreatedFineResponse} representing the created fine
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedFineResponse add(@Valid @RequestBody CreateFineRequest request) {
        return fineService.add(request);
    }

    /**
     * Updates an existing fine.
     *
     * @param request the request body containing updated fine details
     */
    @PutMapping
    public void update(@Valid @RequestBody UpdateFineRequest request) {
        fineService.update(request);
    }

    /**
     * Retrieves a fine by its ID.
     *
     * @param id the unique identifier of the fine
     * @return a {@link GetByIdFineResponse} representing the fine details
     */
    @GetMapping("/{id}")
    public GetByIdFineResponse getById(@PathVariable UUID id) {
        return fineService.getById(id);
    }

    /**
     * Retrieves all fines.
     *
     * @return a list of {@link GetAllFineResponse} representing all fines
     */
    @GetMapping
    public List<GetAllFineResponse> getAll() {
        return fineService.getAll();
    }

    /**
     * Deletes a fine by its ID.
     *
     * @param id the unique identifier of the fine to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        fineService.delete(id);
    }
}
