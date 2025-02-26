package com.outis.realstate.properties.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.properties.domain.Quotation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuotationDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal finalPrice;

    @NotNull
    private Instant validityDate;

    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Instant getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Instant validityDate) {
        this.validityDate = validityDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuotationDTO)) {
            return false;
        }

        QuotationDTO quotationDTO = (QuotationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quotationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuotationDTO{" +
            "id=" + getId() +
            ", finalPrice=" + getFinalPrice() +
            ", validityDate='" + getValidityDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
