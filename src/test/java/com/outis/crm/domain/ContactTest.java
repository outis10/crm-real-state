package com.outis.crm.domain;

import static com.outis.crm.domain.ContactTestSamples.*;
import static com.outis.crm.domain.CustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contact.class);
        Contact contact1 = getContactSample1();
        Contact contact2 = new Contact();
        assertThat(contact1).isNotEqualTo(contact2);

        contact2.setId(contact1.getId());
        assertThat(contact1).isEqualTo(contact2);

        contact2 = getContactSample2();
        assertThat(contact1).isNotEqualTo(contact2);
    }

    @Test
    void customerTest() {
        Contact contact = getContactRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        contact.setCustomer(customerBack);
        assertThat(contact.getCustomer()).isEqualTo(customerBack);

        contact.customer(null);
        assertThat(contact.getCustomer()).isNull();
    }
}
