package com.outis.crm.service.dto;

import com.outis.crm.domain.enumeration.OperationTypeEnum;
import com.outis.crm.domain.enumeration.PropertyStatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.crm.domain.Property} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PropertyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String codeName;

    @NotNull
    private String type;

    @NotNull
    private OperationTypeEnum operationType;

    @NotNull
    private String location;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String postalCode;

    @NotNull
    private BigDecimal price;

    private BigDecimal rentalPrice;

    @NotNull
    private Integer area;

    private Integer bedrooms;

    private Integer bathrooms;

    private Double appreciationRate;

    private String features;

    @NotNull
    private PropertyStatusEnum status;

    private String images;

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

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getAppreciationRate() {
        return appreciationRate;
    }

    public void setAppreciationRate(Double appreciationRate) {
        this.appreciationRate = appreciationRate;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public PropertyStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PropertyStatusEnum status) {
        this.status = status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertyDTO)) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, propertyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", type='" + getType() + "'" +
            ", operationType='" + getOperationType() + "'" +
            ", location='" + getLocation() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", price=" + getPrice() +
            ", rentalPrice=" + getRentalPrice() +
            ", area=" + getArea() +
            ", bedrooms=" + getBedrooms() +
            ", bathrooms=" + getBathrooms() +
            ", appreciationRate=" + getAppreciationRate() +
            ", features='" + getFeatures() + "'" +
            ", status='" + getStatus() + "'" +
            ", images='" + getImages() + "'" +
            "}";
    }
}
