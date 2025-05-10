package com.nefodov.sheltered.shared.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class CoordinatesDTO {

    @Max(value = 90, message = "Широта не може бути більшою за 90")
    @Min(value = -90, message = "Широта не може бути меншою за -90")
    private double latitude;

    @Max(value = 180, message = "Довгота не може бути більшою за 180")
    @Min(value = -180, message = "Довгота не може бути меншою за -180")
    private double longitude;

    public CoordinatesDTO() {

    }

    public CoordinatesDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
