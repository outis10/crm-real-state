package com.outis.realstate.ai.service.dto;

import com.outis.realstate.ai.domain.enumeration.EntityNameEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.realstate.ai.domain.ChatInteraction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChatInteractionDTO implements Serializable {

    private String id;

    @NotNull
    private Long entityId;

    private EntityNameEnum entityName;

    @NotNull
    private String customerQuestion;

    @NotNull
    private String chatbotAnswer;

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

    public String getCustomerQuestion() {
        return customerQuestion;
    }

    public void setCustomerQuestion(String customerQuestion) {
        this.customerQuestion = customerQuestion;
    }

    public String getChatbotAnswer() {
        return chatbotAnswer;
    }

    public void setChatbotAnswer(String chatbotAnswer) {
        this.chatbotAnswer = chatbotAnswer;
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
        if (!(o instanceof ChatInteractionDTO)) {
            return false;
        }

        ChatInteractionDTO chatInteractionDTO = (ChatInteractionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chatInteractionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatInteractionDTO{" +
            "id='" + getId() + "'" +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", customerQuestion='" + getCustomerQuestion() + "'" +
            ", chatbotAnswer='" + getChatbotAnswer() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
