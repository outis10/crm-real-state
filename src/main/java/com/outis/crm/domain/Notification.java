package com.outis.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outis.crm.domain.enumeration.NotificationStatusEnum;
import com.outis.crm.domain.enumeration.NotificationTargetEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "target", nullable = false)
    private NotificationTargetEnum target;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatusEnum status;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

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

    public Notification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationTargetEnum getTarget() {
        return this.target;
    }

    public Notification target(NotificationTargetEnum target) {
        this.setTarget(target);
        return this;
    }

    public void setTarget(NotificationTargetEnum target) {
        this.target = target;
    }

    public String getContent() {
        return this.content;
    }

    public Notification content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationStatusEnum getStatus() {
        return this.status;
    }

    public Notification status(NotificationStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(NotificationStatusEnum status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public Notification timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Notification customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return getId() != null && getId().equals(((Notification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", target='" + getTarget() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
