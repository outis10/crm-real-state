package com.outis.crm.web.rest;

import static com.outis.crm.domain.QuotationAsserts.*;
import static com.outis.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static com.outis.crm.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.crm.IntegrationTest;
import com.outis.crm.domain.Quotation;
import com.outis.crm.repository.QuotationRepository;
import com.outis.crm.service.dto.QuotationDTO;
import com.outis.crm.service.mapper.QuotationMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link QuotationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotationResourceIT {

    private static final BigDecimal DEFAULT_FINAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FINAL_PRICE = new BigDecimal(2);

    private static final Instant DEFAULT_VALIDITY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDITY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quotations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationMapper quotationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationMockMvc;

    private Quotation quotation;

    private Quotation insertedQuotation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotation createEntity() {
        return new Quotation().finalPrice(DEFAULT_FINAL_PRICE).validityDate(DEFAULT_VALIDITY_DATE).comments(DEFAULT_COMMENTS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotation createUpdatedEntity() {
        return new Quotation().finalPrice(UPDATED_FINAL_PRICE).validityDate(UPDATED_VALIDITY_DATE).comments(UPDATED_COMMENTS);
    }

    @BeforeEach
    public void initTest() {
        quotation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedQuotation != null) {
            quotationRepository.delete(insertedQuotation);
            insertedQuotation = null;
        }
    }

    @Test
    @Transactional
    void createQuotation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);
        var returnedQuotationDTO = om.readValue(
            restQuotationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            QuotationDTO.class
        );

        // Validate the Quotation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedQuotation = quotationMapper.toEntity(returnedQuotationDTO);
        assertQuotationUpdatableFieldsEquals(returnedQuotation, getPersistedQuotation(returnedQuotation));

        insertedQuotation = returnedQuotation;
    }

    @Test
    @Transactional
    void createQuotationWithExistingId() throws Exception {
        // Create the Quotation with an existing ID
        quotation.setId(1L);
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFinalPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        quotation.setFinalPrice(null);

        // Create the Quotation, which fails.
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidityDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        quotation.setValidityDate(null);

        // Create the Quotation, which fails.
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuotations() throws Exception {
        // Initialize the database
        insertedQuotation = quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList
        restQuotationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].finalPrice").value(hasItem(sameNumber(DEFAULT_FINAL_PRICE))))
            .andExpect(jsonPath("$.[*].validityDate").value(hasItem(DEFAULT_VALIDITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    void getQuotation() throws Exception {
        // Initialize the database
        insertedQuotation = quotationRepository.saveAndFlush(quotation);

        // Get the quotation
        restQuotationMockMvc
            .perform(get(ENTITY_API_URL_ID, quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotation.getId().intValue()))
            .andExpect(jsonPath("$.finalPrice").value(sameNumber(DEFAULT_FINAL_PRICE)))
            .andExpect(jsonPath("$.validityDate").value(DEFAULT_VALIDITY_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getNonExistingQuotation() throws Exception {
        // Get the quotation
        restQuotationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuotation() throws Exception {
        // Initialize the database
        insertedQuotation = quotationRepository.saveAndFlush(quotation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quotation
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQuotation are not directly saved in db
        em.detach(updatedQuotation);
        updatedQuotation.finalPrice(UPDATED_FINAL_PRICE).validityDate(UPDATED_VALIDITY_DATE).comments(UPDATED_COMMENTS);
        QuotationDTO quotationDTO = quotationMapper.toDto(updatedQuotation);

        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(quotationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQuotationToMatchAllProperties(updatedQuotation);
    }

    @Test
    @Transactional
    void putNonExistingQuotation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotation.setId(longCount.incrementAndGet());

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(quotationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotation.setId(longCount.incrementAndGet());

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(quotationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotation.setId(longCount.incrementAndGet());

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(quotationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationWithPatch() throws Exception {
        // Initialize the database
        insertedQuotation = quotationRepository.saveAndFlush(quotation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quotation using partial update
        Quotation partialUpdatedQuotation = new Quotation();
        partialUpdatedQuotation.setId(quotation.getId());

        partialUpdatedQuotation.validityDate(UPDATED_VALIDITY_DATE).comments(UPDATED_COMMENTS);

        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuotationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedQuotation, quotation),
            getPersistedQuotation(quotation)
        );
    }

    @Test
    @Transactional
    void fullUpdateQuotationWithPatch() throws Exception {
        // Initialize the database
        insertedQuotation = quotationRepository.saveAndFlush(quotation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the quotation using partial update
        Quotation partialUpdatedQuotation = new Quotation();
        partialUpdatedQuotation.setId(quotation.getId());

        partialUpdatedQuotation.finalPrice(UPDATED_FINAL_PRICE).validityDate(UPDATED_VALIDITY_DATE).comments(UPDATED_COMMENTS);

        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQuotationUpdatableFieldsEquals(partialUpdatedQuotation, getPersistedQuotation(partialUpdatedQuotation));
    }

    @Test
    @Transactional
    void patchNonExistingQuotation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotation.setId(longCount.incrementAndGet());

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(quotationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotation.setId(longCount.incrementAndGet());

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(quotationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        quotation.setId(longCount.incrementAndGet());

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(quotationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotation() throws Exception {
        // Initialize the database
        insertedQuotation = quotationRepository.saveAndFlush(quotation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the quotation
        restQuotationMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return quotationRepository.count();
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

    protected Quotation getPersistedQuotation(Quotation quotation) {
        return quotationRepository.findById(quotation.getId()).orElseThrow();
    }

    protected void assertPersistedQuotationToMatchAllProperties(Quotation expectedQuotation) {
        assertQuotationAllPropertiesEquals(expectedQuotation, getPersistedQuotation(expectedQuotation));
    }

    protected void assertPersistedQuotationToMatchUpdatableProperties(Quotation expectedQuotation) {
        assertQuotationAllUpdatablePropertiesEquals(expectedQuotation, getPersistedQuotation(expectedQuotation));
    }
}
