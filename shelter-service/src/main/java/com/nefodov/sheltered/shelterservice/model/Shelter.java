package com.nefodov.sheltered.shelterservice.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shelters")
public class Shelter {

    @EmbeddedId
    private Coordinates coordinates;

    @Enumerated(EnumType.STRING)
    private ShelterStatus status;

    @ElementCollection(targetClass = ShelterCondition.class)
    @JoinTable(name = "conditions")
    @Enumerated(EnumType.STRING)
    private List<ShelterCondition> conditions;

    private int capacity;
    private double area;
    private String additional;

    public Shelter() {
    }

    public Shelter(Coordinates coordinates, ShelterStatus status, List<ShelterCondition> conditions, int capacity, double area, String additional) {
        this.coordinates = coordinates;
        this.status = status;
        this.conditions = conditions;
        this.capacity = capacity;
        this.area = area;
        this.additional = additional;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "latitude=" + coordinates.getLatitude() +
                ", longitude=" + coordinates.getLongitude() +
                ", status=" + status +
                ", conditions=" + conditions +
                ", capacity=" + capacity +
                ", area=" + area +
                ", additional='" + additional + '\'' +
                '}';
    }
}
