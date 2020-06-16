package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MeatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meat.class);
        Meat meat1 = new Meat();
        meat1.setId(1L);
        Meat meat2 = new Meat();
        meat2.setId(meat1.getId());
        assertThat(meat1).isEqualTo(meat2);
        meat2.setId(2L);
        assertThat(meat1).isNotEqualTo(meat2);
        meat1.setId(null);
        assertThat(meat1).isNotEqualTo(meat2);
    }
}
