package com.outis.crm.web.rest;

import static com.outis.crm.domain.NearbyLocationAsserts.*;
import static com.outis.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.crm.IntegrationTest;
import com.outis.crm.domain.NearbyLocation;
import com.outis.crm.domain.Property;
import com.outis.crm.repository.NearbyLocationRepository;
import com.outis.crm.service.NearbyLocationService;
import com.outis.crm.service.dto.NearbyLocationDTO;
import com.outis.crm.service.mapper.NearbyLocationMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link NearbyLocationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NearbyLocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISTANCE = 1D;
    private static final Double UPDATED_DISTANCE = 2D;

    private static final String DEFAULT_COORDINATES = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nearby-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NearbyLocationRepository nearbyLocationRepository;

    @Mock
    private NearbyLocationRepository nearbyLocationRepositoryMock;

    @Autowired
    private NearbyLocationMapper nearbyLocationMapper;

    @Mock
    private NearbyLocationService nearbyLocationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNearbyLocationMockMvc;

    private NearbyLocation nearbyLocation;

    private NearbyLocation insertedNearbyLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NearbyLocation createEntity(EntityManager em) {
        NearbyLocation nearbyLocation = new NearbyLocation()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .distance(DEFAULT_DISTANCE)
            .coordinates(DEFAULT_COORDINATES);
        // Add required entity
        Property property;
        if (TestUtil.findAll(em, Property.class).isEmpty()) {
            property = PropertyResourceIT.createEntity();
            em.persist(property);
            em.flush();
        } else {
            property = TestUtil.findAll(em, Property.class).get(0);
        }
        nearbyLocation.setProperty(property);
        return nearbyLocation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NearbyLocation createUpdatedEntity(EntityManager em) {
        NearbyLocation updatedNearbyLocation = new NearbyLocation()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .distance(UPDATED_DISTANCE)
            .coordinates(UPDATED_COORDINATES);
        // Add required entity
        Property property;
        if (TestUtil.findAll(em, Property.class).isEmpty()) {
            property = PropertyResourceIT.createUpdatedEntity();
            em.persist(property);
            em.flush();
        } else {
            property = TestUtil.findAll(em, Property.class).get(0);
        }
        updatedNearbyLocation.setProperty(property);
        return updatedNearbyLocation;
    }

    @BeforeEach
    public void initTest() {
        nearbyLocation = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedNearbyLocation != null) {
            nearbyLocationRepository.delete(insertedNearbyLocation);
            insertedNearbyLocation = null;
        }
    }

    @Test
    @Transactional
    void createNearbyLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);
        var returnedNearbyLocationDTO = om.readValue(
            restNearbyLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nearbyLocationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NearbyLocationDTO.class
        );

        // Validate the NearbyLocation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNearbyLocation = nearbyLocationMapper.toEntity(returnedNearbyLocationDTO);
        assertNearbyLocationUpdatableFieldsEquals(returnedNearbyLocation, getPersistedNearbyLocation(returnedNearbyLocation));

        insertedNearbyLocation = returnedNearbyLocation;
    }

    @Test
    @Transactional
    void createNearbyLocationWithExistingId() throws Exception {
        // Create the NearbyLocation with an existing ID
        nearbyLocation.setId(1L);
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNearbyLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nearbyLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nearbyLocation.setName(null);

        // Create the NearbyLocation, which fails.
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        restNearbyLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nearbyLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nearbyLocation.setType(null);

        // Create the NearbyLocation, which fails.
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        restNearbyLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nearbyLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDistanceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nearbyLocation.setDistance(null);

        // Create the NearbyLocation, which fails.
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        restNearbyLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nearbyLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNearbyLocations() throws Exception {
        // Initialize the database
        insertedNearbyLocation = nearbyLocationRepository.saveAndFlush(nearbyLocation);

        // Get all the nearbyLocationList
        restNearbyLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nearbyLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].coordinates").value(hasItem(DEFAULT_COORDINATES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNearbyLocationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(nearbyLocationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNearbyLocationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nearbyLocationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNearbyLocationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nearbyLocationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNearbyLocationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nearbyLocationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNearbyLocation() throws Exception {
        // Initialize the database
        insertedNearbyLocation = nearbyLocationRepository.saveAndFlush(nearbyLocation);

        // Get the nearbyLocation
        restNearbyLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, nearbyLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nearbyLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE))
            .andExpect(jsonPath("$.coordinates").value(DEFAULT_COORDINATES));
    }

    @Test
    @Transactional
    void getNonExistingNearbyLocation() throws Exception {
        // Get the nearbyLocation
        restNearbyLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNearbyLocation() throws Exception {
        // Initialize the database
        insertedNearbyLocation = nearbyLocationRepository.saveAndFlush(nearbyLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nearbyLocation
        NearbyLocation updatedNearbyLocation = nearbyLocationRepository.findById(nearbyLocation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNearbyLocation are not directly saved in db
        em.detach(updatedNearbyLocation);
        updatedNearbyLocation.name(UPDATED_NAME).type(UPDATED_TYPE).distance(UPDATED_DISTANCE).coordinates(UPDATED_COORDINATES);
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(updatedNearbyLocation);

        restNearbyLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nearbyLocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nearbyLocationDTO))
            )
            .andExpect(status().isOk());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNearbyLocationToMatchAllProperties(updatedNearbyLocation);
    }

    @Test
    @Transactional
    void putNonExistingNearbyLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nearbyLocation.setId(longCount.incrementAndGet());

        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNearbyLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nearbyLocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nearbyLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNearbyLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nearbyLocation.setId(longCount.incrementAndGet());

        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNearbyLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nearbyLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNearbyLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nearbyLocation.setId(longCount.incrementAndGet());

        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNearbyLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nearbyLocationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNearbyLocationWithPatch() throws Exception {
        // Initialize the database
        insertedNearbyLocation = nearbyLocationRepository.saveAndFlush(nearbyLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nearbyLocation using partial update
        NearbyLocation partialUpdatedNearbyLocation = new NearbyLocation();
        partialUpdatedNearbyLocation.setId(nearbyLocation.getId());

        partialUpdatedNearbyLocation.name(UPDATED_NAME).coordinates(UPDATED_COORDINATES);

        restNearbyLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNearbyLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNearbyLocation))
            )
            .andExpect(status().isOk());

        // Validate the NearbyLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNearbyLocationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNearbyLocation, nearbyLocation),
            getPersistedNearbyLocation(nearbyLocation)
        );
    }

    @Test
    @Transactional
    void fullUpdateNearbyLocationWithPatch() throws Exception {
        // Initialize the database
        insertedNearbyLocation = nearbyLocationRepository.saveAndFlush(nearbyLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nearbyLocation using partial update
        NearbyLocation partialUpdatedNearbyLocation = new NearbyLocation();
        partialUpdatedNearbyLocation.setId(nearbyLocation.getId());

        partialUpdatedNearbyLocation.name(UPDATED_NAME).type(UPDATED_TYPE).distance(UPDATED_DISTANCE).coordinates(UPDATED_COORDINATES);

        restNearbyLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNearbyLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNearbyLocation))
            )
            .andExpect(status().isOk());

        // Validate the NearbyLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNearbyLocationUpdatableFieldsEquals(partialUpdatedNearbyLocation, getPersistedNearbyLocation(partialUpdatedNearbyLocation));
    }

    @Test
    @Transactional
    void patchNonExistingNearbyLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nearbyLocation.setId(longCount.incrementAndGet());

        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNearbyLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nearbyLocationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nearbyLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNearbyLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nearbyLocation.setId(longCount.incrementAndGet());

        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNearbyLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nearbyLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNearbyLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nearbyLocation.setId(longCount.incrementAndGet());

        // Create the NearbyLocation
        NearbyLocationDTO nearbyLocationDTO = nearbyLocationMapper.toDto(nearbyLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNearbyLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nearbyLocationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NearbyLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNearbyLocation() throws Exception {
        // Initialize the database
        insertedNearbyLocation = nearbyLocationRepository.saveAndFlush(nearbyLocation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nearbyLocation
        restNearbyLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, nearbyLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nearbyLocationRepository.count();
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

    protected NearbyLocation getPersistedNearbyLocation(NearbyLocation nearbyLocation) {
        return nearbyLocationRepository.findById(nearbyLocation.getId()).orElseThrow();
    }

    protected void assertPersistedNearbyLocationToMatchAllProperties(NearbyLocation expectedNearbyLocation) {
        assertNearbyLocationAllPropertiesEquals(expectedNearbyLocation, getPersistedNearbyLocation(expectedNearbyLocation));
    }

    protected void assertPersistedNearbyLocationToMatchUpdatableProperties(NearbyLocation expectedNearbyLocation) {
        assertNearbyLocationAllUpdatablePropertiesEquals(expectedNearbyLocation, getPersistedNearbyLocation(expectedNearbyLocation));
    }
}
