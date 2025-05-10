package com.nefodov.sheltered.supplyservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "supply_requests")
public class SupplyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull(message = "Заявка повинна мати назву")
    private String name;

    @NotNull(message = "Заявка повинна мати опис")
    private String description;

    @Column(name = "shelter_latitude")
    private Double shelterLatitude;

    @Column(name = "shelter_longitude")
    private Double shelterLongitude;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date submitDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getShelterLatitude() {
        return shelterLatitude;
    }

    public void setShelterLatitude(Double shelterLatitude) {
        this.shelterLatitude = shelterLatitude;
    }

    public Double getShelterLongitude() {
        return shelterLongitude;
    }

    public void setShelterLongitude(Double shelterLongitude) {
        this.shelterLongitude = shelterLongitude;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
}
