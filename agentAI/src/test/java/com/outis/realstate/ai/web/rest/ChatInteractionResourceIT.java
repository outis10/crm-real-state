package com.outis.realstate.ai.web.rest;

import static com.outis.realstate.ai.domain.ChatInteractionAsserts.*;
import static com.outis.realstate.ai.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.realstate.ai.IntegrationTest;
import com.outis.realstate.ai.domain.ChatInteraction;
import com.outis.realstate.ai.domain.enumeration.EntityNameEnum;
import com.outis.realstate.ai.repository.ChatInteractionRepository;
import com.outis.realstate.ai.service.dto.ChatInteractionDTO;
import com.outis.realstate.ai.service.mapper.ChatInteractionMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ChatInteractionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChatInteractionResourceIT {

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;

    private static final EntityNameEnum DEFAULT_ENTITY_NAME = EntityNameEnum.PROPERTY;
    private static final EntityNameEnum UPDATED_ENTITY_NAME = EntityNameEnum.CUSTOMER;

    private static final String DEFAULT_CUSTOMER_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_CHATBOT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_CHATBOT_ANSWER = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/chat-interactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChatInteractionRepository chatInteractionRepository;

    @Autowired
    private ChatInteractionMapper chatInteractionMapper;

    @Autowired
    private MockMvc restChatInteractionMockMvc;

    private ChatInteraction chatInteraction;

    private ChatInteraction insertedChatInteraction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatInteraction createEntity() {
        return new ChatInteraction()
            .entityId(DEFAULT_ENTITY_ID)
            .entityName(DEFAULT_ENTITY_NAME)
            .customerQuestion(DEFAULT_CUSTOMER_QUESTION)
            .chatbotAnswer(DEFAULT_CHATBOT_ANSWER)
            .timestamp(DEFAULT_TIMESTAMP);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatInteraction createUpdatedEntity() {
        return new ChatInteraction()
            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .customerQuestion(UPDATED_CUSTOMER_QUESTION)
            .chatbotAnswer(UPDATED_CHATBOT_ANSWER)
            .timestamp(UPDATED_TIMESTAMP);
    }

    @BeforeEach
    public void initTest() {
        chatInteraction = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedChatInteraction != null) {
            chatInteractionRepository.delete(insertedChatInteraction);
            insertedChatInteraction = null;
        }
    }

    @Test
    void createChatInteraction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);
        var returnedChatInteractionDTO = om.readValue(
            restChatInteractionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(chatInteractionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ChatInteractionDTO.class
        );

        // Validate the ChatInteraction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedChatInteraction = chatInteractionMapper.toEntity(returnedChatInteractionDTO);
        assertChatInteractionUpdatableFieldsEquals(returnedChatInteraction, getPersistedChatInteraction(returnedChatInteraction));

        insertedChatInteraction = returnedChatInteraction;
    }

    @Test
    void createChatInteractionWithExistingId() throws Exception {
        // Create the ChatInteraction with an existing ID
        chatInteraction.setId("existing_id");
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatInteractionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkEntityIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setEntityId(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCustomerQuestionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setCustomerQuestion(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkChatbotAnswerIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setChatbotAnswer(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTimestampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setTimestamp(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllChatInteractions() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.save(chatInteraction);

        // Get all the chatInteractionList
        restChatInteractionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatInteraction.getId())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].customerQuestion").value(hasItem(DEFAULT_CUSTOMER_QUESTION)))
            .andExpect(jsonPath("$.[*].chatbotAnswer").value(hasItem(DEFAULT_CHATBOT_ANSWER)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    void getChatInteraction() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.save(chatInteraction);

        // Get the chatInteraction
        restChatInteractionMockMvc
            .perform(get(ENTITY_API_URL_ID, chatInteraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chatInteraction.getId()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.customerQuestion").value(DEFAULT_CUSTOMER_QUESTION))
            .andExpect(jsonPath("$.chatbotAnswer").value(DEFAULT_CHATBOT_ANSWER))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    void getNonExistingChatInteraction() throws Exception {
        // Get the chatInteraction
        restChatInteractionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingChatInteraction() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.save(chatInteraction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chatInteraction
        ChatInteraction updatedChatInteraction = chatInteractionRepository.findById(chatInteraction.getId()).orElseThrow();
        updatedChatInteraction
            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .customerQuestion(UPDATED_CUSTOMER_QUESTION)
            .chatbotAnswer(UPDATED_CHATBOT_ANSWER)
            .timestamp(UPDATED_TIMESTAMP);
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(updatedChatInteraction);

        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chatInteractionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChatInteractionToMatchAllProperties(updatedChatInteraction);
    }

    @Test
    void putNonExistingChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(UUID.randomUUID().toString());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chatInteractionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(UUID.randomUUID().toString());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(UUID.randomUUID().toString());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChatInteractionWithPatch() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.save(chatInteraction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chatInteraction using partial update
        ChatInteraction partialUpdatedChatInteraction = new ChatInteraction();
        partialUpdatedChatInteraction.setId(chatInteraction.getId());

        partialUpdatedChatInteraction
            .customerQuestion(UPDATED_CUSTOMER_QUESTION)
            .chatbotAnswer(UPDATED_CHATBOT_ANSWER)
            .timestamp(UPDATED_TIMESTAMP);

        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChatInteraction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChatInteraction))
            )
            .andExpect(status().isOk());

        // Validate the ChatInteraction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChatInteractionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedChatInteraction, chatInteraction),
            getPersistedChatInteraction(chatInteraction)
        );
    }

    @Test
    void fullUpdateChatInteractionWithPatch() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.save(chatInteraction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chatInteraction using partial update
        ChatInteraction partialUpdatedChatInteraction = new ChatInteraction();
        partialUpdatedChatInteraction.setId(chatInteraction.getId());

        partialUpdatedChatInteraction
            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .customerQuestion(UPDATED_CUSTOMER_QUESTION)
            .chatbotAnswer(UPDATED_CHATBOT_ANSWER)
            .timestamp(UPDATED_TIMESTAMP);

        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChatInteraction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChatInteraction))
            )
            .andExpect(status().isOk());

        // Validate the ChatInteraction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChatInteractionUpdatableFieldsEquals(
            partialUpdatedChatInteraction,
            getPersistedChatInteraction(partialUpdatedChatInteraction)
        );
    }

    @Test
    void patchNonExistingChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(UUID.randomUUID().toString());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chatInteractionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(UUID.randomUUID().toString());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(UUID.randomUUID().toString());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChatInteraction() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.save(chatInteraction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the chatInteraction
        restChatInteractionMockMvc
            .perform(delete(ENTITY_API_URL_ID, chatInteraction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return chatInteractionRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ChatInteraction getPersistedChatInteraction(ChatInteraction chatInteraction) {
        return chatInteractionRepository.findById(chatInteraction.getId()).orElseThrow();
    }

    protected void assertPersistedChatInteractionToMatchAllProperties(ChatInteraction expectedChatInteraction) {
        assertChatInteractionAllPropertiesEquals(expectedChatInteraction, getPersistedChatInteraction(expectedChatInteraction));
    }

    protected void assertPersistedChatInteractionToMatchUpdatableProperties(ChatInteraction expectedChatInteraction) {
        assertChatInteractionAllUpdatablePropertiesEquals(expectedChatInteraction, getPersistedChatInteraction(expectedChatInteraction));
    }
}
