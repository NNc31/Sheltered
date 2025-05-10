package com.nefodov.sheltered.shelterservice.service.mapper;

import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class})
public interface ShelterMapper {

    ShelterDTO toDTO(Shelter shelter);
    Shelter toEntity(ShelterDTO shelterDTO);
}
