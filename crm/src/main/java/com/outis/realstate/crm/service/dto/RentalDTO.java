package com.outis.realstate.crm.service.dto;

import com.outis.realstate.crm.domain.enumeration.ContractStatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.crm.domain.Rental} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private BigDecimal monthlyRent;

    private BigDecimal securityDeposit;

    @NotNull
    private ContractStatusEnum contractStatus;

    private Long createdBy;

    private PropertyDTO property;

    private CustomerDTO customer;

    private OpportunityDTO opportunity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public BigDecimal getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public ContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public void setProperty(PropertyDTO property) {
        this.property = property;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public OpportunityDTO getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(OpportunityDTO opportunity) {
        this.opportunity = opportunity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentalDTO)) {
            return false;
        }

        RentalDTO rentalDTO = (RentalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rentalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentalDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", monthlyRent=" + getMonthlyRent() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", contractStatus='" + getContractStatus() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", property=" + getProperty() +
            ", customer=" + getCustomer() +
            ", opportunity=" + getOpportunity() +
            "}";
    }
}
