package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outis.crm.domain.enumeration.ChargeStatusEnum;
import com.outis.crm.domain.enumeration.ChargeTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Charge.
 */
@Entity
@Table(name = "charge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Charge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChargeTypeEnum type;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChargeStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "chatInteractions", "notifications", "quotations", "rentalContracts", "charges", "contacts" },
        allowSetters = true
    )
    private Customer customer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "charges", "payments", "property", "customer" }, allowSetters = true)
    private RentalContract rentalContract;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Charge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChargeTypeEnum getType() {
        return this.type;
    }

    public Charge type(ChargeTypeEnum type) {
        this.setType(type);
        return this;
    }

    public void setType(ChargeTypeEnum type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Charge amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public Charge dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public ChargeStatusEnum getStatus() {
        return this.status;
    }

    public Charge status(ChargeStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ChargeStatusEnum status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Charge customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public RentalContract getRentalContract() {
        return this.rentalContract;
    }

    public void setRentalContract(RentalContract rentalContract) {
        this.rentalContract = rentalContract;
    }

    public Charge rentalContract(RentalContract rentalContract) {
        this.setRentalContract(rentalContract);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Charge)) {
            return false;
        }
        return getId() != null && getId().equals(((Charge) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Charge{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", dueDate='" + getDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
