package com.outis.crm.web.rest;

import static com.outis.crm.domain.RentalContractAsserts.*;
import static com.outis.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static com.outis.crm.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.crm.IntegrationTest;
import com.outis.crm.domain.RentalContract;
import com.outis.crm.domain.enumeration.ContractStatusEnum;
import com.outis.crm.repository.RentalContractRepository;
import com.outis.crm.service.dto.RentalContractDTO;
import com.outis.crm.service.mapper.RentalContractMapper;
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
 * Integration tests for the {@link RentalContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RentalContractResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_MONTHLY_RENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_RENT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SECURITY_DEPOSIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECURITY_DEPOSIT = new BigDecimal(2);

    private static final ContractStatusEnum DEFAULT_STATUS = ContractStatusEnum.ACTIVE;
    private static final ContractStatusEnum UPDATED_STATUS = ContractStatusEnum.FINISHED;

    private static final String ENTITY_API_URL = "/api/rental-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RentalContractRepository rentalContractRepository;

    @Autowired
    private RentalContractMapper rentalContractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentalContractMockMvc;

    private RentalContract rentalContract;

    private RentalContract insertedRentalContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalContract createEntity() {
        return new RentalContract()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .monthlyRent(DEFAULT_MONTHLY_RENT)
            .securityDeposit(DEFAULT_SECURITY_DEPOSIT)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentalContract createUpdatedEntity() {
        return new RentalContract()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .monthlyRent(UPDATED_MONTHLY_RENT)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        rentalContract = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRentalContract != null) {
            rentalContractRepository.delete(insertedRentalContract);
            insertedRentalContract = null;
        }
    }

    @Test
    @Transactional
    void createRentalContract() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);
        var returnedRentalContractDTO = om.readValue(
            restRentalContractMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RentalContractDTO.class
        );

        // Validate the RentalContract in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRentalContract = rentalContractMapper.toEntity(returnedRentalContractDTO);
        assertRentalContractUpdatableFieldsEquals(returnedRentalContract, getPersistedRentalContract(returnedRentalContract));

        insertedRentalContract = returnedRentalContract;
    }

    @Test
    @Transactional
    void createRentalContractWithExistingId() throws Exception {
        // Create the RentalContract with an existing ID
        rentalContract.setId(1L);
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalContract.setStartDate(null);

        // Create the RentalContract, which fails.
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        restRentalContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalContract.setEndDate(null);

        // Create the RentalContract, which fails.
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        restRentalContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMonthlyRentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalContract.setMonthlyRent(null);

        // Create the RentalContract, which fails.
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        restRentalContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rentalContract.setStatus(null);

        // Create the RentalContract, which fails.
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        restRentalContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRentalContracts() throws Exception {
        // Initialize the database
        insertedRentalContract = rentalContractRepository.saveAndFlush(rentalContract);

        // Get all the rentalContractList
        restRentalContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentalContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyRent").value(hasItem(sameNumber(DEFAULT_MONTHLY_RENT))))
            .andExpect(jsonPath("$.[*].securityDeposit").value(hasItem(sameNumber(DEFAULT_SECURITY_DEPOSIT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getRentalContract() throws Exception {
        // Initialize the database
        insertedRentalContract = rentalContractRepository.saveAndFlush(rentalContract);

        // Get the rentalContract
        restRentalContractMockMvc
            .perform(get(ENTITY_API_URL_ID, rentalContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rentalContract.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.monthlyRent").value(sameNumber(DEFAULT_MONTHLY_RENT)))
            .andExpect(jsonPath("$.securityDeposit").value(sameNumber(DEFAULT_SECURITY_DEPOSIT)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRentalContract() throws Exception {
        // Get the rentalContract
        restRentalContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRentalContract() throws Exception {
        // Initialize the database
        insertedRentalContract = rentalContractRepository.saveAndFlush(rentalContract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentalContract
        RentalContract updatedRentalContract = rentalContractRepository.findById(rentalContract.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRentalContract are not directly saved in db
        em.detach(updatedRentalContract);
        updatedRentalContract
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .monthlyRent(UPDATED_MONTHLY_RENT)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .status(UPDATED_STATUS);
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(updatedRentalContract);

        restRentalContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalContractDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalContractDTO))
            )
            .andExpect(status().isOk());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRentalContractToMatchAllProperties(updatedRentalContract);
    }

    @Test
    @Transactional
    void putNonExistingRentalContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalContract.setId(longCount.incrementAndGet());

        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalContractDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRentalContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalContract.setId(longCount.incrementAndGet());

        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRentalContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalContract.setId(longCount.incrementAndGet());

        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalContractMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRentalContractWithPatch() throws Exception {
        // Initialize the database
        insertedRentalContract = rentalContractRepository.saveAndFlush(rentalContract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentalContract using partial update
        RentalContract partialUpdatedRentalContract = new RentalContract();
        partialUpdatedRentalContract.setId(rentalContract.getId());

        partialUpdatedRentalContract.securityDeposit(UPDATED_SECURITY_DEPOSIT).status(UPDATED_STATUS);

        restRentalContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRentalContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRentalContract))
            )
            .andExpect(status().isOk());

        // Validate the RentalContract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalContractUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRentalContract, rentalContract),
            getPersistedRentalContract(rentalContract)
        );
    }

    @Test
    @Transactional
    void fullUpdateRentalContractWithPatch() throws Exception {
        // Initialize the database
        insertedRentalContract = rentalContractRepository.saveAndFlush(rentalContract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentalContract using partial update
        RentalContract partialUpdatedRentalContract = new RentalContract();
        partialUpdatedRentalContract.setId(rentalContract.getId());

        partialUpdatedRentalContract
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .monthlyRent(UPDATED_MONTHLY_RENT)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .status(UPDATED_STATUS);

        restRentalContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRentalContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRentalContract))
            )
            .andExpect(status().isOk());

        // Validate the RentalContract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalContractUpdatableFieldsEquals(partialUpdatedRentalContract, getPersistedRentalContract(partialUpdatedRentalContract));
    }

    @Test
    @Transactional
    void patchNonExistingRentalContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalContract.setId(longCount.incrementAndGet());

        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rentalContractDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRentalContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalContract.setId(longCount.incrementAndGet());

        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalContractDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRentalContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentalContract.setId(longCount.incrementAndGet());

        // Create the RentalContract
        RentalContractDTO rentalContractDTO = rentalContractMapper.toDto(rentalContract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalContractMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rentalContractDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RentalContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRentalContract() throws Exception {
        // Initialize the database
        insertedRentalContract = rentalContractRepository.saveAndFlush(rentalContract);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rentalContract
        restRentalContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, rentalContract.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rentalContractRepository.count();
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

    protected RentalContract getPersistedRentalContract(RentalContract rentalContract) {
        return rentalContractRepository.findById(rentalContract.getId()).orElseThrow();
    }

    protected void assertPersistedRentalContractToMatchAllProperties(RentalContract expectedRentalContract) {
        assertRentalContractAllPropertiesEquals(expectedRentalContract, getPersistedRentalContract(expectedRentalContract));
    }

    protected void assertPersistedRentalContractToMatchUpdatableProperties(RentalContract expectedRentalContract) {
        assertRentalContractAllUpdatablePropertiesEquals(expectedRentalContract, getPersistedRentalContract(expectedRentalContract));
    }
}
