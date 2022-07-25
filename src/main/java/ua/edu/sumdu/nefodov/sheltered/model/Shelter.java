package ua.edu.sumdu.nefodov.sheltered.model;

import javax.persistence.*;

@Entity
@IdClass(Coordinates.class)
@Table(name = "shelters")
public class Shelter {

    @Id
    private double latitude;

    @Id
    private double longitude;

    @Transient
    private int counter;

    @Enumerated(EnumType.STRING)
    private ShelterStatus status;

    private int capacity;
    private double area;
    private boolean food;
    private boolean water;
    private boolean electricity;
    private String additional;

    public Shelter() {
        counter = 0;
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

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public boolean isElectricity() {
        return electricity;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
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
                ", capacity=" + capacity +
                ", area=" + area +
                ", food=" + food +
                ", water=" + water +
                ", electricity=" + electricity +
                ", additional='" + additional + '\'' +
                '}';
    }
}
