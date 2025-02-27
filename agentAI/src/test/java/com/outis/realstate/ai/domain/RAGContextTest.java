package com.outis.realstate.ai.domain;

import static com.outis.realstate.ai.domain.RAGContextTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.ai.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RAGContextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RAGContext.class);
        RAGContext rAGContext1 = getRAGContextSample1();
        RAGContext rAGContext2 = new RAGContext();
        assertThat(rAGContext1).isNotEqualTo(rAGContext2);

        rAGContext2.setId(rAGContext1.getId());
        assertThat(rAGContext1).isEqualTo(rAGContext2);

        rAGContext2 = getRAGContextSample2();
        assertThat(rAGContext1).isNotEqualTo(rAGContext2);
    }
}
