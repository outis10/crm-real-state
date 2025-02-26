package com.outis.realstate.properties.domain;

import static com.outis.realstate.properties.domain.SaleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.properties.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sale.class);
        Sale sale1 = getSaleSample1();
        Sale sale2 = new Sale();
        assertThat(sale1).isNotEqualTo(sale2);

        sale2.setId(sale1.getId());
        assertThat(sale1).isEqualTo(sale2);

        sale2 = getSaleSample2();
        assertThat(sale1).isNotEqualTo(sale2);
    }
}
