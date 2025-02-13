package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outis.crm.domain.enumeration.OperationTypeEnum;
import com.outis.crm.domain.enumeration.PropertyStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "property" }, allowSetters = true)
    private Set<NearbyLocation> nearbyLocations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "property" }, allowSetters = true)
    private Set<Quotation> quotations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "charges", "payments", "property", "customer" }, allowSetters = true)
    private Set<RentalContract> rentalContracts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "property" }, allowSetters = true)
    private Set<Opportunity> opportunities = new HashSet<>();

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

    public Set<NearbyLocation> getNearbyLocations() {
        return this.nearbyLocations;
    }

    public void setNearbyLocations(Set<NearbyLocation> nearbyLocations) {
        if (this.nearbyLocations != null) {
            this.nearbyLocations.forEach(i -> i.setProperty(null));
        }
        if (nearbyLocations != null) {
            nearbyLocations.forEach(i -> i.setProperty(this));
        }
        this.nearbyLocations = nearbyLocations;
    }

    public Property nearbyLocations(Set<NearbyLocation> nearbyLocations) {
        this.setNearbyLocations(nearbyLocations);
        return this;
    }

    public Property addNearbyLocation(NearbyLocation nearbyLocation) {
        this.nearbyLocations.add(nearbyLocation);
        nearbyLocation.setProperty(this);
        return this;
    }

    public Property removeNearbyLocation(NearbyLocation nearbyLocation) {
        this.nearbyLocations.remove(nearbyLocation);
        nearbyLocation.setProperty(null);
        return this;
    }

    public Set<Quotation> getQuotations() {
        return this.quotations;
    }

    public void setQuotations(Set<Quotation> quotations) {
        if (this.quotations != null) {
            this.quotations.forEach(i -> i.setProperty(null));
        }
        if (quotations != null) {
            quotations.forEach(i -> i.setProperty(this));
        }
        this.quotations = quotations;
    }

    public Property quotations(Set<Quotation> quotations) {
        this.setQuotations(quotations);
        return this;
    }

    public Property addQuotation(Quotation quotation) {
        this.quotations.add(quotation);
        quotation.setProperty(this);
        return this;
    }

    public Property removeQuotation(Quotation quotation) {
        this.quotations.remove(quotation);
        quotation.setProperty(null);
        return this;
    }

    public Set<RentalContract> getRentalContracts() {
        return this.rentalContracts;
    }

    public void setRentalContracts(Set<RentalContract> rentalContracts) {
        if (this.rentalContracts != null) {
            this.rentalContracts.forEach(i -> i.setProperty(null));
        }
        if (rentalContracts != null) {
            rentalContracts.forEach(i -> i.setProperty(this));
        }
        this.rentalContracts = rentalContracts;
    }

    public Property rentalContracts(Set<RentalContract> rentalContracts) {
        this.setRentalContracts(rentalContracts);
        return this;
    }

    public Property addRentalContract(RentalContract rentalContract) {
        this.rentalContracts.add(rentalContract);
        rentalContract.setProperty(this);
        return this;
    }

    public Property removeRentalContract(RentalContract rentalContract) {
        this.rentalContracts.remove(rentalContract);
        rentalContract.setProperty(null);
        return this;
    }

    public Set<Opportunity> getOpportunities() {
        return this.opportunities;
    }

    public void setOpportunities(Set<Opportunity> opportunities) {
        if (this.opportunities != null) {
            this.opportunities.forEach(i -> i.setProperty(null));
        }
        if (opportunities != null) {
            opportunities.forEach(i -> i.setProperty(this));
        }
        this.opportunities = opportunities;
    }

    public Property opportunities(Set<Opportunity> opportunities) {
        this.setOpportunities(opportunities);
        return this;
    }

    public Property addOpportunity(Opportunity opportunity) {
        this.opportunities.add(opportunity);
        opportunity.setProperty(this);
        return this;
    }

    public Property removeOpportunity(Opportunity opportunity) {
        this.opportunities.remove(opportunity);
        opportunity.setProperty(null);
        return this;
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
            "}";
    }
}
