package com.outis.crm.web.rest;

import static com.outis.crm.domain.ChatInteractionAsserts.*;
import static com.outis.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.crm.IntegrationTest;
import com.outis.crm.domain.ChatInteraction;
import com.outis.crm.repository.ChatInteractionRepository;
import com.outis.crm.service.dto.ChatInteractionDTO;
import com.outis.crm.service.mapper.ChatInteractionMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChatInteractionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChatInteractionResourceIT {

    private static final String DEFAULT_CUSTOMER_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CHATBOT_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_CHATBOT_RESPONSE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/chat-interactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChatInteractionRepository chatInteractionRepository;

    @Autowired
    private ChatInteractionMapper chatInteractionMapper;

    @Autowired
    private EntityManager em;

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
            .customerMessage(DEFAULT_CUSTOMER_MESSAGE)
            .chatbotResponse(DEFAULT_CHATBOT_RESPONSE)
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
            .customerMessage(UPDATED_CUSTOMER_MESSAGE)
            .chatbotResponse(UPDATED_CHATBOT_RESPONSE)
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
    @Transactional
    void createChatInteraction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);
        var returnedChatInteractionDTO = om.readValue(
            restChatInteractionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO)))
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
    @Transactional
    void createChatInteractionWithExistingId() throws Exception {
        // Create the ChatInteraction with an existing ID
        chatInteraction.setId(1L);
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatInteractionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomerMessageIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setCustomerMessage(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChatbotResponseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setChatbotResponse(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        chatInteraction.setTimestamp(null);

        // Create the ChatInteraction, which fails.
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        restChatInteractionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChatInteractions() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.saveAndFlush(chatInteraction);

        // Get all the chatInteractionList
        restChatInteractionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatInteraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerMessage").value(hasItem(DEFAULT_CUSTOMER_MESSAGE)))
            .andExpect(jsonPath("$.[*].chatbotResponse").value(hasItem(DEFAULT_CHATBOT_RESPONSE)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    void getChatInteraction() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.saveAndFlush(chatInteraction);

        // Get the chatInteraction
        restChatInteractionMockMvc
            .perform(get(ENTITY_API_URL_ID, chatInteraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chatInteraction.getId().intValue()))
            .andExpect(jsonPath("$.customerMessage").value(DEFAULT_CUSTOMER_MESSAGE))
            .andExpect(jsonPath("$.chatbotResponse").value(DEFAULT_CHATBOT_RESPONSE))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChatInteraction() throws Exception {
        // Get the chatInteraction
        restChatInteractionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChatInteraction() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.saveAndFlush(chatInteraction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chatInteraction
        ChatInteraction updatedChatInteraction = chatInteractionRepository.findById(chatInteraction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedChatInteraction are not directly saved in db
        em.detach(updatedChatInteraction);
        updatedChatInteraction
            .customerMessage(UPDATED_CUSTOMER_MESSAGE)
            .chatbotResponse(UPDATED_CHATBOT_RESPONSE)
            .timestamp(UPDATED_TIMESTAMP);
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(updatedChatInteraction);

        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chatInteractionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChatInteractionToMatchAllProperties(updatedChatInteraction);
    }

    @Test
    @Transactional
    void putNonExistingChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(longCount.incrementAndGet());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chatInteractionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(longCount.incrementAndGet());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(longCount.incrementAndGet());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatInteractionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChatInteractionWithPatch() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.saveAndFlush(chatInteraction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chatInteraction using partial update
        ChatInteraction partialUpdatedChatInteraction = new ChatInteraction();
        partialUpdatedChatInteraction.setId(chatInteraction.getId());

        partialUpdatedChatInteraction.chatbotResponse(UPDATED_CHATBOT_RESPONSE).timestamp(UPDATED_TIMESTAMP);

        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChatInteraction.getId())
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
    @Transactional
    void fullUpdateChatInteractionWithPatch() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.saveAndFlush(chatInteraction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chatInteraction using partial update
        ChatInteraction partialUpdatedChatInteraction = new ChatInteraction();
        partialUpdatedChatInteraction.setId(chatInteraction.getId());

        partialUpdatedChatInteraction
            .customerMessage(UPDATED_CUSTOMER_MESSAGE)
            .chatbotResponse(UPDATED_CHATBOT_RESPONSE)
            .timestamp(UPDATED_TIMESTAMP);

        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChatInteraction.getId())
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
    @Transactional
    void patchNonExistingChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(longCount.incrementAndGet());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chatInteractionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(longCount.incrementAndGet());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chatInteractionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChatInteraction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chatInteraction.setId(longCount.incrementAndGet());

        // Create the ChatInteraction
        ChatInteractionDTO chatInteractionDTO = chatInteractionMapper.toDto(chatInteraction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatInteractionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(chatInteractionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChatInteraction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChatInteraction() throws Exception {
        // Initialize the database
        insertedChatInteraction = chatInteractionRepository.saveAndFlush(chatInteraction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the chatInteraction
        restChatInteractionMockMvc
            .perform(delete(ENTITY_API_URL_ID, chatInteraction.getId()).accept(MediaType.APPLICATION_JSON))
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
