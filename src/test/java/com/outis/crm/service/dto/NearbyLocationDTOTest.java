package com.outis.crm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NearbyLocationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NearbyLocationDTO.class);
        NearbyLocationDTO nearbyLocationDTO1 = new NearbyLocationDTO();
        nearbyLocationDTO1.setId(1L);
        NearbyLocationDTO nearbyLocationDTO2 = new NearbyLocationDTO();
        assertThat(nearbyLocationDTO1).isNotEqualTo(nearbyLocationDTO2);
        nearbyLocationDTO2.setId(nearbyLocationDTO1.getId());
        assertThat(nearbyLocationDTO1).isEqualTo(nearbyLocationDTO2);
        nearbyLocationDTO2.setId(2L);
        assertThat(nearbyLocationDTO1).isNotEqualTo(nearbyLocationDTO2);
        nearbyLocationDTO1.setId(null);
        assertThat(nearbyLocationDTO1).isNotEqualTo(nearbyLocationDTO2);
    }
}
