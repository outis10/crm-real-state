package com.outis.crm.domain;

import static com.outis.crm.domain.NearbyLocationTestSamples.*;
import static com.outis.crm.domain.PropertyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NearbyLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NearbyLocation.class);
        NearbyLocation nearbyLocation1 = getNearbyLocationSample1();
        NearbyLocation nearbyLocation2 = new NearbyLocation();
        assertThat(nearbyLocation1).isNotEqualTo(nearbyLocation2);

        nearbyLocation2.setId(nearbyLocation1.getId());
        assertThat(nearbyLocation1).isEqualTo(nearbyLocation2);

        nearbyLocation2 = getNearbyLocationSample2();
        assertThat(nearbyLocation1).isNotEqualTo(nearbyLocation2);
    }

    @Test
    void propertyTest() {
        NearbyLocation nearbyLocation = getNearbyLocationRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        nearbyLocation.setProperty(propertyBack);
        assertThat(nearbyLocation.getProperty()).isEqualTo(propertyBack);

        nearbyLocation.property(null);
        assertThat(nearbyLocation.getProperty()).isNull();
    }
}
