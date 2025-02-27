package com.outis.realstate.ai.service.dto;

import com.outis.realstate.ai.domain.enumeration.EntityNameEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.ai.domain.RAGContext} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RAGContextDTO implements Serializable {

    private String id;

    @NotNull
    private Long entityId;

    private EntityNameEnum entityName;

    @NotNull
    private String contextText;

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

    public String getContextText() {
        return contextText;
    }

    public void setContextText(String contextText) {
        this.contextText = contextText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RAGContextDTO)) {
            return false;
        }

        RAGContextDTO rAGContextDTO = (RAGContextDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rAGContextDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RAGContextDTO{" +
            "id='" + getId() + "'" +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", contextText='" + getContextText() + "'" +
            "}";
    }
}
