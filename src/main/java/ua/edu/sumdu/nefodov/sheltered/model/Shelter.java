package ua.edu.sumdu.nefodov.sheltered.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shelters")
public class Shelter {

    @EmbeddedId
    private Coordinates coordinates;

    @Enumerated(EnumType.STRING)
    private ShelterStatus status;

    @ElementCollection(targetClass = ShelterConditions.class)
    @JoinTable(name = "conditions")
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
                && shelter.getCoordinates().getLatitude() >= -90 && shelter.getCoordinates().getLatitude() <= 90
                && shelter.getCoordinates().getLongitude() >= -180 && shelter.getCoordinates().getLongitude() <= 180;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
                "latitude=" + coordinates.getLatitude() +
                ", longitude=" + coordinates.getLongitude() +
                ", counter=" + counter +
                ", status=" + status +
                ", conditions=" + conditions +
                ", capacity=" + capacity +
                ", area=" + area +
                ", additional='" + additional + '\'' +
                '}';
    }
}
