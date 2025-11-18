package com.turkcell.fine_services.mapper;

import com.turkcell.fine_services.dto.request.CreateFineRequest;
import com.turkcell.fine_services.dto.response.CreatedFineResponse;
import com.turkcell.fine_services.dto.response.GetAllFineResponse;
import com.turkcell.fine_services.dto.response.GetByIdFineResponse;
import com.turkcell.fine_services.entity.Fine;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FineMapper {

    FineMapper INSTANCE = Mappers.getMapper(FineMapper.class);

    @Mapping(target = "paid", constant = "false")
    Fine createFineRequestToFine(@Valid CreateFineRequest createFineRequest);

    CreatedFineResponse fineToCreatedFineResponse(Fine fine);

    GetByIdFineResponse fineToGetByIdFineResponse(Fine fine);

    List<GetAllFineResponse> fineListToGetAllFineResponseList(List<Fine> fineList);
}
