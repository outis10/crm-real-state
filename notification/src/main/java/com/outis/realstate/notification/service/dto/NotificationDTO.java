package com.outis.realstate.notification.service.dto;

import com.outis.realstate.notification.domain.enumeration.EntityNameEnum;
import com.outis.realstate.notification.domain.enumeration.NotificationStatusEnum;
import com.outis.realstate.notification.domain.enumeration.NotificationTargetEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.notification.domain.Notification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationDTO implements Serializable {

    private String id;

    @NotNull
    private Long entityId;

    private EntityNameEnum entityName;

    @NotNull
    private NotificationTargetEnum target;

    @NotNull
    private String content;

    @NotNull
    private NotificationStatusEnum status;

    @NotNull
    private Instant timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNameEnum getEntityName() {
        return entityName;
    }

    public void setEntityName(EntityNameEnum entityName) {
        this.entityName = entityName;
    }

    public NotificationTargetEnum getTarget() {
        return target;
    }

    public void setTarget(NotificationTargetEnum target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(NotificationStatusEnum status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id='" + getId() + "'" +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", target='" + getTarget() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
