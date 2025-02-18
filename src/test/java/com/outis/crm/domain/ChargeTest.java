package com.outis.crm.domain;

import static com.outis.crm.domain.ChargeTestSamples.*;
import static com.outis.crm.domain.CustomerTestSamples.*;
import static com.outis.crm.domain.RentalContractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
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
    void customerTest() {
        Charge charge = getChargeRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        charge.setCustomer(customerBack);
        assertThat(charge.getCustomer()).isEqualTo(customerBack);

        charge.customer(null);
        assertThat(charge.getCustomer()).isNull();
    }

    @Test
    void rentalContractTest() {
        Charge charge = getChargeRandomSampleGenerator();
        RentalContract rentalContractBack = getRentalContractRandomSampleGenerator();

        charge.setRentalContract(rentalContractBack);
        assertThat(charge.getRentalContract()).isEqualTo(rentalContractBack);

        charge.rentalContract(null);
        assertThat(charge.getRentalContract()).isNull();
    }
}
