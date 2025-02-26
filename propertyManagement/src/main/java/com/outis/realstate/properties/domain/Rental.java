package com.outis.realstate.properties.domain;

import com.outis.realstate.properties.domain.enumeration.ContractStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rental.
 */
@Entity
@Table(name = "rental")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rental implements Serializable {

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
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull
    @Column(name = "monthly_rent", precision = 21, scale = 2, nullable = false)
    private BigDecimal monthlyRent;

    @Column(name = "security_deposit", precision = 21, scale = 2)
    private BigDecimal securityDeposit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status", nullable = false)
    private ContractStatusEnum contractStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rental id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return this.propertyId;
    }

    public Rental propertyId(Long propertyId) {
        this.setPropertyId(propertyId);
        return this;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public Rental customerId(Long customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOportunityId() {
        return this.oportunityId;
    }

    public Rental oportunityId(Long oportunityId) {
        this.setOportunityId(oportunityId);
        return this;
    }

    public void setOportunityId(Long oportunityId) {
        this.oportunityId = oportunityId;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Rental startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Rental endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMonthlyRent() {
        return this.monthlyRent;
    }

    public Rental monthlyRent(BigDecimal monthlyRent) {
        this.setMonthlyRent(monthlyRent);
        return this;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public BigDecimal getSecurityDeposit() {
        return this.securityDeposit;
    }

    public Rental securityDeposit(BigDecimal securityDeposit) {
        this.setSecurityDeposit(securityDeposit);
        return this;
    }

    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public ContractStatusEnum getContractStatus() {
        return this.contractStatus;
    }

    public Rental contractStatus(ContractStatusEnum contractStatus) {
        this.setContractStatus(contractStatus);
        return this;
    }

    public void setContractStatus(ContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rental)) {
            return false;
        }
        return getId() != null && getId().equals(((Rental) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rental{" +
            "id=" + getId() +
            ", propertyId=" + getPropertyId() +
            ", customerId=" + getCustomerId() +
            ", oportunityId=" + getOportunityId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", monthlyRent=" + getMonthlyRent() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", contractStatus='" + getContractStatus() + "'" +
            "}";
    }
}
