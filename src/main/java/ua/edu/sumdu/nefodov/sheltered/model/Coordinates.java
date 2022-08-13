package ua.edu.sumdu.nefodov.sheltered.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Coordinates implements Serializable {

    @Max(value = 90, message = "Широта не може бути більшою за 90")
    @Min(value = -90, message = "Широта не може бути меншою за -90")
    private double latitude;

    @Max(value = 180, message = "Довгота не може бути більшою за 180")
    @Min(value = -180, message = "Довгота не може бути меншою за -180")
    private double longitude;

    public Coordinates() {

    }

    public Coordinates(double latitude, double longitude) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
