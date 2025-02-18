package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outis.crm.domain.enumeration.OpportunityStageEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
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

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "chatInteractions", "notifications", "quotations", "rentalContracts", "charges", "contacts" },
        allowSetters = true
    )
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "nearbyLocations", "quotations", "rentalContracts", "opportunities" }, allowSetters = true)
    private Property property;

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

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Opportunity property(Property property) {
        this.setProperty(property);
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
            "}";
    }
}
