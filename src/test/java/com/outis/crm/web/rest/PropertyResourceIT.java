package com.outis.crm.web.rest;

import static com.outis.crm.domain.PropertyAsserts.*;
import static com.outis.crm.web.rest.TestUtil.createUpdateProxyForBean;
import static com.outis.crm.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outis.crm.IntegrationTest;
import com.outis.crm.domain.Property;
import com.outis.crm.domain.enumeration.OperationTypeEnum;
import com.outis.crm.domain.enumeration.PropertyStatusEnum;
import com.outis.crm.repository.PropertyRepository;
import com.outis.crm.service.dto.PropertyDTO;
import com.outis.crm.service.mapper.PropertyMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link PropertyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PropertyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final OperationTypeEnum DEFAULT_OPERATION_TYPE = OperationTypeEnum.SALES;
    private static final OperationTypeEnum UPDATED_OPERATION_TYPE = OperationTypeEnum.LEASE;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENTAL_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_AREA = 1;
    private static final Integer UPDATED_AREA = 2;

    private static final Integer DEFAULT_BEDROOMS = 1;
    private static final Integer UPDATED_BEDROOMS = 2;

    private static final Integer DEFAULT_BATHROOMS = 1;
    private static final Integer UPDATED_BATHROOMS = 2;

    private static final Double DEFAULT_APPRECIATION_RATE = 1D;
    private static final Double UPDATED_APPRECIATION_RATE = 2D;

    private static final String DEFAULT_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_FEATURES = "BBBBBBBBBB";

    private static final PropertyStatusEnum DEFAULT_STATUS = PropertyStatusEnum.AVAILABLE;
    private static final PropertyStatusEnum UPDATED_STATUS = PropertyStatusEnum.SOLD;

    private static final String DEFAULT_IMAGES = "AAAAAAAAAA";
    private static final String UPDATED_IMAGES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/properties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropertyMockMvc;

    private Property property;

    private Property insertedProperty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity() {
        return new Property()
            .name(DEFAULT_NAME)
            .codeName(DEFAULT_CODE_NAME)
            .type(DEFAULT_TYPE)
            .operationType(DEFAULT_OPERATION_TYPE)
            .location(DEFAULT_LOCATION)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .postalCode(DEFAULT_POSTAL_CODE)
            .price(DEFAULT_PRICE)
            .rentalPrice(DEFAULT_RENTAL_PRICE)
            .area(DEFAULT_AREA)
            .bedrooms(DEFAULT_BEDROOMS)
            .bathrooms(DEFAULT_BATHROOMS)
            .appreciationRate(DEFAULT_APPRECIATION_RATE)
            .features(DEFAULT_FEATURES)
            .status(DEFAULT_STATUS)
            .images(DEFAULT_IMAGES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createUpdatedEntity() {
        return new Property()
            .name(UPDATED_NAME)
            .codeName(UPDATED_CODE_NAME)
            .type(UPDATED_TYPE)
            .operationType(UPDATED_OPERATION_TYPE)
            .location(UPDATED_LOCATION)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .price(UPDATED_PRICE)
            .rentalPrice(UPDATED_RENTAL_PRICE)
            .area(UPDATED_AREA)
            .bedrooms(UPDATED_BEDROOMS)
            .bathrooms(UPDATED_BATHROOMS)
            .appreciationRate(UPDATED_APPRECIATION_RATE)
            .features(UPDATED_FEATURES)
            .status(UPDATED_STATUS)
            .images(UPDATED_IMAGES);
    }

    @BeforeEach
    public void initTest() {
        property = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProperty != null) {
            propertyRepository.delete(insertedProperty);
            insertedProperty = null;
        }
    }

    @Test
    @Transactional
    void createProperty() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);
        var returnedPropertyDTO = om.readValue(
            restPropertyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PropertyDTO.class
        );

        // Validate the Property in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProperty = propertyMapper.toEntity(returnedPropertyDTO);
        assertPropertyUpdatableFieldsEquals(returnedProperty, getPersistedProperty(returnedProperty));

        insertedProperty = returnedProperty;
    }

    @Test
    @Transactional
    void createPropertyWithExistingId() throws Exception {
        // Create the Property with an existing ID
        property.setId(1L);
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setName(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setType(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOperationTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setOperationType(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setLocation(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setCity(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setState(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setPostalCode(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setPrice(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAreaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setArea(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        property.setStatus(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProperties() throws Exception {
        // Initialize the database
        insertedProperty = propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].codeName").value(hasItem(DEFAULT_CODE_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].operationType").value(hasItem(DEFAULT_OPERATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].rentalPrice").value(hasItem(sameNumber(DEFAULT_RENTAL_PRICE))))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].bedrooms").value(hasItem(DEFAULT_BEDROOMS)))
            .andExpect(jsonPath("$.[*].bathrooms").value(hasItem(DEFAULT_BATHROOMS)))
            .andExpect(jsonPath("$.[*].appreciationRate").value(hasItem(DEFAULT_APPRECIATION_RATE)))
            .andExpect(jsonPath("$.[*].features").value(hasItem(DEFAULT_FEATURES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)));
    }

    @Test
    @Transactional
    void getProperty() throws Exception {
        // Initialize the database
        insertedProperty = propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc
            .perform(get(ENTITY_API_URL_ID, property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.codeName").value(DEFAULT_CODE_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.operationType").value(DEFAULT_OPERATION_TYPE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.rentalPrice").value(sameNumber(DEFAULT_RENTAL_PRICE)))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.bedrooms").value(DEFAULT_BEDROOMS))
            .andExpect(jsonPath("$.bathrooms").value(DEFAULT_BATHROOMS))
            .andExpect(jsonPath("$.appreciationRate").value(DEFAULT_APPRECIATION_RATE))
            .andExpect(jsonPath("$.features").value(DEFAULT_FEATURES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES));
    }

    @Test
    @Transactional
    void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProperty() throws Exception {
        // Initialize the database
        insertedProperty = propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .name(UPDATED_NAME)
            .codeName(UPDATED_CODE_NAME)
            .type(UPDATED_TYPE)
            .operationType(UPDATED_OPERATION_TYPE)
            .location(UPDATED_LOCATION)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .price(UPDATED_PRICE)
            .rentalPrice(UPDATED_RENTAL_PRICE)
            .area(UPDATED_AREA)
            .bedrooms(UPDATED_BEDROOMS)
            .bathrooms(UPDATED_BATHROOMS)
            .appreciationRate(UPDATED_APPRECIATION_RATE)
            .features(UPDATED_FEATURES)
            .status(UPDATED_STATUS)
            .images(UPDATED_IMAGES);
        PropertyDTO propertyDTO = propertyMapper.toDto(updatedProperty);

        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, propertyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPropertyToMatchAllProperties(updatedProperty);
    }

    @Test
    @Transactional
    void putNonExistingProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, propertyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePropertyWithPatch() throws Exception {
        // Initialize the database
        insertedProperty = propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the property using partial update
        Property partialUpdatedProperty = new Property();
        partialUpdatedProperty.setId(property.getId());

        partialUpdatedProperty
            .codeName(UPDATED_CODE_NAME)
            .type(UPDATED_TYPE)
            .postalCode(UPDATED_POSTAL_CODE)
            .rentalPrice(UPDATED_RENTAL_PRICE)
            .area(UPDATED_AREA)
            .bedrooms(UPDATED_BEDROOMS)
            .bathrooms(UPDATED_BATHROOMS)
            .status(UPDATED_STATUS)
            .images(UPDATED_IMAGES);

        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProperty.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPropertyUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProperty, property), getPersistedProperty(property));
    }

    @Test
    @Transactional
    void fullUpdatePropertyWithPatch() throws Exception {
        // Initialize the database
        insertedProperty = propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the property using partial update
        Property partialUpdatedProperty = new Property();
        partialUpdatedProperty.setId(property.getId());

        partialUpdatedProperty
            .name(UPDATED_NAME)
            .codeName(UPDATED_CODE_NAME)
            .type(UPDATED_TYPE)
            .operationType(UPDATED_OPERATION_TYPE)
            .location(UPDATED_LOCATION)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .price(UPDATED_PRICE)
            .rentalPrice(UPDATED_RENTAL_PRICE)
            .area(UPDATED_AREA)
            .bedrooms(UPDATED_BEDROOMS)
            .bathrooms(UPDATED_BATHROOMS)
            .appreciationRate(UPDATED_APPRECIATION_RATE)
            .features(UPDATED_FEATURES)
            .status(UPDATED_STATUS)
            .images(UPDATED_IMAGES);

        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProperty.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProperty))
            )
            .andExpect(status().isOk());

        // Validate the Property in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPropertyUpdatableFieldsEquals(partialUpdatedProperty, getPersistedProperty(partialUpdatedProperty));
    }

    @Test
    @Transactional
    void patchNonExistingProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, propertyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(propertyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProperty() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        property.setId(longCount.incrementAndGet());

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPropertyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(propertyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Property in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProperty() throws Exception {
        // Initialize the database
        insertedProperty = propertyRepository.saveAndFlush(property);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the property
        restPropertyMockMvc
            .perform(delete(ENTITY_API_URL_ID, property.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return propertyRepository.count();
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

    protected Property getPersistedProperty(Property property) {
        return propertyRepository.findById(property.getId()).orElseThrow();
    }

    protected void assertPersistedPropertyToMatchAllProperties(Property expectedProperty) {
        assertPropertyAllPropertiesEquals(expectedProperty, getPersistedProperty(expectedProperty));
    }

    protected void assertPersistedPropertyToMatchUpdatableProperties(Property expectedProperty) {
        assertPropertyAllUpdatablePropertiesEquals(expectedProperty, getPersistedProperty(expectedProperty));
    }
}
