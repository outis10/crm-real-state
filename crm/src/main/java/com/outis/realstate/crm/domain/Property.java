package com.outis.realstate.crm.domain;

import com.outis.realstate.crm.domain.enumeration.OperationTypeEnum;
import com.outis.realstate.crm.domain.enumeration.PropertyStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code_name")
    private String codeName;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationTypeEnum operationType;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "rental_price", precision = 21, scale = 2)
    private BigDecimal rentalPrice;

    @NotNull
    @Column(name = "area", nullable = false)
    private Integer area;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "appreciation_rate")
    private Double appreciationRate;

    @Column(name = "features")
    private String features;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PropertyStatusEnum status;

    @Column(name = "images")
    private String images;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Property id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Property name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public Property codeName(String codeName) {
        this.setCodeName(codeName);
        return this;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getType() {
        return this.type;
    }

    public Property type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OperationTypeEnum getOperationType() {
        return this.operationType;
    }

    public Property operationType(OperationTypeEnum operationType) {
        this.setOperationType(operationType);
        return this;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public String getLocation() {
        return this.location;
    }

    public Property location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return this.city;
    }

    public Property city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Property state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Property postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Property price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRentalPrice() {
        return this.rentalPrice;
    }

    public Property rentalPrice(BigDecimal rentalPrice) {
        this.setRentalPrice(rentalPrice);
        return this;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Integer getArea() {
        return this.area;
    }

    public Property area(Integer area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBedrooms() {
        return this.bedrooms;
    }

    public Property bedrooms(Integer bedrooms) {
        this.setBedrooms(bedrooms);
        return this;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return this.bathrooms;
    }

    public Property bathrooms(Integer bathrooms) {
        this.setBathrooms(bathrooms);
        return this;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getAppreciationRate() {
        return this.appreciationRate;
    }

    public Property appreciationRate(Double appreciationRate) {
        this.setAppreciationRate(appreciationRate);
        return this;
    }

    public void setAppreciationRate(Double appreciationRate) {
        this.appreciationRate = appreciationRate;
    }

    public String getFeatures() {
        return this.features;
    }

    public Property features(String features) {
        this.setFeatures(features);
        return this;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public PropertyStatusEnum getStatus() {
        return this.status;
    }

    public Property status(PropertyStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PropertyStatusEnum status) {
        this.status = status;
    }

    public String getImages() {
        return this.images;
    }

    public Property images(String images) {
        this.setImages(images);
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Property createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return getId() != null && getId().equals(((Property) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Property{" +
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
            ", createdBy=" + getCreatedBy() +
            "}";
    }
}
