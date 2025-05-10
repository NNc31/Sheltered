package com.nefodov.sheltered.shelterservice.service.mapper;

import com.nefodov.sheltered.shared.model.CoordinatesDTO;
import com.nefodov.sheltered.shelterservice.model.Coordinates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper {
    CoordinatesDTO toDTO(Coordinates coordinates);
    Coordinates toEntity(CoordinatesDTO coordinatesDTO);
}
