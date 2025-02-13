package com.outis.crm.domain;

import static com.outis.crm.domain.ChargeTestSamples.*;
import static com.outis.crm.domain.ChatInteractionTestSamples.*;
import static com.outis.crm.domain.ContactTestSamples.*;
import static com.outis.crm.domain.CustomerTestSamples.*;
import static com.outis.crm.domain.NotificationTestSamples.*;
import static com.outis.crm.domain.QuotationTestSamples.*;
import static com.outis.crm.domain.RentalContractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void chatInteractionTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        ChatInteraction chatInteractionBack = getChatInteractionRandomSampleGenerator();

        customer.addChatInteraction(chatInteractionBack);
        assertThat(customer.getChatInteractions()).containsOnly(chatInteractionBack);
        assertThat(chatInteractionBack.getCustomer()).isEqualTo(customer);

        customer.removeChatInteraction(chatInteractionBack);
        assertThat(customer.getChatInteractions()).doesNotContain(chatInteractionBack);
        assertThat(chatInteractionBack.getCustomer()).isNull();

        customer.chatInteractions(new HashSet<>(Set.of(chatInteractionBack)));
        assertThat(customer.getChatInteractions()).containsOnly(chatInteractionBack);
        assertThat(chatInteractionBack.getCustomer()).isEqualTo(customer);

        customer.setChatInteractions(new HashSet<>());
        assertThat(customer.getChatInteractions()).doesNotContain(chatInteractionBack);
        assertThat(chatInteractionBack.getCustomer()).isNull();
    }

    @Test
    void notificationTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        customer.addNotification(notificationBack);
        assertThat(customer.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getCustomer()).isEqualTo(customer);

        customer.removeNotification(notificationBack);
        assertThat(customer.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getCustomer()).isNull();

        customer.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(customer.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getCustomer()).isEqualTo(customer);

        customer.setNotifications(new HashSet<>());
        assertThat(customer.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getCustomer()).isNull();
    }

    @Test
    void quotationTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Quotation quotationBack = getQuotationRandomSampleGenerator();

        customer.addQuotation(quotationBack);
        assertThat(customer.getQuotations()).containsOnly(quotationBack);
        assertThat(quotationBack.getCustomer()).isEqualTo(customer);

        customer.removeQuotation(quotationBack);
        assertThat(customer.getQuotations()).doesNotContain(quotationBack);
        assertThat(quotationBack.getCustomer()).isNull();

        customer.quotations(new HashSet<>(Set.of(quotationBack)));
        assertThat(customer.getQuotations()).containsOnly(quotationBack);
        assertThat(quotationBack.getCustomer()).isEqualTo(customer);

        customer.setQuotations(new HashSet<>());
        assertThat(customer.getQuotations()).doesNotContain(quotationBack);
        assertThat(quotationBack.getCustomer()).isNull();
    }

    @Test
    void rentalContractTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        RentalContract rentalContractBack = getRentalContractRandomSampleGenerator();

        customer.addRentalContract(rentalContractBack);
        assertThat(customer.getRentalContracts()).containsOnly(rentalContractBack);
        assertThat(rentalContractBack.getCustomer()).isEqualTo(customer);

        customer.removeRentalContract(rentalContractBack);
        assertThat(customer.getRentalContracts()).doesNotContain(rentalContractBack);
        assertThat(rentalContractBack.getCustomer()).isNull();

        customer.rentalContracts(new HashSet<>(Set.of(rentalContractBack)));
        assertThat(customer.getRentalContracts()).containsOnly(rentalContractBack);
        assertThat(rentalContractBack.getCustomer()).isEqualTo(customer);

        customer.setRentalContracts(new HashSet<>());
        assertThat(customer.getRentalContracts()).doesNotContain(rentalContractBack);
        assertThat(rentalContractBack.getCustomer()).isNull();
    }

    @Test
    void chargeTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Charge chargeBack = getChargeRandomSampleGenerator();

        customer.addCharge(chargeBack);
        assertThat(customer.getCharges()).containsOnly(chargeBack);
        assertThat(chargeBack.getCustomer()).isEqualTo(customer);

        customer.removeCharge(chargeBack);
        assertThat(customer.getCharges()).doesNotContain(chargeBack);
        assertThat(chargeBack.getCustomer()).isNull();

        customer.charges(new HashSet<>(Set.of(chargeBack)));
        assertThat(customer.getCharges()).containsOnly(chargeBack);
        assertThat(chargeBack.getCustomer()).isEqualTo(customer);

        customer.setCharges(new HashSet<>());
        assertThat(customer.getCharges()).doesNotContain(chargeBack);
        assertThat(chargeBack.getCustomer()).isNull();
    }

    @Test
    void contactTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Contact contactBack = getContactRandomSampleGenerator();

        customer.addContact(contactBack);
        assertThat(customer.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getCustomer()).isEqualTo(customer);

        customer.removeContact(contactBack);
        assertThat(customer.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getCustomer()).isNull();

        customer.contacts(new HashSet<>(Set.of(contactBack)));
        assertThat(customer.getContacts()).containsOnly(contactBack);
        assertThat(contactBack.getCustomer()).isEqualTo(customer);

        customer.setContacts(new HashSet<>());
        assertThat(customer.getContacts()).doesNotContain(contactBack);
        assertThat(contactBack.getCustomer()).isNull();
    }
}
