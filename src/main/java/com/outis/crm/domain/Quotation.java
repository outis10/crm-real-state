package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Quotation.
 */
@Entity
@Table(name = "quotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "final_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal finalPrice;

    @NotNull
    @Column(name = "validity_date", nullable = false)
    private Instant validityDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Quotation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFinalPrice() {
        return this.finalPrice;
    }

    public Quotation finalPrice(BigDecimal finalPrice) {
        this.setFinalPrice(finalPrice);
        return this;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Instant getValidityDate() {
        return this.validityDate;
    }

    public Quotation validityDate(Instant validityDate) {
        this.setValidityDate(validityDate);
        return this;
    }

    public void setValidityDate(Instant validityDate) {
        this.validityDate = validityDate;
    }

    public String getComments() {
        return this.comments;
    }

    public Quotation comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Quotation customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Quotation property(Property property) {
        this.setProperty(property);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quotation)) {
            return false;
        }
        return getId() != null && getId().equals(((Quotation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quotation{" +
            "id=" + getId() +
            ", finalPrice=" + getFinalPrice() +
            ", validityDate='" + getValidityDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
