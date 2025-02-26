package com.outis.realstate.properties.domain;

import com.outis.realstate.properties.domain.enumeration.SaleStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "oportunity_id")
    private Long oportunityId;

    @NotNull
    @Column(name = "sale_date", nullable = false)
    private Instant saleDate;

    @NotNull
    @Column(name = "total_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatusEnum status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return this.propertyId;
    }

    public Sale propertyId(Long propertyId) {
        this.setPropertyId(propertyId);
        return this;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public Sale customerId(Long customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOportunityId() {
        return this.oportunityId;
    }

    public Sale oportunityId(Long oportunityId) {
        this.setOportunityId(oportunityId);
        return this;
    }

    public void setOportunityId(Long oportunityId) {
        this.oportunityId = oportunityId;
    }

    public Instant getSaleDate() {
        return this.saleDate;
    }

    public Sale saleDate(Instant saleDate) {
        this.setSaleDate(saleDate);
        return this;
    }

    public void setSaleDate(Instant saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public Sale totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SaleStatusEnum getStatus() {
        return this.status;
    }

    public Sale status(SaleStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SaleStatusEnum status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return getId() != null && getId().equals(((Sale) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", propertyId=" + getPropertyId() +
            ", customerId=" + getCustomerId() +
            ", oportunityId=" + getOportunityId() +
            ", saleDate='" + getSaleDate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
