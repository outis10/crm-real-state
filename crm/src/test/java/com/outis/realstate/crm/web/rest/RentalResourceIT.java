package com.outis.realstate.crm.web.rest;

import static com.outis.realstate.crm.domain.RentalAsserts.*;
import static com.outis.realstate.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static com.outis.realstate.crm.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.realstate.crm.IntegrationTest;
import com.outis.realstate.crm.domain.Rental;
import com.outis.realstate.crm.domain.enumeration.ContractStatusEnum;
import com.outis.realstate.crm.repository.RentalRepository;
import com.outis.realstate.crm.service.RentalService;
import com.outis.realstate.crm.service.dto.RentalDTO;
import com.outis.realstate.crm.service.mapper.RentalMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RentalResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RentalResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_MONTHLY_RENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHLY_RENT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SECURITY_DEPOSIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECURITY_DEPOSIT = new BigDecimal(2);

    private static final ContractStatusEnum DEFAULT_CONTRACT_STATUS = ContractStatusEnum.ACTIVE;
    private static final ContractStatusEnum UPDATED_CONTRACT_STATUS = ContractStatusEnum.FINISHED;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/rentals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RentalRepository rentalRepository;

    @Mock
    private RentalRepository rentalRepositoryMock;

    @Autowired
    private RentalMapper rentalMapper;

    @Mock
    private RentalService rentalServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentalMockMvc;

    private Rental rental;

    private Rental insertedRental;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rental createEntity() {
        return new Rental()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .monthlyRent(DEFAULT_MONTHLY_RENT)
            .securityDeposit(DEFAULT_SECURITY_DEPOSIT)
            .contractStatus(DEFAULT_CONTRACT_STATUS)
            .createdBy(DEFAULT_CREATED_BY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rental createUpdatedEntity() {
        return new Rental()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .monthlyRent(UPDATED_MONTHLY_RENT)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .createdBy(UPDATED_CREATED_BY);
    }

    @BeforeEach
    public void initTest() {
        rental = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRental != null) {
            rentalRepository.delete(insertedRental);
            insertedRental = null;
        }
    }

    @Test
    @Transactional
    void createRental() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);
        var returnedRentalDTO = om.readValue(
            restRentalMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RentalDTO.class
        );

        // Validate the Rental in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRental = rentalMapper.toEntity(returnedRentalDTO);
        assertRentalUpdatableFieldsEquals(returnedRental, getPersistedRental(returnedRental));

        insertedRental = returnedRental;
    }

    @Test
    @Transactional
    void createRentalWithExistingId() throws Exception {
        // Create the Rental with an existing ID
        rental.setId(1L);
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setStartDate(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setEndDate(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMonthlyRentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setMonthlyRent(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContractStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setContractStatus(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRentals() throws Exception {
        // Initialize the database
        insertedRental = rentalRepository.saveAndFlush(rental);

        // Get all the rentalList
        restRentalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rental.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyRent").value(hasItem(sameNumber(DEFAULT_MONTHLY_RENT))))
            .andExpect(jsonPath("$.[*].securityDeposit").value(hasItem(sameNumber(DEFAULT_SECURITY_DEPOSIT))))
            .andExpect(jsonPath("$.[*].contractStatus").value(hasItem(DEFAULT_CONTRACT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRentalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(rentalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRentalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(rentalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRentalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(rentalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRentalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(rentalRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRental() throws Exception {
        // Initialize the database
        insertedRental = rentalRepository.saveAndFlush(rental);

        // Get the rental
        restRentalMockMvc
            .perform(get(ENTITY_API_URL_ID, rental.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rental.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.monthlyRent").value(sameNumber(DEFAULT_MONTHLY_RENT)))
            .andExpect(jsonPath("$.securityDeposit").value(sameNumber(DEFAULT_SECURITY_DEPOSIT)))
            .andExpect(jsonPath("$.contractStatus").value(DEFAULT_CONTRACT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRental() throws Exception {
        // Get the rental
        restRentalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRental() throws Exception {
        // Initialize the database
        insertedRental = rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rental
        Rental updatedRental = rentalRepository.findById(rental.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRental are not directly saved in db
        em.detach(updatedRental);
        updatedRental
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .monthlyRent(UPDATED_MONTHLY_RENT)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .createdBy(UPDATED_CREATED_BY);
        RentalDTO rentalDTO = rentalMapper.toDto(updatedRental);

        restRentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRentalToMatchAllProperties(updatedRental);
    }

    @Test
    @Transactional
    void putNonExistingRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRentalWithPatch() throws Exception {
        // Initialize the database
        insertedRental = rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rental using partial update
        Rental partialUpdatedRental = new Rental();
        partialUpdatedRental.setId(rental.getId());

        partialUpdatedRental
            .startDate(UPDATED_START_DATE)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .createdBy(UPDATED_CREATED_BY);

        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRental.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRental))
            )
            .andExpect(status().isOk());

        // Validate the Rental in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRental, rental), getPersistedRental(rental));
    }

    @Test
    @Transactional
    void fullUpdateRentalWithPatch() throws Exception {
        // Initialize the database
        insertedRental = rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rental using partial update
        Rental partialUpdatedRental = new Rental();
        partialUpdatedRental.setId(rental.getId());

        partialUpdatedRental
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .monthlyRent(UPDATED_MONTHLY_RENT)
            .securityDeposit(UPDATED_SECURITY_DEPOSIT)
            .contractStatus(UPDATED_CONTRACT_STATUS)
            .createdBy(UPDATED_CREATED_BY);

        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRental.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRental))
            )
            .andExpect(status().isOk());

        // Validate the Rental in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalUpdatableFieldsEquals(partialUpdatedRental, getPersistedRental(partialUpdatedRental));
    }

    @Test
    @Transactional
    void patchNonExistingRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rentalDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRental() throws Exception {
        // Initialize the database
        insertedRental = rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rental
        restRentalMockMvc
            .perform(delete(ENTITY_API_URL_ID, rental.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rentalRepository.count();
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

    protected Rental getPersistedRental(Rental rental) {
        return rentalRepository.findById(rental.getId()).orElseThrow();
    }

    protected void assertPersistedRentalToMatchAllProperties(Rental expectedRental) {
        assertRentalAllPropertiesEquals(expectedRental, getPersistedRental(expectedRental));
    }

    protected void assertPersistedRentalToMatchUpdatableProperties(Rental expectedRental) {
        assertRentalAllUpdatablePropertiesEquals(expectedRental, getPersistedRental(expectedRental));
    }
}
