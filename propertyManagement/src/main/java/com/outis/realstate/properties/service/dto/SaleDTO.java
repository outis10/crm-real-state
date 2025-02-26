package com.outis.realstate.properties.service.dto;

import com.outis.realstate.properties.domain.enumeration.SaleStatusEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.properties.domain.Sale} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaleDTO implements Serializable {

    private Long id;

    @NotNull
    private Long propertyId;

    @NotNull
    private Long customerId;

    private Long oportunityId;

    @NotNull
    private Instant saleDate;

    @NotNull
    private BigDecimal totalAmount;

    private SaleStatusEnum status;

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

    public Instant getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Instant saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SaleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SaleStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleDTO)) {
            return false;
        }

        SaleDTO saleDTO = (SaleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, saleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleDTO{" +
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
