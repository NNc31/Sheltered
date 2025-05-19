package com.nefodov.sheltered.shared.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public class ShelterDTO {

    private CoordinatesDTO coordinates;

    @NotNull(message = "Статус не може бути пустим")
    private ShelterStatus status;

    @NotNull(message = "Умови не можуть бути пустими")
    private List<ShelterCondition> conditions;

    @Min(value = 1, message = "Місткість не може бути меншою за 1")
    private int capacity;

    @DecimalMin(value = "0.01", message = "Площа не може бути меншою за 1")
    private double area;

    @Size(max = 250, message = "Додаткова інформація має бути не більше 250 символів")
    private String additional;

    public CoordinatesDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesDTO coordinates) {
        this.coordinates = coordinates;
    }

    public ShelterStatus getStatus() {
        return status;
    }

    public void setStatus(ShelterStatus status) {
        this.status = status;
    }

    public List<ShelterCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<ShelterCondition> conditions) {
        this.conditions = conditions;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "ShelterDTO{" +
                "coordinates=" + coordinates +
                ", status=" + status +
                ", conditions=" + conditions +
                ", capacity=" + capacity +
                ", area=" + area +
                ", additional='" + additional + '\'' +
                '}';
    }
}
