package com.outis.realstate.ai.web.rest;

import static com.outis.realstate.ai.domain.RAGContextAsserts.*;
import static com.outis.realstate.ai.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.realstate.ai.IntegrationTest;
import com.outis.realstate.ai.domain.RAGContext;
import com.outis.realstate.ai.domain.enumeration.EntityNameEnum;
import com.outis.realstate.ai.repository.RAGContextRepository;
import com.outis.realstate.ai.service.dto.RAGContextDTO;
import com.outis.realstate.ai.service.mapper.RAGContextMapper;
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
 * Integration tests for the {@link RAGContextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RAGContextResourceIT {

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;

    private static final EntityNameEnum DEFAULT_ENTITY_NAME = EntityNameEnum.PROPERTY;
    private static final EntityNameEnum UPDATED_ENTITY_NAME = EntityNameEnum.CUSTOMER;

    private static final String DEFAULT_CONTEXT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rag-contexts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RAGContextRepository rAGContextRepository;

    @Autowired
    private RAGContextMapper rAGContextMapper;

    @Autowired
    private MockMvc restRAGContextMockMvc;

    private RAGContext rAGContext;

    private RAGContext insertedRAGContext;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RAGContext createEntity() {
        return new RAGContext().entityId(DEFAULT_ENTITY_ID).entityName(DEFAULT_ENTITY_NAME).contextText(DEFAULT_CONTEXT_TEXT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RAGContext createUpdatedEntity() {
        return new RAGContext().entityId(UPDATED_ENTITY_ID).entityName(UPDATED_ENTITY_NAME).contextText(UPDATED_CONTEXT_TEXT);
    }

    @BeforeEach
    public void initTest() {
        rAGContext = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRAGContext != null) {
            rAGContextRepository.delete(insertedRAGContext);
            insertedRAGContext = null;
        }
    }

    @Test
    void createRAGContext() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);
        var returnedRAGContextDTO = om.readValue(
            restRAGContextMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rAGContextDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RAGContextDTO.class
        );

        // Validate the RAGContext in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRAGContext = rAGContextMapper.toEntity(returnedRAGContextDTO);
        assertRAGContextUpdatableFieldsEquals(returnedRAGContext, getPersistedRAGContext(returnedRAGContext));

        insertedRAGContext = returnedRAGContext;
    }

    @Test
    void createRAGContextWithExistingId() throws Exception {
        // Create the RAGContext with an existing ID
        rAGContext.setId("existing_id");
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRAGContextMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rAGContextDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkEntityIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rAGContext.setEntityId(null);

        // Create the RAGContext, which fails.
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        restRAGContextMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rAGContextDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkContextTextIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rAGContext.setContextText(null);

        // Create the RAGContext, which fails.
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        restRAGContextMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rAGContextDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllRAGContexts() throws Exception {
        // Initialize the database
        insertedRAGContext = rAGContextRepository.save(rAGContext);

        // Get all the rAGContextList
        restRAGContextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rAGContext.getId())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].contextText").value(hasItem(DEFAULT_CONTEXT_TEXT)));
    }

    @Test
    void getRAGContext() throws Exception {
        // Initialize the database
        insertedRAGContext = rAGContextRepository.save(rAGContext);

        // Get the rAGContext
        restRAGContextMockMvc
            .perform(get(ENTITY_API_URL_ID, rAGContext.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rAGContext.getId()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.contextText").value(DEFAULT_CONTEXT_TEXT));
    }

    @Test
    void getNonExistingRAGContext() throws Exception {
        // Get the rAGContext
        restRAGContextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRAGContext() throws Exception {
        // Initialize the database
        insertedRAGContext = rAGContextRepository.save(rAGContext);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rAGContext
        RAGContext updatedRAGContext = rAGContextRepository.findById(rAGContext.getId()).orElseThrow();
        updatedRAGContext.entityId(UPDATED_ENTITY_ID).entityName(UPDATED_ENTITY_NAME).contextText(UPDATED_CONTEXT_TEXT);
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(updatedRAGContext);

        restRAGContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rAGContextDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rAGContextDTO))
            )
            .andExpect(status().isOk());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRAGContextToMatchAllProperties(updatedRAGContext);
    }

    @Test
    void putNonExistingRAGContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rAGContext.setId(UUID.randomUUID().toString());

        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRAGContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rAGContextDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rAGContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRAGContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rAGContext.setId(UUID.randomUUID().toString());

        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRAGContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rAGContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRAGContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rAGContext.setId(UUID.randomUUID().toString());

        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRAGContextMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rAGContextDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRAGContextWithPatch() throws Exception {
        // Initialize the database
        insertedRAGContext = rAGContextRepository.save(rAGContext);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rAGContext using partial update
        RAGContext partialUpdatedRAGContext = new RAGContext();
        partialUpdatedRAGContext.setId(rAGContext.getId());

        partialUpdatedRAGContext.entityName(UPDATED_ENTITY_NAME).contextText(UPDATED_CONTEXT_TEXT);

        restRAGContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRAGContext.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRAGContext))
            )
            .andExpect(status().isOk());

        // Validate the RAGContext in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRAGContextUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRAGContext, rAGContext),
            getPersistedRAGContext(rAGContext)
        );
    }

    @Test
    void fullUpdateRAGContextWithPatch() throws Exception {
        // Initialize the database
        insertedRAGContext = rAGContextRepository.save(rAGContext);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rAGContext using partial update
        RAGContext partialUpdatedRAGContext = new RAGContext();
        partialUpdatedRAGContext.setId(rAGContext.getId());

        partialUpdatedRAGContext.entityId(UPDATED_ENTITY_ID).entityName(UPDATED_ENTITY_NAME).contextText(UPDATED_CONTEXT_TEXT);

        restRAGContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRAGContext.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRAGContext))
            )
            .andExpect(status().isOk());

        // Validate the RAGContext in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRAGContextUpdatableFieldsEquals(partialUpdatedRAGContext, getPersistedRAGContext(partialUpdatedRAGContext));
    }

    @Test
    void patchNonExistingRAGContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rAGContext.setId(UUID.randomUUID().toString());

        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRAGContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rAGContextDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rAGContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRAGContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rAGContext.setId(UUID.randomUUID().toString());

        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRAGContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rAGContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRAGContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rAGContext.setId(UUID.randomUUID().toString());

        // Create the RAGContext
        RAGContextDTO rAGContextDTO = rAGContextMapper.toDto(rAGContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRAGContextMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rAGContextDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RAGContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRAGContext() throws Exception {
        // Initialize the database
        insertedRAGContext = rAGContextRepository.save(rAGContext);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rAGContext
        restRAGContextMockMvc
            .perform(delete(ENTITY_API_URL_ID, rAGContext.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rAGContextRepository.count();
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

    protected RAGContext getPersistedRAGContext(RAGContext rAGContext) {
        return rAGContextRepository.findById(rAGContext.getId()).orElseThrow();
    }

    protected void assertPersistedRAGContextToMatchAllProperties(RAGContext expectedRAGContext) {
        assertRAGContextAllPropertiesEquals(expectedRAGContext, getPersistedRAGContext(expectedRAGContext));
    }

    protected void assertPersistedRAGContextToMatchUpdatableProperties(RAGContext expectedRAGContext) {
        assertRAGContextAllUpdatablePropertiesEquals(expectedRAGContext, getPersistedRAGContext(expectedRAGContext));
    }
}
