package com.outis.realstate.ai.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.ai.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RAGContextDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RAGContextDTO.class);
        RAGContextDTO rAGContextDTO1 = new RAGContextDTO();
        rAGContextDTO1.setId("id1");
        RAGContextDTO rAGContextDTO2 = new RAGContextDTO();
        assertThat(rAGContextDTO1).isNotEqualTo(rAGContextDTO2);
        rAGContextDTO2.setId(rAGContextDTO1.getId());
        assertThat(rAGContextDTO1).isEqualTo(rAGContextDTO2);
        rAGContextDTO2.setId("id2");
        assertThat(rAGContextDTO1).isNotEqualTo(rAGContextDTO2);
        rAGContextDTO1.setId(null);
        assertThat(rAGContextDTO1).isNotEqualTo(rAGContextDTO2);
    }
}
