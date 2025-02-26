package com.outis.realstate.properties.service.dto;

import com.outis.realstate.properties.domain.enumeration.ContractStatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.properties.domain.Rental} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalDTO implements Serializable {

    private Long id;

    @NotNull
    private Long propertyId;

    @NotNull
    private Long customerId;

    private Long oportunityId;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private BigDecimal monthlyRent;

    private BigDecimal securityDeposit;

    @NotNull
    private ContractStatusEnum contractStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOportunityId() {
        return oportunityId;
    }

    public void setOportunityId(Long oportunityId) {
        this.oportunityId = oportunityId;
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
