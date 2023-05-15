package ua.edu.sumdu.nefodov.sheltered.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "shelters")
public class Shelter {

    @EmbeddedId
    private Coordinates coordinates;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Статус не може бути пустим")
    private ShelterStatus status;

    @ElementCollection(targetClass = ShelterConditions.class)
    @JoinTable(name = "conditions")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Умови не можуть бути пустими")
    private List<ShelterConditions> conditions;

    @Min(value = 1, message = "Місткість не може бути меншою за 1")
    private int capacity;

    @DecimalMin(value = "0.01", message = "Площа не може бути меншою за 1")
    private double area;

    @Size(max = 250, message = "Додаткова інформація має бути не більше 250 символів")
    private String additional;

    public Shelter() {
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
                ", status=" + status +
                ", conditions=" + conditions +
                ", capacity=" + capacity +
                ", area=" + area +
                ", additional='" + additional + '\'' +
                '}';
    }
}
