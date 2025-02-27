package com.outis.realstate.ai.domain;

import com.outis.realstate.ai.domain.enumeration.EntityNameEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RAGContext.
 */
@Document(collection = "rag_context")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RAGContext implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("entity_id")
    private Long entityId;

    @Field("entity_name")
    private EntityNameEnum entityName;

    @NotNull
    @Field("context_text")
    private String contextText;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public RAGContext id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public RAGContext entityId(Long entityId) {
        this.setEntityId(entityId);
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNameEnum getEntityName() {
        return this.entityName;
    }

    public RAGContext entityName(EntityNameEnum entityName) {
        this.setEntityName(entityName);
        return this;
    }

    public void setEntityName(EntityNameEnum entityName) {
        this.entityName = entityName;
    }

    public String getContextText() {
        return this.contextText;
    }

    public RAGContext contextText(String contextText) {
        this.setContextText(contextText);
        return this;
    }

    public void setContextText(String contextText) {
        this.contextText = contextText;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RAGContext)) {
            return false;
        }
        return getId() != null && getId().equals(((RAGContext) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RAGContext{" +
            "id=" + getId() +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", contextText='" + getContextText() + "'" +
            "}";
    }
}
