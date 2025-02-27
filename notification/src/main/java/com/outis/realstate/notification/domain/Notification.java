package com.outis.realstate.notification.domain;

import com.outis.realstate.notification.domain.enumeration.EntityNameEnum;
import com.outis.realstate.notification.domain.enumeration.NotificationStatusEnum;
import com.outis.realstate.notification.domain.enumeration.NotificationTargetEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Notification.
 */
@Document(collection = "notification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("entity_id")
    private Long entityId;

    @Field("entity_name")
    private EntityNameEnum entityName;

    @NotNull
    @Field("target")
    private NotificationTargetEnum target;

    @NotNull
    @Field("content")
    private String content;

    @NotNull
    @Field("status")
    private NotificationStatusEnum status;

    @NotNull
    @Field("timestamp")
    private Instant timestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Notification id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public Notification entityId(Long entityId) {
        this.setEntityId(entityId);
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNameEnum getEntityName() {
        return this.entityName;
    }

    public Notification entityName(EntityNameEnum entityName) {
        this.setEntityName(entityName);
        return this;
    }

    public void setEntityName(EntityNameEnum entityName) {
        this.entityName = entityName;
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
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", target='" + getTarget() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
