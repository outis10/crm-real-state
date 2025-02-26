package com.outis.realstate.properties.domain;

import static com.outis.realstate.properties.domain.PaymentTestSamples.*;
import static com.outis.realstate.properties.domain.RentalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.properties.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void rentalTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        Rental rentalBack = getRentalRandomSampleGenerator();

        payment.setRental(rentalBack);
        assertThat(payment.getRental()).isEqualTo(rentalBack);

        payment.rental(null);
        assertThat(payment.getRental()).isNull();
    }
}
