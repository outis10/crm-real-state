package com.outis.crm.domain;

import static com.outis.crm.domain.ChargeTestSamples.*;
import static com.outis.crm.domain.CustomerTestSamples.*;
import static com.outis.crm.domain.PaymentTestSamples.*;
import static com.outis.crm.domain.PropertyTestSamples.*;
import static com.outis.crm.domain.RentalContractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RentalContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalContract.class);
        RentalContract rentalContract1 = getRentalContractSample1();
        RentalContract rentalContract2 = new RentalContract();
        assertThat(rentalContract1).isNotEqualTo(rentalContract2);

        rentalContract2.setId(rentalContract1.getId());
        assertThat(rentalContract1).isEqualTo(rentalContract2);

        rentalContract2 = getRentalContractSample2();
        assertThat(rentalContract1).isNotEqualTo(rentalContract2);
    }

    @Test
    void chargeTest() {
        RentalContract rentalContract = getRentalContractRandomSampleGenerator();
        Charge chargeBack = getChargeRandomSampleGenerator();

        rentalContract.addCharge(chargeBack);
        assertThat(rentalContract.getCharges()).containsOnly(chargeBack);
        assertThat(chargeBack.getRentalContract()).isEqualTo(rentalContract);

        rentalContract.removeCharge(chargeBack);
        assertThat(rentalContract.getCharges()).doesNotContain(chargeBack);
        assertThat(chargeBack.getRentalContract()).isNull();

        rentalContract.charges(new HashSet<>(Set.of(chargeBack)));
        assertThat(rentalContract.getCharges()).containsOnly(chargeBack);
        assertThat(chargeBack.getRentalContract()).isEqualTo(rentalContract);

        rentalContract.setCharges(new HashSet<>());
        assertThat(rentalContract.getCharges()).doesNotContain(chargeBack);
        assertThat(chargeBack.getRentalContract()).isNull();
    }

    @Test
    void paymentTest() {
        RentalContract rentalContract = getRentalContractRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        rentalContract.addPayment(paymentBack);
        assertThat(rentalContract.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getRentralContract()).isEqualTo(rentalContract);

        rentalContract.removePayment(paymentBack);
        assertThat(rentalContract.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getRentralContract()).isNull();

        rentalContract.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(rentalContract.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getRentralContract()).isEqualTo(rentalContract);

        rentalContract.setPayments(new HashSet<>());
        assertThat(rentalContract.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getRentralContract()).isNull();
    }

    @Test
    void propertyTest() {
        RentalContract rentalContract = getRentalContractRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        rentalContract.setProperty(propertyBack);
        assertThat(rentalContract.getProperty()).isEqualTo(propertyBack);

        rentalContract.property(null);
        assertThat(rentalContract.getProperty()).isNull();
    }

    @Test
    void customerTest() {
        RentalContract rentalContract = getRentalContractRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        rentalContract.setCustomer(customerBack);
        assertThat(rentalContract.getCustomer()).isEqualTo(customerBack);

        rentalContract.customer(null);
        assertThat(rentalContract.getCustomer()).isNull();
    }
}
