package com.outis.realstate.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outis.realstate.crm.domain.enumeration.OpportunityStageEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Opportunity.
 */
@Entity
@Table(name = "opportunity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @DecimalMin(value = "0")
    @Column(name = "budget", precision = 21, scale = 2)
    private BigDecimal budget;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "probability", nullable = false)
    private Integer probability;

    @NotNull
    @Column(name = "expected_close_date", nullable = false)
    private LocalDate expectedCloseDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stage", nullable = false)
    private OpportunityStageEnum stage;


    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "rentals" }, allowSetters = true)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "opportunity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "property", "customer", "opportunity" }, allowSetters = true)
    private Set<Rental> rentals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opportunity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Opportunity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudget() {
        return this.budget;
    }

    public Opportunity budget(BigDecimal budget) {
        this.setBudget(budget);
        return this;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Opportunity amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getProbability() {
        return this.probability;
    }

    public Opportunity probability(Integer probability) {
        this.setProbability(probability);
        return this;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public LocalDate getExpectedCloseDate() {
        return this.expectedCloseDate;
    }

    public Opportunity expectedCloseDate(LocalDate expectedCloseDate) {
        this.setExpectedCloseDate(expectedCloseDate);
        return this;
    }

    public void setExpectedCloseDate(LocalDate expectedCloseDate) {
        this.expectedCloseDate = expectedCloseDate;
    }

    public OpportunityStageEnum getStage() {
        return this.stage;
    }

    public Opportunity stage(OpportunityStageEnum stage) {
        this.setStage(stage);
        return this;
    }

    public void setStage(OpportunityStageEnum stage) {
        this.stage = stage;
    }

    public String getDescription() {
        return this.description;
    }

    public Opportunity description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Opportunity createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return this.modifiedAt;
    }

    public Opportunity modifiedAt(Instant modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Instant getClosedAt() {
        return this.closedAt;
    }

    public Opportunity closedAt(Instant closedAt) {
        this.setClosedAt(closedAt);
        return this;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Opportunity createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Opportunity customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Set<Rental> getRentals() {
        return this.rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        if (this.rentals != null) {
            this.rentals.forEach(i -> i.setOpportunity(null));
        }
        if (rentals != null) {
            rentals.forEach(i -> i.setOpportunity(this));
        }
        this.rentals = rentals;
    }

    public Opportunity rentals(Set<Rental> rentals) {
        this.setRentals(rentals);
        return this;
    }

    public Opportunity addRental(Rental rental) {
        this.rentals.add(rental);
        rental.setOpportunity(this);
        return this;
    }

    public Opportunity removeRental(Rental rental) {
        this.rentals.remove(rental);
        rental.setOpportunity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opportunity)) {
            return false;
        }
        return getId() != null && getId().equals(((Opportunity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opportunity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", budget=" + getBudget() +
            ", amount=" + getAmount() +
            ", probability=" + getProbability() +
            ", expectedCloseDate='" + getExpectedCloseDate() + "'" +
            ", stage='" + getStage() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", closedAt='" + getClosedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            "}";
    }
}
