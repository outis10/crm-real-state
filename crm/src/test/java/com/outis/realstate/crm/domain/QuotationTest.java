package com.outis.realstate.crm.domain;

import static com.outis.realstate.crm.domain.QuotationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.crm.web.rest.TestUtil;
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
}
