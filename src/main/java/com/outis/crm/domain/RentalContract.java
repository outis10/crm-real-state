package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outis.crm.domain.enumeration.ContractStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RentalContract.
 */
@Entity
@Table(name = "rental_contract")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

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
    @Column(name = "status", nullable = false)
    private ContractStatusEnum status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentalContract")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer", "rentalContract" }, allowSetters = true)
    private Set<Charge> charges = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentralContract")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rentralContract" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "nearbyLocations", "quotations", "rentalContracts", "opportunities" }, allowSetters = true)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "chatInteractions", "notifications", "quotations", "rentalContracts", "charges", "contacts" },
        allowSetters = true
    )
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RentalContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public RentalContract startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public RentalContract endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMonthlyRent() {
        return this.monthlyRent;
    }

    public RentalContract monthlyRent(BigDecimal monthlyRent) {
        this.setMonthlyRent(monthlyRent);
        return this;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public BigDecimal getSecurityDeposit() {
        return this.securityDeposit;
    }

    public RentalContract securityDeposit(BigDecimal securityDeposit) {
        this.setSecurityDeposit(securityDeposit);
        return this;
    }

    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public ContractStatusEnum getStatus() {
        return this.status;
    }

    public RentalContract status(ContractStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ContractStatusEnum status) {
        this.status = status;
    }

    public Set<Charge> getCharges() {
        return this.charges;
    }

    public void setCharges(Set<Charge> charges) {
        if (this.charges != null) {
            this.charges.forEach(i -> i.setRentalContract(null));
        }
        if (charges != null) {
            charges.forEach(i -> i.setRentalContract(this));
        }
        this.charges = charges;
    }

    public RentalContract charges(Set<Charge> charges) {
        this.setCharges(charges);
        return this;
    }

    public RentalContract addCharge(Charge charge) {
        this.charges.add(charge);
        charge.setRentalContract(this);
        return this;
    }

    public RentalContract removeCharge(Charge charge) {
        this.charges.remove(charge);
        charge.setRentalContract(null);
        return this;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setRentralContract(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setRentralContract(this));
        }
        this.payments = payments;
    }

    public RentalContract payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public RentalContract addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setRentralContract(this);
        return this;
    }

    public RentalContract removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setRentralContract(null);
        return this;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public RentalContract property(Property property) {
        this.setProperty(property);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RentalContract customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentalContract)) {
            return false;
        }
        return getId() != null && getId().equals(((RentalContract) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentalContract{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", monthlyRent=" + getMonthlyRent() +
            ", securityDeposit=" + getSecurityDeposit() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
