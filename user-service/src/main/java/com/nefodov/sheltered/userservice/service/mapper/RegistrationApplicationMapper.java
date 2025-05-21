package com.nefodov.sheltered.userservice.service.mapper;

import com.nefodov.sheltered.shared.model.RegistrationApplicationDTO;
import com.nefodov.sheltered.userservice.model.RegistrationApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationApplicationMapper {
    RegistrationApplicationDTO toDTO(RegistrationApplication registrationApplication);
    RegistrationApplication toEntity(RegistrationApplicationDTO registrationApplicationDTO);
}
