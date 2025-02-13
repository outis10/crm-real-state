package com.outis.crm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentalContractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalContractDTO.class);
        RentalContractDTO rentalContractDTO1 = new RentalContractDTO();
        rentalContractDTO1.setId(1L);
        RentalContractDTO rentalContractDTO2 = new RentalContractDTO();
        assertThat(rentalContractDTO1).isNotEqualTo(rentalContractDTO2);
        rentalContractDTO2.setId(rentalContractDTO1.getId());
        assertThat(rentalContractDTO1).isEqualTo(rentalContractDTO2);
        rentalContractDTO2.setId(2L);
        assertThat(rentalContractDTO1).isNotEqualTo(rentalContractDTO2);
        rentalContractDTO1.setId(null);
        assertThat(rentalContractDTO1).isNotEqualTo(rentalContractDTO2);
    }
}
