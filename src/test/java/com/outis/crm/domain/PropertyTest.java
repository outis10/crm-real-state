package com.outis.crm.domain;

import static com.outis.crm.domain.NearbyLocationTestSamples.*;
import static com.outis.crm.domain.OpportunityTestSamples.*;
import static com.outis.crm.domain.PropertyTestSamples.*;
import static com.outis.crm.domain.QuotationTestSamples.*;
import static com.outis.crm.domain.RentalContractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = getPropertySample1();
        Property property2 = new Property();
        assertThat(property1).isNotEqualTo(property2);

        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);

        property2 = getPropertySample2();
        assertThat(property1).isNotEqualTo(property2);
    }

    @Test
    void nearbyLocationTest() {
        Property property = getPropertyRandomSampleGenerator();
        NearbyLocation nearbyLocationBack = getNearbyLocationRandomSampleGenerator();

        property.addNearbyLocation(nearbyLocationBack);
        assertThat(property.getNearbyLocations()).containsOnly(nearbyLocationBack);
        assertThat(nearbyLocationBack.getProperty()).isEqualTo(property);

        property.removeNearbyLocation(nearbyLocationBack);
        assertThat(property.getNearbyLocations()).doesNotContain(nearbyLocationBack);
        assertThat(nearbyLocationBack.getProperty()).isNull();

        property.nearbyLocations(new HashSet<>(Set.of(nearbyLocationBack)));
        assertThat(property.getNearbyLocations()).containsOnly(nearbyLocationBack);
        assertThat(nearbyLocationBack.getProperty()).isEqualTo(property);

        property.setNearbyLocations(new HashSet<>());
        assertThat(property.getNearbyLocations()).doesNotContain(nearbyLocationBack);
        assertThat(nearbyLocationBack.getProperty()).isNull();
    }

    @Test
    void quotationTest() {
        Property property = getPropertyRandomSampleGenerator();
        Quotation quotationBack = getQuotationRandomSampleGenerator();

        property.addQuotation(quotationBack);
        assertThat(property.getQuotations()).containsOnly(quotationBack);
        assertThat(quotationBack.getProperty()).isEqualTo(property);

        property.removeQuotation(quotationBack);
        assertThat(property.getQuotations()).doesNotContain(quotationBack);
        assertThat(quotationBack.getProperty()).isNull();

        property.quotations(new HashSet<>(Set.of(quotationBack)));
        assertThat(property.getQuotations()).containsOnly(quotationBack);
        assertThat(quotationBack.getProperty()).isEqualTo(property);

        property.setQuotations(new HashSet<>());
        assertThat(property.getQuotations()).doesNotContain(quotationBack);
        assertThat(quotationBack.getProperty()).isNull();
    }

    @Test
    void rentalContractTest() {
        Property property = getPropertyRandomSampleGenerator();
        RentalContract rentalContractBack = getRentalContractRandomSampleGenerator();

        property.addRentalContract(rentalContractBack);
        assertThat(property.getRentalContracts()).containsOnly(rentalContractBack);
        assertThat(rentalContractBack.getProperty()).isEqualTo(property);

        property.removeRentalContract(rentalContractBack);
        assertThat(property.getRentalContracts()).doesNotContain(rentalContractBack);
        assertThat(rentalContractBack.getProperty()).isNull();

        property.rentalContracts(new HashSet<>(Set.of(rentalContractBack)));
        assertThat(property.getRentalContracts()).containsOnly(rentalContractBack);
        assertThat(rentalContractBack.getProperty()).isEqualTo(property);

        property.setRentalContracts(new HashSet<>());
        assertThat(property.getRentalContracts()).doesNotContain(rentalContractBack);
        assertThat(rentalContractBack.getProperty()).isNull();
    }

    @Test
    void opportunityTest() {
        Property property = getPropertyRandomSampleGenerator();
        Opportunity opportunityBack = getOpportunityRandomSampleGenerator();

        property.addOpportunity(opportunityBack);
        assertThat(property.getOpportunities()).containsOnly(opportunityBack);
        assertThat(opportunityBack.getProperty()).isEqualTo(property);

        property.removeOpportunity(opportunityBack);
        assertThat(property.getOpportunities()).doesNotContain(opportunityBack);
        assertThat(opportunityBack.getProperty()).isNull();

        property.opportunities(new HashSet<>(Set.of(opportunityBack)));
        assertThat(property.getOpportunities()).containsOnly(opportunityBack);
        assertThat(opportunityBack.getProperty()).isEqualTo(property);

        property.setOpportunities(new HashSet<>());
        assertThat(property.getOpportunities()).doesNotContain(opportunityBack);
        assertThat(opportunityBack.getProperty()).isNull();
    }
}
