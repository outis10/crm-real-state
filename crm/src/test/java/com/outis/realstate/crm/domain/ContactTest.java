package com.outis.realstate.crm.domain;

import static com.outis.realstate.crm.domain.ContactTestSamples.*;
import static com.outis.realstate.crm.domain.CustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.crm.web.rest.TestUtil;
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
    void assignedToTest() {
        Contact contact = getContactRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        contact.setAssignedTo(customerBack);
        assertThat(contact.getAssignedTo()).isEqualTo(customerBack);

        contact.assignedTo(null);
        assertThat(contact.getAssignedTo()).isNull();
    }
}
