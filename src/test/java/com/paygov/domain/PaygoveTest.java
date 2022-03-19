package com.paygov.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.paygov.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaygoveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paygove.class);
        Paygove paygove1 = new Paygove();
        paygove1.setId(1L);
        Paygove paygove2 = new Paygove();
        paygove2.setId(paygove1.getId());
        assertThat(paygove1).isEqualTo(paygove2);
        paygove2.setId(2L);
        assertThat(paygove1).isNotEqualTo(paygove2);
        paygove1.setId(null);
        assertThat(paygove1).isNotEqualTo(paygove2);
    }
}
