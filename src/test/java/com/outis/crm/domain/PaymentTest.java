package com.outis.crm.domain;

import static com.outis.crm.domain.PaymentTestSamples.*;
import static com.outis.crm.domain.RentalContractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
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
    void rentralContractTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        RentalContract rentalContractBack = getRentalContractRandomSampleGenerator();

        payment.setRentralContract(rentalContractBack);
        assertThat(payment.getRentralContract()).isEqualTo(rentalContractBack);

        payment.rentralContract(null);
        assertThat(payment.getRentralContract()).isNull();
    }
}
