package com.outis.crm.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.crm.domain.NearbyLocation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NearbyLocationDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private Double distance;

    private String coordinates;

    @NotNull
    private PropertyDTO property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public void setProperty(PropertyDTO property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NearbyLocationDTO)) {
            return false;
        }

        NearbyLocationDTO nearbyLocationDTO = (NearbyLocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nearbyLocationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NearbyLocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", distance=" + getDistance() +
            ", coordinates='" + getCoordinates() + "'" +
            ", property=" + getProperty() +
            "}";
    }
}
