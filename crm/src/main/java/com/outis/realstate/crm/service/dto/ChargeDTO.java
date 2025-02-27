package com.outis.realstate.crm.service.dto;

import com.outis.realstate.crm.domain.enumeration.ChargeStatusEnum;
import com.outis.realstate.crm.domain.enumeration.ChargeTypeEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.crm.domain.Charge} entity.
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

    private Long createdBy;

    @NotNull
    private RentalDTO rental;

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public RentalDTO getRental() {
        return rental;
    }

    public void setRental(RentalDTO rental) {
        this.rental = rental;
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
            ", createdBy=" + getCreatedBy() +
            ", rental=" + getRental() +
            "}";
    }
}
