package com.outis.crm.domain;

import static com.outis.crm.domain.CustomerTestSamples.*;
import static com.outis.crm.domain.PropertyTestSamples.*;
import static com.outis.crm.domain.QuotationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuotationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotation.class);
        Quotation quotation1 = getQuotationSample1();
        Quotation quotation2 = new Quotation();
        assertThat(quotation1).isNotEqualTo(quotation2);

        quotation2.setId(quotation1.getId());
        assertThat(quotation1).isEqualTo(quotation2);

        quotation2 = getQuotationSample2();
        assertThat(quotation1).isNotEqualTo(quotation2);
    }

    @Test
    void customerTest() {
        Quotation quotation = getQuotationRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        quotation.setCustomer(customerBack);
        assertThat(quotation.getCustomer()).isEqualTo(customerBack);

        quotation.customer(null);
        assertThat(quotation.getCustomer()).isNull();
    }

    @Test
    void propertyTest() {
        Quotation quotation = getQuotationRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        quotation.setProperty(propertyBack);
        assertThat(quotation.getProperty()).isEqualTo(propertyBack);

        quotation.property(null);
        assertThat(quotation.getProperty()).isNull();
    }
}
