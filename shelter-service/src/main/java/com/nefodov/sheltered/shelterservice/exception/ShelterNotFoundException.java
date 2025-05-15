package com.nefodov.sheltered.shelterservice.exception;

import com.nefodov.sheltered.shared.exception.ShelteredException;
import com.nefodov.sheltered.shelterservice.model.Coordinates;

public class ShelterNotFoundException extends ShelteredException {
    public ShelterNotFoundException(Coordinates coords) {
        super("Shelter not found at coordinates: " + coords.getLatitude() + ", " + coords.getLongitude());
    }
}