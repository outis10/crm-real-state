package com.outis.crm.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.outis.crm.domain.ChatInteraction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChatInteractionDTO implements Serializable {

    private Long id;

    @NotNull
    private String customerMessage;

    @NotNull
    private String chatbotResponse;

    @NotNull
    private Instant timestamp;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getChatbotResponse() {
        return chatbotResponse;
    }

    public void setChatbotResponse(String chatbotResponse) {
        this.chatbotResponse = chatbotResponse;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
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
            "id=" + getId() +
            ", customerMessage='" + getCustomerMessage() + "'" +
            ", chatbotResponse='" + getChatbotResponse() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", customer=" + getCustomer() +
            "}";
    }
}
