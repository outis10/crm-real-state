package com.outis.crm.web.rest;

import static com.outis.crm.domain.ChargeAsserts.*;
import static com.outis.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static com.outis.crm.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.crm.IntegrationTest;
import com.outis.crm.domain.Charge;
import com.outis.crm.domain.RentalContract;
import com.outis.crm.domain.enumeration.ChargeStatusEnum;
import com.outis.crm.domain.enumeration.ChargeTypeEnum;
import com.outis.crm.repository.ChargeRepository;
import com.outis.crm.service.dto.ChargeDTO;
import com.outis.crm.service.mapper.ChargeMapper;
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
 * Integration tests for the {@link ChargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChargeResourceIT {

    private static final ChargeTypeEnum DEFAULT_TYPE = ChargeTypeEnum.LEASE;
    private static final ChargeTypeEnum UPDATED_TYPE = ChargeTypeEnum.MAINTAIN;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ChargeStatusEnum DEFAULT_STATUS = ChargeStatusEnum.PENDING;
    private static final ChargeStatusEnum UPDATED_STATUS = ChargeStatusEnum.PAID;

    private static final String ENTITY_API_URL = "/api/charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private ChargeMapper chargeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChargeMockMvc;

    private Charge charge;

    private Charge insertedCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charge createEntity(EntityManager em) {
        Charge charge = new Charge().type(DEFAULT_TYPE).amount(DEFAULT_AMOUNT).dueDate(DEFAULT_DUE_DATE).status(DEFAULT_STATUS);
        // Add required entity
        RentalContract rentalContract;
        if (TestUtil.findAll(em, RentalContract.class).isEmpty()) {
            rentalContract = RentalContractResourceIT.createEntity();
            em.persist(rentalContract);
            em.flush();
        } else {
            rentalContract = TestUtil.findAll(em, RentalContract.class).get(0);
        }
        charge.setRentalContract(rentalContract);
        return charge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charge createUpdatedEntity(EntityManager em) {
        Charge updatedCharge = new Charge().type(UPDATED_TYPE).amount(UPDATED_AMOUNT).dueDate(UPDATED_DUE_DATE).status(UPDATED_STATUS);
        // Add required entity
        RentalContract rentalContract;
        if (TestUtil.findAll(em, RentalContract.class).isEmpty()) {
            rentalContract = RentalContractResourceIT.createUpdatedEntity();
            em.persist(rentalContract);
            em.flush();
        } else {
            rentalContract = TestUtil.findAll(em, RentalContract.class).get(0);
        }
        updatedCharge.setRentalContract(rentalContract);
        return updatedCharge;
    }

    @BeforeEach
    public void initTest() {
        charge = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCharge != null) {
            chargeRepository.delete(insertedCharge);
            insertedCharge = null;
        }
    }

    @Test
    @Transactional
    void createCharge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);
        var returnedChargeDTO = om.readValue(
            restChargeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ChargeDTO.class
        );

        // Validate the Charge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCharge = chargeMapper.toEntity(returnedChargeDTO);
        assertChargeUpdatableFieldsEquals(returnedCharge, getPersistedCharge(returnedCharge));

        insertedCharge = returnedCharge;
    }

    @Test
    @Transactional
    void createChargeWithExistingId() throws Exception {
        // Create the Charge with an existing ID
        charge.setId(1L);
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        charge.setType(null);

        // Create the Charge, which fails.
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        restChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        charge.setAmount(null);

        // Create the Charge, which fails.
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        restChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDueDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        charge.setDueDate(null);

        // Create the Charge, which fails.
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        restChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        charge.setStatus(null);

        // Create the Charge, which fails.
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        restChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCharges() throws Exception {
        // Initialize the database
        insertedCharge = chargeRepository.saveAndFlush(charge);

        // Get all the chargeList
        restChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charge.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getCharge() throws Exception {
        // Initialize the database
        insertedCharge = chargeRepository.saveAndFlush(charge);

        // Get the charge
        restChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, charge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(charge.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCharge() throws Exception {
        // Get the charge
        restChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCharge() throws Exception {
        // Initialize the database
        insertedCharge = chargeRepository.saveAndFlush(charge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the charge
        Charge updatedCharge = chargeRepository.findById(charge.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCharge are not directly saved in db
        em.detach(updatedCharge);
        updatedCharge.type(UPDATED_TYPE).amount(UPDATED_AMOUNT).dueDate(UPDATED_DUE_DATE).status(UPDATED_STATUS);
        ChargeDTO chargeDTO = chargeMapper.toDto(updatedCharge);

        restChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChargeToMatchAllProperties(updatedCharge);
    }

    @Test
    @Transactional
    void putNonExistingCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        charge.setId(longCount.incrementAndGet());

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chargeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        charge.setId(longCount.incrementAndGet());

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chargeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        charge.setId(longCount.incrementAndGet());

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChargeWithPatch() throws Exception {
        // Initialize the database
        insertedCharge = chargeRepository.saveAndFlush(charge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the charge using partial update
        Charge partialUpdatedCharge = new Charge();
        partialUpdatedCharge.setId(charge.getId());

        partialUpdatedCharge.dueDate(UPDATED_DUE_DATE);

        restChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCharge))
            )
            .andExpect(status().isOk());

        // Validate the Charge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChargeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCharge, charge), getPersistedCharge(charge));
    }

    @Test
    @Transactional
    void fullUpdateChargeWithPatch() throws Exception {
        // Initialize the database
        insertedCharge = chargeRepository.saveAndFlush(charge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the charge using partial update
        Charge partialUpdatedCharge = new Charge();
        partialUpdatedCharge.setId(charge.getId());

        partialUpdatedCharge.type(UPDATED_TYPE).amount(UPDATED_AMOUNT).dueDate(UPDATED_DUE_DATE).status(UPDATED_STATUS);

        restChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCharge))
            )
            .andExpect(status().isOk());

        // Validate the Charge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChargeUpdatableFieldsEquals(partialUpdatedCharge, getPersistedCharge(partialUpdatedCharge));
    }

    @Test
    @Transactional
    void patchNonExistingCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        charge.setId(longCount.incrementAndGet());

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chargeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chargeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        charge.setId(longCount.incrementAndGet());

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chargeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        charge.setId(longCount.incrementAndGet());

        // Create the Charge
        ChargeDTO chargeDTO = chargeMapper.toDto(charge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChargeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(chargeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Charge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCharge() throws Exception {
        // Initialize the database
        insertedCharge = chargeRepository.saveAndFlush(charge);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the charge
        restChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, charge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return chargeRepository.count();
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

    protected Charge getPersistedCharge(Charge charge) {
        return chargeRepository.findById(charge.getId()).orElseThrow();
    }

    protected void assertPersistedChargeToMatchAllProperties(Charge expectedCharge) {
        assertChargeAllPropertiesEquals(expectedCharge, getPersistedCharge(expectedCharge));
    }

    protected void assertPersistedChargeToMatchUpdatableProperties(Charge expectedCharge) {
        assertChargeAllUpdatablePropertiesEquals(expectedCharge, getPersistedCharge(expectedCharge));
    }
}
