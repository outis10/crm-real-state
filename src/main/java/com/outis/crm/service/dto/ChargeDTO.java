package com.outis.crm.service.dto;

import com.outis.crm.domain.enumeration.ChargeStatusEnum;
import com.outis.crm.domain.enumeration.ChargeTypeEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.crm.domain.Charge} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChargeDTO implements Serializable {

    private Long id;

    @NotNull
    private ChargeTypeEnum type;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Instant dueDate;

    @NotNull
    private ChargeStatusEnum status;

    private CustomerDTO customer;

    @NotNull
    private RentalContractDTO rentalContract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChargeTypeEnum getType() {
        return type;
    }

    public void setType(ChargeTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public ChargeStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ChargeStatusEnum status) {
        this.status = status;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public RentalContractDTO getRentalContract() {
        return rentalContract;
    }

    public void setRentalContract(RentalContractDTO rentalContract) {
        this.rentalContract = rentalContract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargeDTO)) {
            return false;
        }

        ChargeDTO chargeDTO = (ChargeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chargeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", dueDate='" + getDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", customer=" + getCustomer() +
            ", rentalContract=" + getRentalContract() +
            "}";
    }
}
