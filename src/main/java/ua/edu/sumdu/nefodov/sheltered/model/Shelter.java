package ua.edu.sumdu.nefodov.sheltered.model;

import javax.persistence.*;
import java.util.List;

@Entity
@IdClass(Coordinates.class)
@Table(name = "shelters")
public class Shelter {

    @Id
    private double latitude;

    @Id
    private double longitude;

    @Enumerated(EnumType.STRING)
    private ShelterStatus status;

    @ElementCollection(targetClass = ShelterConditions.class)
    @JoinTable(name = "conditions")
    @Column(name = "conditions")
    @Enumerated(EnumType.STRING)
    private List<ShelterConditions> conditions;

    private int counter;
    private int capacity;
    private double area;
    private String additional;

    public Shelter() {
        counter = 0;
    }

    public static boolean isValidShelter(Shelter shelter) {
        return shelter.getArea() > 0 && shelter.getCapacity() > 0 && shelter.getStatus() != null
                && shelter.getLatitude() >= -90 && shelter.getLatitude() <= 90
                && shelter.getLongitude() >= -180 && shelter.getLongitude() <= 180;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
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

    public List<ShelterConditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<ShelterConditions> conditions) {
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
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", counter=" + counter +
                ", status=" + status +
                ", conditions=" + conditions +
                ", capacity=" + capacity +
                ", area=" + area +
                ", additional='" + additional + '\'' +
                '}';
    }
}
