package com.outis.realstate.properties.domain;

import static com.outis.realstate.properties.domain.ChargeTestSamples.*;
import static com.outis.realstate.properties.domain.RentalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.properties.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Charge.class);
        Charge charge1 = getChargeSample1();
        Charge charge2 = new Charge();
        assertThat(charge1).isNotEqualTo(charge2);

        charge2.setId(charge1.getId());
        assertThat(charge1).isEqualTo(charge2);

        charge2 = getChargeSample2();
        assertThat(charge1).isNotEqualTo(charge2);
    }

    @Test
    void rentalTest() {
        Charge charge = getChargeRandomSampleGenerator();
        Rental rentalBack = getRentalRandomSampleGenerator();

        charge.setRental(rentalBack);
        assertThat(charge.getRental()).isEqualTo(rentalBack);

        charge.rental(null);
        assertThat(charge.getRental()).isNull();
    }
}
