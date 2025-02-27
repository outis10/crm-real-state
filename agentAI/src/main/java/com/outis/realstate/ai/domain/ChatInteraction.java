package com.outis.realstate.ai.domain;

import com.outis.realstate.ai.domain.enumeration.EntityNameEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ChatInteraction.
 */
@Document(collection = "chat_interaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChatInteraction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("entity_id")
    private Long entityId;

    @Field("entity_name")
    private EntityNameEnum entityName;

    @NotNull
    @Field("customer_question")
    private String customerQuestion;

    @NotNull
    @Field("chatbot_answer")
    private String chatbotAnswer;

    @NotNull
    @Field("timestamp")
    private Instant timestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ChatInteraction id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public ChatInteraction entityId(Long entityId) {
        this.setEntityId(entityId);
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNameEnum getEntityName() {
        return this.entityName;
    }

    public ChatInteraction entityName(EntityNameEnum entityName) {
        this.setEntityName(entityName);
        return this;
    }

    public void setEntityName(EntityNameEnum entityName) {
        this.entityName = entityName;
    }

    public String getCustomerQuestion() {
        return this.customerQuestion;
    }

    public ChatInteraction customerQuestion(String customerQuestion) {
        this.setCustomerQuestion(customerQuestion);
        return this;
    }

    public void setCustomerQuestion(String customerQuestion) {
        this.customerQuestion = customerQuestion;
    }

    public String getChatbotAnswer() {
        return this.chatbotAnswer;
    }

    public ChatInteraction chatbotAnswer(String chatbotAnswer) {
        this.setChatbotAnswer(chatbotAnswer);
        return this;
    }

    public void setChatbotAnswer(String chatbotAnswer) {
        this.chatbotAnswer = chatbotAnswer;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public ChatInteraction timestamp(Instant timestamp) {
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
        if (!(o instanceof ChatInteraction)) {
            return false;
        }
        return getId() != null && getId().equals(((ChatInteraction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatInteraction{" +
            "id=" + getId() +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", customerQuestion='" + getCustomerQuestion() + "'" +
            ", chatbotAnswer='" + getChatbotAnswer() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
