package com.outis.crm.service.dto;

import com.outis.crm.domain.enumeration.ContractStatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.crm.domain.RentalContract} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalContractDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private BigDecimal monthlyRent;

    private BigDecimal securityDeposit;

    @NotNull
    private ContractStatusEnum status;

    private PropertyDTO property;

    private CustomerDTO customer;

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

    public ContractStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ContractStatusEnum status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentalContractDTO)) {
            return false;
        }

        RentalContractDTO rentalContractDTO = (RentalContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rentalContractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentalContractDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", monthlyRent=" + getMonthlyRent() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", status='" + getStatus() + "'" +
            ", property=" + getProperty() +
            ", customer=" + getCustomer() +
            "}";
    }
}
