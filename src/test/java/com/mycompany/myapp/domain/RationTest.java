package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class RationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ration.class);
        Ration ration1 = new Ration();
        ration1.setId(1L);
        Ration ration2 = new Ration();
        ration2.setId(ration1.getId());
        assertThat(ration1).isEqualTo(ration2);
        ration2.setId(2L);
        assertThat(ration1).isNotEqualTo(ration2);
        ration1.setId(null);
        assertThat(ration1).isNotEqualTo(ration2);
    }
}
